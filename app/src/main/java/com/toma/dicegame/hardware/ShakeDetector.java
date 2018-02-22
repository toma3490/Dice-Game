package com.toma.dicegame.hardware;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static com.toma.dicegame.utils.Constants.SHAKE_COUNT_RESET_TIME_MS;
import static com.toma.dicegame.utils.Constants.SHAKE_SLOP_TIME_MS;
import static com.toma.dicegame.utils.Constants.SHAKE_THRESHOLD_GRAVITY;

/**
 * Created by toma on 21.02.18.
 */

public class ShakeDetector implements SensorEventListener {

    private OnShakeListener shakeListener;
    private long shakeTimestamp;
    private int shakeCount;

    public void setOnShakeListener (OnShakeListener listener){
        this.shakeListener = listener;
    }
    public interface OnShakeListener {
        public void onShake (int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shakeListener != null){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY){
                final long now = System.currentTimeMillis();

                if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now){
                    return;
                }

                if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now){
                    shakeCount = 0;
                }

                shakeTimestamp = now;
                shakeCount++;

                shakeListener.onShake(shakeCount);
            }
        }
    }
}
