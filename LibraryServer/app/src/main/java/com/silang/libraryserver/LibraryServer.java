package com.silang.libraryserver;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class LibraryServer extends Service {

    private Stub stub;

    public LibraryServer() {
        stub = new Stub();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //添加下列代码将后台Service变成前台Service
        //构建"点击通知后打开MainActivity"的Intent对象
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        //新建Builer对象
        Notification.Builder builer = new Notification.Builder(this);
        builer.setContentTitle("前台服务通知的标题");//设置通知的标题
        builer.setContentText("前台服务通知的内容");//设置通知的内容
        builer.setSmallIcon(R.mipmap.ic_launcher);//设置通知的图标
        builer.setContentIntent(pendingIntent);//设置点击通知后的操作
        Notification notification = builer.getNotification();//将Builder对象转变成普通的notification
        startForeground(1, notification);//让Service变成前台Service,并在系统的状态栏显示出来
    }

    @Override
    public IBinder onBind(Intent intent) {
       //返回给客户端
        return stub;
    }


    /***
     * 将Binder + BookCheckService 合成有跨进程提供服务的Stub
     */
    class Stub extends Binder implements BooKCheckService{
        @Override
        public String getBookInfo(String bookName) {
            return bookName+"的信息如下：****************";
        }
        @Override
        public String getBookList() {
            return "本馆所有书籍列表如下：\n" +
                    "1.****************\n"+
                    "2.****************\n"+
                    "3.****************\n"+
                    "4.****************\n";
        }
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){
                case IBinder.FIRST_CALL_TRANSACTION + 0:
                    data.enforceInterface("token10086");
                    if(data.readInt()==1){//表示客户端传过来的bookName不为空
                        reply.writeNoException();
                        reply.writeString(getBookInfo(data.readString()));
                    }else{
                        reply.writeNoException();
                        reply.writeString("您输入的书名为空！");
                    }
                    return true;

                case IBinder.FIRST_CALL_TRANSACTION + 1:
                    data.enforceInterface("token10000");
                    if(data.readInt()==1){
                        reply.writeNoException();
                        reply.writeString(getBookList());
                    }else{
                        reply.writeNoException();
                        reply.writeString("查询不到图书列表！");
                    }
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
    }


}
