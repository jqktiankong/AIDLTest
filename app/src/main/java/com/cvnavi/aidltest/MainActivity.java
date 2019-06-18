package com.cvnavi.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private IUserInterface iUserInterface;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent();
        intent.setClass(this, UserService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        startService(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            iUserInterface = IUserInterface.Stub.asInterface(service);
            try {
                User user = (User) iUserInterface.getUser();
                Log.d("jiqingke", "user = " + user.toString());
            } catch (RemoteException e) {
                Log.d("jiqingke", "user = " + e.toString());
                e.printStackTrace();
            }

        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            iUserInterface = null;
        }
    };
}
