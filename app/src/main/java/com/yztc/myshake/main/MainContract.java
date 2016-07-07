package com.yztc.myshake.main;

import android.content.Context;
import android.os.Handler;

import com.yztc.myshake.baseinterface.IModle;
import com.yztc.myshake.baseinterface.IPresenter;
import com.yztc.myshake.baseinterface.IView;

/**
 * 接口约束类
 * Created by Administrator on 2016/7/6.
 */
public class MainContract {
    interface MainView extends IView{
       void  onShake(boolean flage);
    }
    interface  MainModle extends IModle{
        void shake();
        void onDestroy();
    }
    interface  MainPresenter extends IPresenter{
        void shake();
        void onDestroy();
    }
}
