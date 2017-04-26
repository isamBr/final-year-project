package com.example.sniketn_pc.diabeticmanagement;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    private EditText ET_USER_EMAIL,ET_USER_PASS;
    private String user_email,user_pass;
    private Context context ;
    private Button getUser, register;
    private TextView textViewNoConnection;
    private CheckBox checkBoxRemenberLogin;
    private SharedPreferences preferences;
    final java.util.Calendar c = java.util.Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();//for toast message
        //ConnectivityManager
        ET_USER_EMAIL = (EditText)findViewById(R.id.email);
        ET_USER_PASS = (EditText)findViewById(R.id.Password);
        getUser = (Button)findViewById(R.id.getUser);
        register = (Button)findViewById(R.id.Register);
        textViewNoConnection= (TextView) findViewById(R.id.textNoConnection);
        checkBoxRemenberLogin= (CheckBox) findViewById(R.id.checkBoxRemenber);

        //check network connection
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //&& networkInfo.isConnected()
        if(networkInfo!=null )
        {
            textViewNoConnection.setVisibility(View.INVISIBLE);

        }
        else
        {
            register.setEnabled(false);
            getUser.setEnabled(false);

        }

        //schedule alarm to notify user to enter his glucose level
        c.set(Calendar.HOUR_OF_DAY,20);
        c.set(Calendar.MINUTE,34);
        c.set(Calendar.SECOND,30);
        Intent intent = new Intent(getApplicationContext(),AlertAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY,pendingIntent);

        register.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                user_email = ET_USER_EMAIL.getText().toString();
                user_pass = ET_USER_PASS.getText().toString();
                String method ="register";
                if(!user_email.contains("@")|| user_email.isEmpty()|| user_email.contains("@@")|| user_email.contains(" "))
                    {
                        Toast.makeText(context, "Please Enter a valid email ..", Toast.LENGTH_LONG).show();
                        ET_USER_EMAIL.setText("");


                    }
                else if(user_pass.isEmpty())
                    {
                        Toast.makeText(context, "Please Enter a password..", Toast.LENGTH_LONG).show();
                        ET_USER_PASS.setText("");
                    }
                else
                    {
                        BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
                        backgroundTask.execute(method, user_email, user_pass);

                    }
                if(checkBoxRemenberLogin.isSelected()) {
                    ET_USER_EMAIL.setText("");
                    ET_USER_PASS.setText("");
                }
            }
        });

        getUser.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                user_email = ET_USER_EMAIL.getText().toString();
                user_pass = ET_USER_PASS.getText().toString();
                String method ="login";

                if(!user_email.contains("@")|| user_email.isEmpty()|| user_email.contains("@@")|| user_email.contains(" "))
                {
                    Toast.makeText(context, "Please Enter a valid email ..", Toast.LENGTH_LONG).show();
                    ET_USER_EMAIL.setText("");
                }
                else if(user_pass.isEmpty())
                {
                    Toast.makeText(context, "Please Enter a password..", Toast.LENGTH_LONG).show();
                    ET_USER_PASS.setText("");
                }
                else
                {
                    BackgroundTask backgroundTask = new BackgroundTask(MainActivity.this);
                    backgroundTask.execute(method,user_email,user_pass);
                    preferences = getSharedPreferences(getBaseContext().getPackageName(), Context.MODE_PRIVATE);
                    preferences.edit().putString("email", user_email).apply();

                }
                if(!checkBoxRemenberLogin.isChecked()) {
                    ET_USER_EMAIL.setText("");
                    ET_USER_PASS.setText("");
                }
            }
        });



    }


}
