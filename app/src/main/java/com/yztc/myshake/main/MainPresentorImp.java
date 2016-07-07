package com.yztc.myshake.main;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**控制层
 * Created by Administrator on 2016/7/6.
 */
public class MainPresentorImp implements MainContract.MainPresenter{
    public static final int SUCCESS=0x11;
    public static final int FALUSE=0x22;
    private MainContract.MainView mainView;

    public MainPresentorImp() {
    }

    public MainContract.MainModle getMainModle() {
        return mainModle;
    }

    public void setMainModle(MainContract.MainModle mainModle) {
        this.mainModle = mainModle;
    }

    public MainContract.MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainContract.MainView mainView) {
        this.mainView = mainView;
    }

    private MainContract.MainModle  mainModle;

    public MainPresentorImp(MainContract.MainModle mainModle, MainContract.MainView mainView) {
        this.mainModle = mainModle;
        this.mainView = mainView;
    }
  //用于接收数据层在检测到重力感应器发生变化后会发送的消息
  public   Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    //调用是图层的摇一摇方法
                    mainView.onShake(true);
                break;
                case FALUSE:
                    mainView.onShake(false);
                    break;
            }
        }
    };
    //调用控制层 注册重力监听器
    @Override
    public void shake() {
        mainModle.shake();
    }
    // 调用控制层 卸载重力监听器
    @Override
    public void onDestroy() {
        mainModle.onDestroy();
    }
}
