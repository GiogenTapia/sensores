package com.giogen.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG ="Sensores" ;
    private static final String TAG2 ="SENS" ;
    private SensorManager sensorManager;
    private Sensor mSensor;
    private Sensor mLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Log.d(TAG, "onCreateSensor: "+sensorManager);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s:deviceSensors) {
            Log.d(TAG, "onCreateListSensor: "+s.getResolution());
            Log.d(TAG, "onCreateListSensorS: "+s);

        }


        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            Log.d(TAG2, "Simon hay magnetometer  ");
        } else {
            // Failure! No magnetometer.
            Log.d(TAG2, "NO hay magnetometer COMPA :C");
        }

        mSensor = null;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            List<Sensor> gravSensors = sensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            for(int i=0; i<gravSensors.size(); i++) {
                if ((gravSensors.get(i).getVendor().contains("Google LLC")) &&
                        (gravSensors.get(i).getVersion() == 3)){
                    // Use the version 3 gravity sensor.
                    mSensor = gravSensors.get(i);
                    Log.d(TAG2, "Usar gravity version 3");
                }
            }
        }
        if (mSensor == null){
            // Use the accelerometer.
            if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Log.d(TAG2, "Usando ACCELEROMETER");
            } else{
                Log.d(TAG2, "NO PUEDES JUGAR AMIGO");
            }
        }




    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        Log.d(TAG2, "CambioDelSensor");
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        // Do something with this sensor value.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }



}



