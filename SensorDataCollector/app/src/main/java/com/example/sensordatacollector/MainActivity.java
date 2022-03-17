package com.example.sensordatacollector;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  SensorEventListener {

    private TextView gyroData,accData,magData;

    private  Sensor magSensor;
    private  Sensor gyroSensor;
    private  Sensor accSensor;

    private SensorManager magSensorManage;
    private  SensorManager gyroSensorManage;
    private  SensorManager accSensorManage;

    private float[] magList = null;
    private float[] gyroList = null;
    private float[] accList = null;

    @SuppressLint("ServiceCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroData = findViewById(R.id.gyroscopreTxt);
        accData = findViewById(R.id.accelaromaterTxt);
        magData = findViewById(R.id.magnetometerTxt);

        magSensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroSensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);

        magSensor = magSensorManage.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accSensor = accSensorManage.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = gyroSensorManage.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(magSensor == null){
            Toast.makeText(MainActivity.this,"No Magnetometer sensor in the device",Toast.LENGTH_LONG).show();
        }
        if(accSensor == null){
            Toast.makeText(MainActivity.this,"No Accelarometer sensor in the device",Toast.LENGTH_LONG).show();
        }
        if(gyroSensor == null){
            Toast.makeText(MainActivity.this,"No Gyroscope sensor in the device",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        magSensorManage.registerListener(this,magSensor,SensorManager.SENSOR_DELAY_NORMAL);
        accSensorManage.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_NORMAL);
        gyroSensorManage.registerListener(this,gyroSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        magSensorManage.unregisterListener(this);
        accSensorManage.unregisterListener(this);
        gyroSensorManage.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magList = new float[3];
            System.arraycopy(sensorEvent.values,0,magList,0,3);
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accList = new float[3];
            System.arraycopy(sensorEvent.values,0,accList,0,3);
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            magList = new float[3];
            System.arraycopy(sensorEvent.values,0,magList,0,3);
        }

        if(magList != null){
            String mValue = "Mx : " + magList[0] + ", My : " + magList[1] + ", Mz : " + magList[2];
            magData.setText(mValue);
        }
        else if(accList != null){
            String mValue = "Ax : " + accList[0] + ", Ay : " + accList[1] + ", Az : " + accList[2];
            accData.setText(mValue);
        }
        else if(gyroList != null){
            String mValue = "Gx : " + gyroList[0] + ", Gy : " + gyroList[1] + ", Gz : " + gyroList[2];
            gyroData.setText(mValue);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
