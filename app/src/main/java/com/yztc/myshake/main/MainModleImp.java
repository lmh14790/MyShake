package com.yztc.myshake.main;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.yztc.myshake.R;

/**
 * 传感器数据控制层
 * Created by Administrator on 2016/7/6.
 */
public class MainModleImp implements  MainContract.MainModle,SensorEventListener{
   private  SensorManager sensorManager;
   private  Sensor sensor;
   private   Handler handler;
   private  Context context;
   private SoundPool soundPool;
    private Vibrator vibrator;
    //soundPool在内存中的id
   private int id;
    //蜂鸣器是否准备完成
    private boolean isPrepare=false;
    public MainModleImp(Handler handler,Context context) {
        this.handler = handler;
        this.context=context;
        //初始化传感器管理对象
        sensorManager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
       //获得加速度传感器
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 初始化soundPool
        soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        //加载声音到soundPool
        id=soundPool.load(context, R.raw.outgoing,0);
        //初始化蜂鸣器
        vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //蜂鸣器准备完成的事件回调方法
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isPrepare=true;

            }
        });

    }
   //注册传感器
    @Override
    public void shake() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.e("tag", "注册成功");
    }
    //activityonDestroy()时persenter调用的取消注册方法
    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(this);
        Log.e("tag","取消注册");
    }
   //重力传感器发生 变化时调用的方法
    @Override
    public void onSensorChanged(SensorEvent event) {
        //获得x y z 三个感应器的最大值
          float max=Math.max(Math.abs(event.values[0]),Math.abs(event.values[1]));
               max=Math.max(max,Math.abs(event.values[2]));

           if(max>12){
               //发送摇一摇成功的消息到presenter中
               handler.sendEmptyMessage(MainPresentorImp.SUCCESS);
               //启动蜂鸣器
               vibrator.vibrate(1000);
               //如果soundPool准备完成播放声音
               if(isPrepare){
                   soundPool.play(id, 1, 1, 0, 0, 1);
               }
           }else{
               handler.sendEmptyMessage(MainPresentorImp.FALUSE);
           }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
