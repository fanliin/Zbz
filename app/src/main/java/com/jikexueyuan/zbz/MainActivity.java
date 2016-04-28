package com.jikexueyuan.zbz;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private ImageView iv;
    private TextView tv;
    float degree, lastDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//通过系统服务得到传感器服务管理器

        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                System.out.println(event.values[0]);
                degree = -event.values[0];
                if (Math.abs(degree - lastDegree) > 5) {
                    RotateAnimation rotateAnimation = new RotateAnimation(lastDegree, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setFillAfter(true);
                    iv.startAnimation(rotateAnimation);
                    lastDegree = degree;
                }
                direction(event.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);//方向传感器
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    public void direction(float angle) {//定义方向
        String orientation = "正北";
        if (angle > 22.5f && angle <= 67.5f) {
            orientation = "东北";
        } else if (angle > 67.5f && angle <= 112.5f) {
            orientation = "正东";
        } else if (angle > 112.5f && angle <= 157.5f) {
            orientation = "东南";
        } else if (angle > 157.5f && angle <= 202.5f) {
            orientation = "正南";
        } else if (angle > 202.5f && angle <= 247.5f) {
            orientation = "西南";
        } else if (angle > 247.5f && angle <= 292.5f) {
            orientation = "正西";
        } else if (angle > 292.5f && angle <= 337.5) {
            orientation = "西北";
        }

        tv.setText(orientation + " " + angle + "度");
    }
}
