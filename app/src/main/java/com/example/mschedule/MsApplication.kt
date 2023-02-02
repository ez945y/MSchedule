package com.example.mschedule

import android.app.Application
//import com.facebook.stetho.Stetho

//implementation 'com.facebook.stetho:stetho:1.5.1'
//android:name="com.example.mschedule.MsApplication"
class MsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Stetho.initializeWithDefaults(this)
    }
}