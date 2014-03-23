package com.tealeaf.plugin.plugins;

import com.tealeaf.TeaLeaf;
import com.tealeaf.plugin.IPlugin;
import com.tealeaf.event.Event;
import com.tealeaf.EventQueue;
import com.tealeaf.logger;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.WindowManager;

public class AccelerometerPlugin implements IPlugin, SensorEventListener {

	private Context context;

	// Sensors and Manager variables
	private SensorManager sensorManager;
	private Sensor accelerometerSensor;

	//flag to indicate if the sensors are currently listening for events
	private boolean sensorsListening = false;

	private float gravitationalConstant = -9.81f;

	private class AccelerometerEvent extends Event {
		public float x, y, z;
		public AccelerometerEvent(float x, float y, float z) {
			super("accelerometerEvent");
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public AccelerometerPlugin() {
	}

	public void onCreateApplication(Context applicationContext) {
		context = applicationContext;
	}

	public void onCreate(Activity activity, Bundle savedInstanceState) {
		// initialize all sensors and the sensor manager
		sensorManager = (SensorManager) context
			.getSystemService(Context.SENSOR_SERVICE);
		accelerometerSensor = sensorManager
			.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	public void startEvents(String jsonData) {
		//logger.log("{accelerometer} startEvents");
		registerListeners();
	}

	public void stopEvents(String jsonData) {
		//logger.log("{accelerometer} stopEvents");
		unregisterListeners();
	}

	// Register all needed sensor listeners
	public void registerListeners() {
		if (!sensorsListening) {
			sensorManager.registerListener(this, accelerometerSensor,
					SensorManager.SENSOR_DELAY_UI); //60,000 microseconds delay
			sensorsListening = true;
		}
	}

	// Unregister any listeners that are currently in use
	public void unregisterListeners() {
		if (sensorsListening) {
			sensorManager.unregisterListener(this);
			sensorsListening = false;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//No logic currently needed for accuracy changes
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Only look at accelerometer events
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}
		EventQueue.pushEvent(new AccelerometerEvent(
			event.values[0] / gravitationalConstant,
			event.values[1] / gravitationalConstant,
			event.values[2] / gravitationalConstant)
		);
	}

	public void onStart() {
	}

	public void onStop() {
	}

	public void onResume() {
	}

	public void onPause() {
	}

	public void onDestroy() {
		unregisterListeners();
	}

	public void onNewIntent(Intent intent) {
	}

	public void setInstallReferrer(String referrer) {
	}

	public void onActivityResult(Integer request, Integer result, Intent data) {
	}

	public boolean consumeOnBackPressed() {
		return true;
	}

	public void onBackPressed() {
	}
}
