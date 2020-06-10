package com.example.lctripsteward.pedometer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.lctripsteward.utils.MySharedPreferencesUtils;


public class PedometerService extends Service implements SensorEventListener {
    private static final String TAG = "PedometerService";
    private SensorManager sensorManager;  //传感器管理者
    private int stepCountToday = 0;  //今日步数
    private int previousStepCount = 0; //上一次获取的“从系统重启到现在的总步数”


    public PedometerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行具体的逻辑操作
                startPedometer();
                Log.d(TAG, "run: "+ SystemClock.elapsedRealtime());

                //MySharedPreferencesUtils.putInt(getBaseContext(), "sign_in_today", 0);  //签到重置
            }
        }).start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int oneDay = 24*60*60*1000;  //24h
        long triggerAtTime = SystemClock.elapsedRealtime() + oneDay;  //出发任务的时间 = 从系统开机到现在的毫秒数 + 延时时长
        Intent i = new Intent(this, PedometerService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);  //触发任务的时间从系统开机算起，且唤醒CPU

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 如果用户sdk>=19，则开始计步
     * 否则Toast提醒
     */
    public void startPedometer(){
        if(sensorManager != null){
            sensorManager = null;
        }

        sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        if(Build.VERSION.SDK_INT >= 19){
            //若用户sdk版本>=19，则开始计步
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                    sensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Log.d(TAG, "startPedometer: sdk版本过低："+ Build.VERSION.SDK_INT);
        }
    }

    /**
     * 向后台提交今日步数
     */
    public void postStepCount(int stepCount){

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            int tempStep = (int)event.values[0];  //获取从系统重启到现在的总步数
            Log.d(TAG, "onSensorChanged: temStep="+tempStep);
            previousStepCount = MySharedPreferencesUtils.getInt(PedometerService.this, "previous_step");  //从本地获取 上一次获取的“从系统重启到现在的总步数”

            if(previousStepCount != -2){
                //本地存储了 上一次获取的“从系统重启到现在的总步数”
                stepCountToday = tempStep - previousStepCount;  //计算今日步数
                postStepCount(stepCountToday);  //提交今日步数

                MySharedPreferencesUtils.putInt(PedometerService.this, "step_count_today", stepCountToday);  //保存今日步数
            }else {  //首次使用app
                MySharedPreferencesUtils.putInt(PedometerService.this, "step_count_today", 0);  //保存今日步数
            }

            MySharedPreferencesUtils.putInt(PedometerService.this, "previous_step", tempStep);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
