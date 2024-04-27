package com.example.trailblazeapp

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class AccelerometerActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var textViewX: TextView? = null
    private var textViewY: TextView? = null
    private var textViewZ: TextView? = null
    private var textViewSuggestions: TextView? = null
    private var decimalFormat: DecimalFormat? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        textViewX = findViewById(R.id.textViewX)
        textViewY = findViewById(R.id.textViewY)
        textViewZ = findViewById(R.id.textViewZ)
        textViewSuggestions = findViewById(R.id.textViewSuggestions)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        decimalFormat = DecimalFormat("#.##")

        if (accelerometer == null) {
            textViewX?.text = "No accelerometer sensor found!"
            textViewY?.text = "No accelerometer sensor found!"
            textViewZ?.text = "No accelerometer sensor found!"
        }
    }

    override fun onResume() {
        super.onResume()
        if (accelerometer != null) {
            sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        if (accelerometer != null) {
            sensorManager!!.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val accelX = event.values[0]
            val accelY = event.values[1]
            val accelZ = event.values[2]

            textViewX?.text = "Acceleration X: " + decimalFormat?.format(accelX.toDouble())
            textViewY?.text = "Acceleration Y: " + decimalFormat?.format(accelY.toDouble())
            textViewZ?.text = "Acceleration Z: " + decimalFormat?.format(accelZ.toDouble())

            // Provide suggestions based on accelerometer readings
            textViewSuggestions?.text = suggestBasedOnAccelerometer(accelX, accelY, accelZ)
        }
    }

    private fun suggestBasedOnAccelerometer(accelX: Float, accelY: Float, accelZ: Float): String {
        // Example suggestions based on accelerometer readings
        return when {
            accelX > 1.5 -> "\n\nSuggestion:\nTilt your device to adjust orientation."
            accelY < -1.5 -> "\n\nSuggestion:\nHold your device upright for better stability."
            else -> "\n\nSuggestion:\nNo specific recommendation at the moment."
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not used, but required to implement SensorEventListener
    }
}
