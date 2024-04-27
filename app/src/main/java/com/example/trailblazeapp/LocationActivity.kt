package com.example.trailblazeapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.Locale

class LocationActivity : AppCompatActivity() {
    private var latitudeTextView: TextView? = null
    private var longitudeTextView: TextView? = null
    private var addressTextView: TextView? = null
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private var geocoder: Geocoder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        latitudeTextView = findViewById(R.id.latitudeTextView)
        longitudeTextView = findViewById(R.id.longitudeTextView)
        addressTextView = findViewById(R.id.addressTextView)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = MyLocationListener()
        geocoder = Geocoder(this, Locale.getDefault())

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission is granted, get location
            location
        }
    }

    private val location: Unit
        private get() {
            if (locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission not granted, request it
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_REQUEST_CODE
                    )
                    return
                }

                // Request location updates from both NETWORK_PROVIDER and GPS_PROVIDER
                locationManager!!.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0f,
                    locationListener!!
                )
                locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    locationListener!!
                )
            } else {
                // Location provider not available, prompt user to enable it
                showLocationProviderDisabledDialog()
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get location
                location
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(
                    this,
                    "Location permission denied. Cannot retrieve location.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude
            latitudeTextView!!.text = "Latitude: $latitude"
            longitudeTextView!!.text = "Longitude: $longitude"

            // Get address from latitude and longitude
            try {
                val addresses = geocoder!!.getFromLocation(latitude, longitude, 1)
                if (addresses != null && addresses.size > 0) {
                    val address = addresses[0].getAddressLine(0)
                    addressTextView!!.text = "Address: $address"
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onProviderDisabled(provider: String) {
            // Handle when provider is disabled
        }

        override fun onProviderEnabled(provider: String) {
            // Handle when provider is enabled
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            // Handle status changes
        }
    }

    private fun showLocationProviderDisabledDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Services Disabled")
        builder.setMessage("Please enable location services to use this feature.")
        builder.setPositiveButton("OK") { dialog, which -> // Redirect user to location settings
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}
