package com.androidshowtime.parseservertest;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.parse.Parse;
import com.parse.ParseACL;

public class StarterApplication extends Application {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        //.server("http://3.133.91.42/parse/")
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                                 .applicationId("myappID")
                                 .clientKey("jwXZwyd2Odbg")//jwXZwyd2Odbg
                                 .server("http://3.133.91.42/parse/")
                                 .build()
        );

       /* ParseObject object = new ParseObject("ExampleObject");
        object.put("myNumber", "123");
        object.put("myString", "rob");
        object.put("Name", "Vontonnie");

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException ex) {


                if (ex == null) {
                    Log.i("Parse Result", "Successful!");
                } else {
                    Log.i("Parse Result", "Failed" + ex.toString());
                }


            }
        });*/
        //vi /opt/bitnami/parse/config.json


        /*instead of doing cd apps/parse/htdocs

just do cd stack/parse

and then cat config.json*/


        //enabling automatic user without username or password
        // ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}

