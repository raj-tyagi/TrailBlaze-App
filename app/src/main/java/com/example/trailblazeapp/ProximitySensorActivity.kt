package com.example.trailblazeapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProximitySensorActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var proximitySensor: Sensor? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proximity_sensor)
        textView = findViewById(R.id.proximityTextView)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (proximitySensor == null) {
            textView?.text = "No proximity sensor found!"
        }
    }

    override fun onResume() {
        super.onResume()
        if (proximitySensor != null) {
            sensorManager!!.registerListener(
                this,
                proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (proximitySensor != null) {
            sensorManager!!.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        val proximityValue = event.values[0]
        textView?.text = "Proximity: $proximityValue"
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not used, but required to implement SensorEventListener
    }
}

//dummy change