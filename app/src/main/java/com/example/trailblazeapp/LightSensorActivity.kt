package com.example.trailblazeapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LightSensorActivity : AppCompatActivity(), SensorEventListener {
    var textView: TextView? = null
    var sensorManager: SensorManager? = null
    var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor)
        textView = findViewById(R.id.textview)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            val lightValue = event.values[0]
            textView!!.text = "Sensor Output Value: $lightValue"

            // Smart energy management logic
            if (lightValue < 100) {
                // Low light condition, suggest to increase indoor lighting or screen brightness
                textView!!.append("\n\nSuggestion :\nLow light detected. Consider increasing indoor lighting or screen brightness.")
            } else {
                // Adequate light condition, suggest to optimize energy usage
                textView!!.append("\n\nSuggestion :\nAdequate light detected. Optimize energy usage.")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
