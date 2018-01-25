package com.example.diptapaul.learningsystem;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Dipta Paul on 5/15/2017.
 */
public class LearningSystem extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
