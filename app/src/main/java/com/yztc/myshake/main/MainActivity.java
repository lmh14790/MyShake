package com.yztc.myshake.main;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.yztc.myshake.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {
    //控制层的实例对象
    MainPresentorImp mainPresentorImp;
    @InjectView(R.id.image)
    ImageView image;
    ObjectAnimator objectAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //控制层的实例对象的初始化
        mainPresentorImp = new MainPresentorImp();
        MainModleImp mainModleImp = new MainModleImp(mainPresentorImp.handler, this);
        mainPresentorImp.setMainView(this);
        mainPresentorImp.setMainModle(mainModleImp);
        //启动重力传感器
        mainPresentorImp.shake();
        //摇一摇的动画
       objectAnimator=ObjectAnimator.ofFloat(image,"Rotation",0,30,0,-30,0,30,0,-30,0,0,30,0,-30,0,30,0,-30,0,0,30,0,-30,0,30,0,-30,0).setDuration(2000);

    }
   //摇一摇后的回调方法
    @Override
    public void onShake(boolean flage) {
        if (flage) {
            objectAnimator.start();
            Log.e("tag", "摇一摇成功");
        } else {
            Log.e("tag", "=========摇一摇失败===========");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresentorImp.onDestroy();
    }
}
