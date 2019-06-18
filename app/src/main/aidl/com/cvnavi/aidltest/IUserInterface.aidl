// IUserInterface.aidl
package com.cvnavi.aidltest;
import com.cvnavi.aidltest.User;
// Declare any non-default types here with import statements

interface IUserInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    User getUser();
}
