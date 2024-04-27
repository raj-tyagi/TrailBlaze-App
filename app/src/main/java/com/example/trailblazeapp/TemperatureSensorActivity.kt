package com.example.trailblazeapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TemperatureSensorActivity : AppCompatActivity(), SensorEventListener {
    private var textView: TextView? = null
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var isTempSensorAvailable: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature_sensor)
        textView = findViewById(R.id.textview)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            isTempSensorAvailable = true
        } else {
            textView?.text = "Temperature Sensor is not Available"
            isTempSensorAvailable = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (isTempSensorAvailable == true) {
            sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        val temperatureValue = event.values[0]
        textView?.text = "$temperatureValue°C"

        // Provide energy-saving suggestions based on temperature
        when {
            temperatureValue > 25 -> {
                textView?.append("\n\nSuggestion:\nHigh temperature detected. Consider adjusting thermostat or cooling systems.")
            }
            temperatureValue < 20 -> {
                textView?.append("\n\nSuggestion:\nLow temperature detected. Consider adjusting thermostat or heating systems.")
            }
            else -> {
                textView?.append("\n\nSuggestion:\nOptimal temperature. Energy usage is optimized.")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        if (isTempSensorAvailable == true) {
            sensorManager!!.unregisterListener(this)
        }
    }
}
