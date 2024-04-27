package com.example.trailblazeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonAccelerometer = findViewById<Button>(R.id.buttonAccelerometer)
        buttonAccelerometer.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    AccelerometerActivity::class.java
                )
            )
        }
        val buttonLightSensor = findViewById<Button>(R.id.buttonLightSensor)
        buttonLightSensor.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    LightSensorActivity::class.java
                )
            )
        }
        val buttonTemperatureSensor = findViewById<Button>(R.id.buttonTemperatureSensor)
        buttonTemperatureSensor.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    TemperatureSensorActivity::class.java
                )
            )
        }
        val buttonBarometer = findViewById<Button>(R.id.buttonBarometer) // New button for barometer
        buttonBarometer.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    BarometerActivity::class.java
                )
            )
        }
        val buttonProximitySensor =
            findViewById<Button>(R.id.buttonProximitySensor) // New button for proximity sensor
        buttonProximitySensor.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    ProximitySensorActivity::class.java
                )
            )
        }
        val buttonLocationFinder =
            findViewById<Button>(R.id.buttonLocationFinder) // New button for location finder
        buttonLocationFinder.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    LocationActivity::class.java
                )
            )
        }
    }
}
