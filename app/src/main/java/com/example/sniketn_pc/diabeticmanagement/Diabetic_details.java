package com.example.sniketn_pc.diabeticmanagement;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Diabetic_details extends AppCompatActivity {
    private Spinner ET_USER_GENDER, ET_USER_TYPE ;
    private ArrayAdapter<CharSequence> adapter, adapter_type;
    private Button saved, back;
    private String user_email, user_name, user_surname, diabetes_type, gender, method, user_mobile, user_height,user_age,doctor_email,emergency_phone ;
    private EditText ET_USER_NAME, ET_USER_SURNAME, ET_USER_AGE, ET_USER_HEIGHT, ET_USER_MOBILE,ET_DOCTOR,ET_EMERGENCY;
    private SharedPreferences preferences;
    static JSONObject jObj = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetic_details);
        //get user email from shared preferences
        preferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        user_email = preferences.getString("email", user_email);
        //Toast.makeText(getBaseContext(), user_email, Toast.LENGTH_LONG).show();
        method ="details";
        BackgroundTask backgroundTask = new BackgroundTask(Diabetic_details.this);
        String data = null;
        try {
            data = backgroundTask.execute(method,user_email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            jObj = new JSONObject(data);
            JSONArray userObject =jObj.getJSONArray("user"); // JSON Array
            JSONObject details = userObject.getJSONObject(0);
            user_name = details.getString("name");
            user_surname = details.getString("Surname");
            gender = details.getString("gender");
            user_height = details.getString("height");
            user_mobile = details.getString("Mobile");
            user_age = details.getString("age");
            diabetes_type = details.getString("diabetes type");
            doctor_email = details.getString("doctor_email");
            emergency_phone = details.getString("emergency_phone");



        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }



        //flat editText and set content
        ET_USER_NAME = (EditText)findViewById(R.id.name_editText);
        ET_USER_NAME.setText(user_name);
        ET_USER_SURNAME = (EditText)findViewById(R.id.surname_editText);
        ET_USER_SURNAME.setText(user_surname);
        ET_USER_AGE = (EditText)findViewById(R.id.age_editText);
        ET_USER_AGE.setText(user_age);
        ET_USER_HEIGHT = (EditText)findViewById(R.id.height_editText);
        ET_USER_HEIGHT.setText(user_height);
        ET_USER_MOBILE=(EditText)findViewById(R.id.contact_editText);
        ET_USER_MOBILE.setText(user_mobile);
        ET_DOCTOR=(EditText)findViewById(R.id.name_editTextD);
        ET_DOCTOR.setText(doctor_email);
        ET_EMERGENCY=(EditText)findViewById(R.id.name_editTextEmergency);
        ET_EMERGENCY.setText(emergency_phone);

        //spinner for choice gender
        ET_USER_GENDER = (Spinner)findViewById(R.id.spinner);
        adapter =ArrayAdapter.createFromResource(this,R.array.Gender,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_USER_GENDER.setAdapter(adapter);
        if(gender.equals("Male"))
        {
            ET_USER_GENDER.setSelection(0);
        }
        else {
            ET_USER_GENDER.setSelection(1);
        }
        ET_USER_GENDER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender= parent.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),parent.getSelectedItem()+ " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(gender.equals("Male"))
                {
                    parent.setSelection(0);
                }
                else {
                    parent.setSelection(1);
                }


            }
        });
        //spinner for choice diabetic type
        ET_USER_TYPE = (Spinner)findViewById(R.id.spinner2);
        adapter_type =ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_USER_TYPE.setAdapter(adapter_type);
        if(diabetes_type.equals("Type 1"))
        {

            ET_USER_TYPE.setSelection(0);
        }
        else {

            ET_USER_TYPE.setSelection(1);
        }
        ET_USER_TYPE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                diabetes_type= parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });

        saved = (Button)findViewById(R.id.save);
        saved.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                method="details_register";
                try{
                    //Double.parseDouble(ET_USER_HEIGHT.getText().toString());
                    //Integer.parseInt(ET_USER_MOBILE.getText().toString());
                    //Double.parseDouble(ET_USER_AGE.getText().toString());

                    user_name = ET_USER_NAME.getText().toString();
                    user_surname = ET_USER_SURNAME.getText().toString();
                    user_height = ET_USER_HEIGHT.getText().toString();
                    user_age = ET_USER_AGE.getText().toString();
                    user_mobile = ET_USER_MOBILE.getText().toString();
                    diabetes_type = ET_USER_TYPE.getSelectedItem().toString();
                    gender = ET_USER_GENDER.getSelectedItem().toString();
                    doctor_email = ET_DOCTOR.getText().toString();
                    emergency_phone = ET_EMERGENCY.getText().toString();
                    BackgroundTask backgroundTask = new BackgroundTask(Diabetic_details.this);
                    backgroundTask.execute(method,user_email,user_name,user_surname,diabetes_type,gender, user_height,user_age,user_mobile,doctor_email,emergency_phone);
                    //save mobile in sharedPreference
                    preferences = getBaseContext().getSharedPreferences(getBaseContext().getPackageName(), Context.MODE_PRIVATE);
                    preferences.edit().putString("user_mobile", user_mobile).apply();

                    Intent display = new Intent(Diabetic_details.this,MenuOption.class);
                    startActivity(display);
                    finish();

                }
                catch(Exception ex)
                {
                    Log.e("Exception", "This is my user details exception" + ex);
                }


            }
        });
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {

                try{


                    Intent display = new Intent(Diabetic_details.this,MenuOption.class);
                    startActivity(display);
                    finish();


                }
                catch(Exception ex)
                {
                    Log.e("Exception", "This is my user details exception" + ex);
                }

            }
        });
    }

}
