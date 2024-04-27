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

class BarometerActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var pressureSensor: Sensor? = null
    private var disptxt: TextView? = null
    private var textViewSuggestions: TextView? = null
    private var decimalFormat: DecimalFormat? = null

    companion object {
        private const val THRESHOLD_LOW_PRESSURE = 1000.0f // Define your own threshold
        private const val THRESHOLD_HIGH_PRESSURE = 1020.0f // Define your own threshold
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barometer)

        disptxt = findViewById(R.id.barometerTextView)
        textViewSuggestions = findViewById(R.id.textViewSuggestions)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        pressureSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PRESSURE)
        decimalFormat = DecimalFormat("#.###")

        if (pressureSensor == null) {
            disptxt?.text = "No barometer sensor found!"
        }
    }

    override fun onResume() {
        super.onResume()
        if (pressureSensor != null) {
            sensorManager!!.registerListener(
                this,
                pressureSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (pressureSensor != null) {
            sensorManager!!.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        val pressureValue = event.values[0]
        disptxt?.text = "Pressure: " + decimalFormat?.format(pressureValue.toDouble()) + " mbar"

        // Provide suggestions based on barometer readings
        textViewSuggestions?.text = suggestBasedOnBarometer(pressureValue)
    }

    private fun suggestBasedOnBarometer(pressureValue: Float): String {
        // Example suggestions based on barometer readings
        return when {
            pressureValue < THRESHOLD_LOW_PRESSURE -> {
                "Low pressure detected. Expect potential weather changes."
            }
            pressureValue > THRESHOLD_HIGH_PRESSURE -> {
                "High pressure detected. Clear weather expected."
            }
            else -> {
                "No specific recommendation at the moment."
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not used, but required to implement SensorEventListener
    }
}
