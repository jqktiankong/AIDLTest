package com.cvnavi.aidltest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IUserInterface iUserInterface;
    private Intent intent;

    private Button add, get;

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.addUser);
        get = findViewById(R.id.getUsers);

        add.setOnClickListener(this);
        get.setOnClickListener(this);


//        intent = new Intent();
//        intent.setClass(this, UserService.class);
//        bindService(intent, mConnection, BIND_AUTO_CREATE);

        Intent intent = new Intent();
        intent.setAction("com.cvnavi.aidltest.userservice");
        intent.setPackage("com.cvnavi.aidltest");
//        Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
        // bindService是为了本应用调用
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        // startService是为了让另一个进程调用
        startService(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            Log.d("jiqingke", "绑定成功");
            iUserInterface = IUserInterface.Stub.asInterface(service);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            iUserInterface = null;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addUser:
                if (iUserInterface != null) {
                    User user = new User();
                    user.setName("test1 " + i);
                    user.setAge(i);
                    try {
                        iUserInterface.addUser(user);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                break;
            case R.id.getUsers:
                if (iUserInterface != null) {
                    try {
                        List<User> users = iUserInterface.getUsers();

                        for (User user : users) {
                            Log.d("jiqingke", "user.name = " + user.getName());
                            Log.d("jiqingke", "user.age = " + user.getAge());
                            Log.d("jiqingke", "----------------------------");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
