package com.cvnavi.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class UserService extends Service {
    @Override
    public void onCreate() {
        Log.d("jiqingke", "创建服务");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private final IUserInterface.Stub iBinder = new IUserInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public User getUser() throws RemoteException {
            User user = new User(1, 2, 3, 4);
            return user;
        }
    };
}
