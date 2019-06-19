package com.cvnavi.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserService extends Service {
    private int i = 0;
    private List<User> users;

    @Override
    public void onCreate() {
        Log.d("jiqingke", "创建服务");
        super.onCreate();
        users = new ArrayList<User>();
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
        public void addUser(User user) throws RemoteException {
            if (user != null) {
                users.add(user);
            }
        }

        @Override
        public List<User> getUsers() throws RemoteException {
            return users;
        }
    };
}
