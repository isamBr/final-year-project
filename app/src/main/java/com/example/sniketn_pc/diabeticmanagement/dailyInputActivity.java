package com.example.sniketn_pc.diabeticmanagement;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class dailyInputActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDatePicker, btnTimePicker, btnSaveInput, btnBackOptMenu;
    private EditText txtGlucose,txtInsulin,txtNote ;
    private TextView txtDate, txtTime ;
    private AlertDialog alertDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String year,month,day,delims, method,doctor_email,emergency_phone, user_email, input_date, input_time, input_glucose,input_insulin, input_note,user_mobile;
    private String [] dateSplit;
    private String message;
    private String messageAlert;
    private SharedPreferences preferences;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private ImageView image;
    private MediaPlayer mp;
    static JSONObject jObj = null;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_input);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        btnSaveInput=(Button)findViewById(R.id.btnSaveDailyInput);
        btnBackOptMenu=(Button)findViewById(R.id.btnBackOptionMenu);
        txtDate=(TextView)findViewById(R.id.in_date);
        txtTime=(TextView)findViewById(R.id.in_time);
        txtGlucose=(EditText)findViewById(R.id.inputGlucose);
        txtInsulin=(EditText)findViewById(R.id.inputInsulin);
        txtNote=(EditText)findViewById(R.id.inputNote);
        //Set alert to invisible
        image = (ImageView)findViewById(R.id.imageView3);
        image.setVisibility(View.INVISIBLE);

        //get user details from option menu
        preferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        user_email = preferences.getString("email", user_email);
        //SharedPreferences to save user email
        preferences = getSharedPreferences(getBaseContext().getPackageName(), Context.MODE_PRIVATE);
        preferences.edit().putString("email", user_email).apply();

        BackgroundTask backgroundTask = new BackgroundTask(dailyInputActivity.this);
        method="details";
        try {
            String data =backgroundTask.execute(method,user_email).get();
            try {
                jObj = new JSONObject(data);
                JSONArray userObject =jObj.getJSONArray("user"); // JSON Array
                JSONObject details = userObject.getJSONObject(0);
                user_mobile = details.getString("Mobile");
                doctor_email = details.getString("doctor_email");
                emergency_phone = details.getString("emergency_phone");
                //Toast.makeText(getBaseContext(), doctor_email+user_mobile+emergency_phone, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSaveInput.setOnClickListener(this);
        btnBackOptMenu.setOnClickListener(this);

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);

        // Get Current Time
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        txtTime.setText(mHour + ":" + mMinute);


    }

    @Override
    public void onClick(View v) {

        if (v == btnSaveInput) {
            method="dialy_inputs";
            delims="-";
            //get user daily input
            input_date = txtDate.getText().toString();
            input_time = txtTime.getText().toString();
            input_glucose = txtGlucose.getText().toString();
            input_insulin = txtInsulin.getText().toString();
            input_note = txtNote.getText().toString();
            //split date to year month year
            dateSplit=input_date.split(delims);
            day =dateSplit[0];
            month =dateSplit[1];
            year =dateSplit[2];
            if(input_glucose.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Please Enter a glucose..", Toast.LENGTH_LONG).show();

            }
            else if(input_insulin.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Please Enter an insulin..", Toast.LENGTH_LONG).show();
            }
            else if(input_note.isEmpty())
            {
                Toast.makeText(getBaseContext(), "Please Enter a note..", Toast.LENGTH_LONG).show();
            }
            else
            {


                //pass parameter to AsyncTask task to connect to webpage
                BackgroundTask backgroundTask = new BackgroundTask(getBaseContext());
                backgroundTask.execute(method,user_email,input_time,input_glucose,input_insulin,input_note,day,month,year);
                Toast.makeText(getBaseContext(),"Input added",Toast.LENGTH_LONG).show();
                int a =(Integer.parseInt(input_glucose));
                //Send message to doctor or with 30 minute if no respond
                alertDialog = new AlertDialog.Builder(dailyInputActivity.this).create();
                alertDialog.setTitle("Alert");
                if(a<4)
                {
                    //Alert blink
                    blink(image);
                    //play sound effect for alert
                    mp= MediaPlayer.create(getBaseContext(),R.raw.blue);
                    mp.start();
                    //Create an notification with a pending intent.
                    // Tapping in the expanded notification makes a call the emergency number
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(dailyInputActivity.this);
                    builder.setSmallIcon(android.R.drawable.ic_dialog_dialer);
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+emergency_phone));
                    PendingIntent pendingIntent = PendingIntent.getActivity(dailyInputActivity.this, 0, intent, 0);
                    builder.setContentIntent(pendingIntent);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                    builder.setContentTitle("Emergency call");
                    builder.setContentText("Your notification content here.");
                    builder.setSubText("Tap to makes a call ");
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Will display the notification in the notification bar
                    notificationManager.notify(1, builder.build());
                    //create alert dialog
                    messageAlert="Low blood glucose levels, Hypo Please Eat carbohydrate ";
                    alertDialog.setMessage(messageAlert);
                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel Alert",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    image.setVisibility(View.INVISIBLE);


                                }
                            });

                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Am Ok carbohydrate taking ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    image.setVisibility(View.INVISIBLE);
                                    Intent display = new Intent(getBaseContext(),MenuOption.class);
                                    startActivity(display);

                                }
                            });

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "I need help",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String location=locationToSendByEmail();
                                    sendEmail(messageAlert, user_email,user_mobile,location);
                                    sendSMSMessage(emergency_phone,messageAlert,location );
                                    Intent display = new Intent(getBaseContext(),MenuOption.class);
                                    startActivity(display);
                                    finish();
                                }
                            });
                    alertDialog.show();
                    // (Under 4 mmol/l Low blood glucose levels, also known as Hypoglycaemia or Hypo)
                    //Take too much insulin
                    //Eat less carbohydrate than usual
                    //Leave too long between meals
                    //Do more activity than usual
                    //Following alcohol (which may not occur until a few hours later)*
                    //Sometimes you may not find an obvious reason
                }
                else if(a>8)
                {
                    //Alert blink
                    blink(image);
                    //play sound effect for alert
                    mp= MediaPlayer.create(getBaseContext(),R.raw.blue);
                    mp.start();
                    //Create an notification with a pending intent.
                    // Tapping in the expanded notification makes a call the number
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(dailyInputActivity.this);
                    builder.setSmallIcon(android.R.drawable.ic_dialog_dialer);
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+emergency_phone));
                    PendingIntent pendingIntent = PendingIntent.getActivity(dailyInputActivity.this, 0, intent, 0);
                    builder.setContentIntent(pendingIntent);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                    builder.setContentTitle("Emergency call");
                    builder.setContentText("Your notification content here.");
                    builder.setSubText("Tap to makes a call ");
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Will display the notification in the notification bar
                    notificationManager.notify(1, builder.build());
                    messageAlert ="High blood glucose levels(Hyper)!Please take your insulin";
                    alertDialog.setMessage(messageAlert);
                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel Alert",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    image.setVisibility(View.INVISIBLE);



                                }
                            });

                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Am Ok insulin taking ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    image.setVisibility(View.INVISIBLE);
                                    Intent display = new Intent(getBaseContext(),MenuOption.class);
                                    startActivity(display);

                                }
                            });

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "I need help",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    String location=locationToSendByEmail();
                                    sendEmail(messageAlert, user_email,user_mobile,location);
                                    sendSMSMessage(emergency_phone,messageAlert,location );
                                    Intent display = new Intent(getBaseContext(),MenuOption.class);
                                    startActivity(display);
                                    finish();

                                }
                            });
                    alertDialog.show();


                    //HbA1c “long term test” and is performed by a medical professional. This is a measure of your blood glucose control over a period of the previous approx 6 -8 weeks.
                    //4 -7 mmol/l before meals, and around 8 mmol/l if testing 2 hours after meals
                    /*High blood glucose levels(Hyperglycaemia or Hyper)
                    cause
                    Are not taking enough insulin
                    Miss or forget to take your insulin ( or take a lower amount in error)
                    Eat more carbohydrate foods than usual
                    Are less active than usual
                    Are under stress
                    Have an illness eg cold flu, infection (see further info re illness below)*/


                }
                else
                {
                    Intent display = new Intent(getBaseContext(),MenuOption.class);
                    startActivity(display);
                    finish();

                }

            }


        }
        if (v == btnBackOptMenu) {

             // menu option activity
            Intent display = new Intent(getBaseContext(),MenuOption.class);
            startActivity(display);
            finish();

        }

        if (v == btnDatePicker) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
    //method to send email alert
    protected void sendEmail(String alertMessage, String user_email, String mobi,String str){

        Mail m = new Mail("diabeticmanagementsystem@gmail.com", "R00120054");

        String[] toArr = {doctor_email, user_email};
        m.setTo(toArr);
        m.setFrom(user_email);
        m.setSubject("Alert from diabetic .");
        m.setBody("User need assistance! Current advice given is: "+ alertMessage+"  User contact email : "+user_email+"  User  mobile  : "+mobi+" Location :"+str);

        try {
            //m.addAttachment("/sdcard/filelocation");
            if(m.send()) {
                Toast.makeText(dailyInputActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(dailyInputActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            Log.e("MailApp", "Could not send email", e);
            Toast.makeText(dailyInputActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
        }

    }
    //method to get current location
    private String locationToSendByEmail(){
        String str ="";
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return str;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER )){

            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

            //get the latitude
            double latitude =location.getLatitude();
            //get the longitude
            double longitude =location.getLongitude();
            //instantiate the class LatLng
            LatLng latLng = new LatLng(latitude,longitude);
            //instantiate the class Geocoder
            Geocoder geocoder = new Geocoder((getApplicationContext()));
            try {
                List<Address> addressList= geocoder.getFromLocation(latitude,longitude, 1);
                str = addressList.get(0).getAddressLine(0)+", ";
                str += addressList.get(0).getLocality()+",";
                str += addressList.get(0).getCountryName();
                //Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            //get the latitude
            double latitude =location.getLatitude();
            //get the longitude
            double longitude =location.getLongitude();
            //instantiate the class LatLng
            LatLng latLng = new LatLng(latitude,longitude);
            //instantiate the class Geocoder
            Geocoder geocoder = new Geocoder((getApplicationContext()));
            try {
                List<Address> addressList= geocoder.getFromLocation(latitude,longitude, 1);
                str = addressList.get(0).getAddressLine(0)+", ";
                str += addressList.get(0).getLocality()+",";
                str += addressList.get(0).getCountryName();
                //Toast.makeText(getBaseContext(),str,Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return str;

    }
    //method to send SMS alert
    protected void sendSMSMessage(String phone,String messag, String location) {
        message ="User need assistance, current advise: " + messag+", Current location: "+location;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(emergency_phone, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        }
    }

    public void blink(ImageView image){

        image.setVisibility(View.INVISIBLE);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        image.startAnimation(animation1);
    }

}
