package com.silang.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView check, get;
    EditText editText1, editText2;
    IBinder mRemote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = (TextView) findViewById(R.id.checkBookInfo);
        get = (TextView) findViewById(R.id.getBookList);
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        check.setOnClickListener(this);
        get.setOnClickListener(this);
        Intent intent = new Intent();
        intent.setAction("com.silang.libraryserver.MY_ACTION");

        bindService(intent, coon, BIND_AUTO_CREATE);
    }
    ServiceConnection coon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.println("*******************服务已经连接！！");
            mRemote = iBinder;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("*******************服务关闭连接！！");
        }
    };

    @Override
    public void onClick(View view) {
        //查询图书
        if (view.getId() == R.id.checkBookInfo) {
            try {
                String t1 = checkBookInfo();
                if(!TextUtils.isEmpty(t1)){
                    editText1.setText(t1);
                }else{
                    editText1.setText("结果是空的！");
                }
            } catch (Exception e) {
                System.out.println("查询图书异常： = " + e.toString());
            }
        }

        //获取图书列表
        if (view.getId() == R.id.getBookList) {
            try {
                String t1 = getBookList();
                if(!TextUtils.isEmpty(t1)){
                    editText2.setText(t1);
                }else{
                    editText2.setText("结果是空的！");
                }
            } catch (Exception e) {
                System.out.println("获取图书列表异常： = " + e.toString());
            }
        }
    }

    String checkBookInfo() throws android.os.RemoteException {
        //_data客户端发送给远程服务端的数据载体
        android.os.Parcel _data = android.os.Parcel.obtain();
        //_reply客户端接收远程服务端的数据的载体
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String result ="";
        try {
            //设置token
            _data.writeInterfaceToken("token10086");

            String bookName = editText1.getText().toString();
            if (!TextUtils.isEmpty(bookName)) {
                _data.writeInt(1);
                _data.writeString(bookName);
            } else {
                _data.writeInt(0);
            }

            //底层是通过NativeBinder框架与远程服务端交互，
            // 会导致远程服务端的Binder的onTransact方法被调用
            mRemote.transact(IBinder.FIRST_CALL_TRANSACTION + 0, _data, _reply, 0);
            _reply.readException();
            //读取远程服务端返回的数据
            result = _reply.readString();
        } finally {
            _data.recycle();
            _reply.recycle();
        }
        return result;
    }

    String getBookList() throws android.os.RemoteException {
        //_data客户端发送给远程服务端的数据载体
        android.os.Parcel _data = android.os.Parcel.obtain();
        //_reply客户端接收远程服务端的数据的载体
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String result = "";
        try {
            //设置token
            _data.writeInterfaceToken("token10000");
            _data.writeInt(1);
            //底层是通过NativeBinder框架与远程服务端交互，
            // 会导致远程服务端的Binder的onTransact方法被调用
            mRemote.transact(IBinder.FIRST_CALL_TRANSACTION + 1, _data, _reply, 0);
            _reply.readException();
            //读取远程服务端返回的数据
            result =  _reply.readString();
        } finally {
            _data.recycle();
            _reply.recycle();
        }
        return result;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(coon);
    }
}
