package com.example.sniketn_pc.diabeticmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sniketn-Pc on 30/01/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context ctx;
    private Activity activity;
    String day,month, year, method, email, password, line, response,
            data, user_name, user_surname, user_type, user_gender, user_mobile,
            input_time,input_glucose,input_insulin,input_note,doctor_email,emergency_phone;
    String user_height , user_age ;
    URL url = null;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    OutputStream OS;
    InputStream IS;
    HttpURLConnection httpURLConnection;
    String reg_url="http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/register.php";
    //String reg_url="http://10.0.2.2/DiabeticManagement/rest/register.php";
    String login_url="http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/login.php";
    //String login_url="http://10.0.2.2/DiabeticManagement/rest/login.php";
    String details_register_url ="http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/details_register.php";
    //String details_register_url ="http://10.0.2.2/DiabeticManagement/rest/details_register.php";
    String dialy_inputs_url ="http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/dialy_inputs.php";
    //String dialy_inputs_url ="http://10.0.2.2/DiabeticManagement/rest/dialy_inputs.php";
    String view_results_url ="http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/view_result.php";
    //String view_results_url ="http://10.0.2.2/DiabeticManagement/rest/view_result.php";
    String display_table_url ="http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/display_inputs.php";
    //String display_table_url ="http://10.0.2.2/DiabeticManagement/rest/display_inputs.php";
    String details_url = "http://ec2-34-251-206-70.eu-west-1.compute.amazonaws.com/rest/details.php";
    static JSONObject jObj = null;
    private SharedPreferences preferences;


    BackgroundTask(Context ctx)
    {
        this.ctx=ctx;

    }
    @Override
    protected void onPreExecute()
    {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");


    }

    @Override
    protected String doInBackground(String... params) {

        method =params[0];

        if(method.equals("display_table"))
        {
            email = params[1];
            month =params[2];
            year =params[3];
            try {
                url = new URL(display_table_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8")
                        + "&" +
                        URLEncoder.encode("month","UTF-8") +"=" + URLEncoder.encode(month,"UTF-8")
                        + "&" +
                        URLEncoder.encode("year","UTF-8") +"=" + URLEncoder.encode(year,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if(method.equals("view_results"))
        {
            email = params[1];
            month =params[2];
            year =params[3];
            try {
                url = new URL(view_results_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8")
                        + "&" +
                        URLEncoder.encode("month","UTF-8") +"=" + URLEncoder.encode(month,"UTF-8")
                        + "&" +
                        URLEncoder.encode("year","UTF-8") +"=" + URLEncoder.encode(year,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(method.equals("dialy_inputs"))
        {
            email = params[1];
            input_time= params[2] ;
            input_glucose= params[3];
            input_insulin= params[4];
            input_note= params[5];
            day= params[6];
            month= params[7];
            year= params[8];

            try {
                url = new URL(dialy_inputs_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8")
                        + "&" +
                        URLEncoder.encode("input_time","UTF-8") +"=" + URLEncoder.encode(input_time,"UTF-8")
                        + "&" +
                        URLEncoder.encode("input_glucose","UTF-8") +"=" + URLEncoder.encode(input_glucose,"UTF-8")
                        + "&" +
                        URLEncoder.encode("input_insulin","UTF-8") +"=" + URLEncoder.encode(input_insulin,"UTF-8")
                        + "&" +
                        URLEncoder.encode("input_note","UTF-8") +"=" + URLEncoder.encode(input_note,"UTF-8")
                        + "&" +
                        URLEncoder.encode("day","UTF-8") +"=" + URLEncoder.encode(day,"UTF-8")
                        + "&" +
                        URLEncoder.encode("month","UTF-8") +"=" + URLEncoder.encode(month,"UTF-8")
                        + "&" +
                        URLEncoder.encode("year","UTF-8") +"=" + URLEncoder.encode(year,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(method.equals("register"))
        {
            email = params[1];
            password = params[2];

            try {
                url = new URL(reg_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8")+ "&" +
                        URLEncoder.encode("password","UTF-8") +"=" + URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(method.equals("login"))
        {
            //get user name and password from main activity
            email = params[1];
            password = params[2];

            //connect to login.php
            try {

                url = new URL(login_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8")+ "&" +
                        URLEncoder.encode("password","UTF-8") +"=" + URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else if(method.equals("details"))
        {
            //get user name
            email = params[1];


            //connect to details.php
            try {

                url = new URL(details_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else if(method.equals("details_register"))
        {
            //get user details from diabetic details activity
            email = params[1];
            user_name = params[2];
            user_surname =params[3];
            user_type = params[4];
            user_gender = params[5];
            user_height = params[6];
            user_age  = params[7];
            user_mobile = params[8];
            doctor_email=params[9];
            emergency_phone=params[10];

            //connect to details_register.php
            try {
                url = new URL(details_register_url);
                httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OS =httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                data = URLEncoder.encode("email","UTF-8") +"=" + URLEncoder.encode(email,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_name","UTF-8") +"=" + URLEncoder.encode(user_name,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_surname","UTF-8") +"=" + URLEncoder.encode(user_surname,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_type","UTF-8") +"=" + URLEncoder.encode(user_type,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_gender","UTF-8") +"=" + URLEncoder.encode(user_gender,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_height","UTF-8") +"=" + URLEncoder.encode(user_height,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_age","UTF-8") +"=" + URLEncoder.encode(user_age,"UTF-8")
                        + "&" +
                        URLEncoder.encode("user_mobile","UTF-8") +"=" + URLEncoder.encode(user_mobile,"UTF-8")
                        + "&" +
                        URLEncoder.encode("doctor_email","UTF-8") +"=" + URLEncoder.encode(doctor_email,"UTF-8")
                        + "&" +
                        URLEncoder.encode("emergency_phone","UTF-8") +"=" + URLEncoder.encode(emergency_phone,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                IS =httpURLConnection.getInputStream();
                bufferedReader =new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                line = "";
                response = "";
                while ((line = bufferedReader.readLine()) != null)
                {
                    response+= line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result)
    {
        String operation ="";
        String message = "";


        if(!method.equals("view_results"))
        {
            try {
                jObj = new JSONObject(response);
                // json success tag
                //operation = jObj.getInt("success");
                operation =jObj.getString("operation");
                message  =jObj.getString("message");

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());

            }

        }
        if(operation.equals("login"))
        {
            //give user details with message
            Toast.makeText(ctx,message,Toast.LENGTH_LONG).show();

            if(message.equals("Login success"))
            {
                //Toast.makeText(ctx,response,Toast.LENGTH_LONG).show();
                try {
                    //jObj = new JSONObject(response);
                    JSONArray userObject =jObj.getJSONArray("user"); // JSON Array
                    JSONObject details = userObject.getJSONObject(0);
                    email = details.getString("email");

                    //save sharedPreference
                    preferences = ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
                    preferences.edit().putString("email", email).apply();

                    //start activity Menu option
                    Intent display = new Intent(ctx,MenuOption.class);
                    ctx.startActivity(display);

                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());

                }

            }

        }
        else if(operation.equals("user details"))
        {
            //display message in toast for the user
            Toast.makeText(ctx,message,Toast.LENGTH_LONG).show();

        }
        else if(operation.equals("new user register"))
            {

                Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
            }
        else if(operation.equals("viewInput"))
            {
                //alertDialog.setMessage(response);
                //alertDialog.show();
            }
        else if(operation.equals("user daily input")) {

            //Toast.makeText(ctx, response, Toast.LENGTH_LONG).show();
        }
        else if(operation.equals("display_table")) {

            //Toast.makeText(ctx, "display_table"+response, Toast.LENGTH_LONG).show();
        }


    }
}
