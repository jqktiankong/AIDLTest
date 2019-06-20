package com.cvnavi.aidltest2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cvnavi.aidltest.IUserInterface;
import com.cvnavi.aidltest.User;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private IUserInterface iUserInterface;

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

        Intent intent = new Intent();
        intent.setAction("com.cvnavi.aidltest.userservice");
        Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
        bindService(eintent, mConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addUser:
                if (iUserInterface != null) {
                    User user = new User();
                    user.setName("test2 " + i);
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
    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            iUserInterface = IUserInterface.Stub.asInterface(service);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            iUserInterface = null;
        }
    };

    public Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
