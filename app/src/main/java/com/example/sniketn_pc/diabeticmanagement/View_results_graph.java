package com.example.sniketn_pc.diabeticmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class View_results_graph extends AppCompatActivity {
    private String method, user_email, year, graphView;
    private double a;
    private int b;
    private int [] x;
    private double [] y;
    private SharedPreferences preferences;
    private Spinner ET_MONTH, ET_YEAR, ET_GRAPH ;
    private ArrayAdapter<CharSequence> adapter, adapter_y, adapter_g;
    private String[] month;
    List<UserInputs> userInputsList = new ArrayList<UserInputs>();
    static JSONObject jObj ,input= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results_graph);
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        preferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        user_email = preferences.getString("email", user_email);
        //Toast.makeText(getBaseContext(),user_email, Toast.LENGTH_LONG).show();
        //spinner for choice month
        ET_MONTH = (Spinner)findViewById(R.id.spinner3);
        adapter = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_MONTH.setAdapter(adapter);
        ET_MONTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month= parent.getSelectedItem().toString().split("-");
                //Toast.makeText(getBaseContext(),parent.getSelectedItem()+ " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner for choice year
        ET_YEAR = (Spinner)findViewById(R.id.spinner4);
        adapter_y = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
        adapter_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_YEAR.setAdapter(adapter_y);
        ET_YEAR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year= parent.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),parent.getSelectedItem()+ " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner for choice graph view
        ET_GRAPH = (Spinner)findViewById(R.id.spinner5);
        adapter_g = ArrayAdapter.createFromResource(this,R.array.graph,android.R.layout.simple_spinner_item);
        adapter_g.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_GRAPH.setAdapter(adapter_g);
        ET_GRAPH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                graphView= parent.getSelectedItem().toString();
                //Toast.makeText(getBaseContext(),parent.getSelectedItem()+ " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button menuOption = (Button)findViewById(R.id.buttonReturn);
        menuOption.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(getBaseContext(),MenuOption.class);
                startActivity(intent);
                finish();

            }
        });

        Button saved = (Button)findViewById(R.id.bViewGraph);
        saved.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                try{
                    //remove all graph
                    graph.removeAllSeries();
                    graph.setTitle("Glucose level/days");
                    //send request data to background task
                    method="view_results";
                    BackgroundTask backgroundTask = new BackgroundTask(getBaseContext());
                    String data =backgroundTask.execute(method,user_email,month[0],year).get();
                    //Toast.makeText(getBaseContext(),data,Toast.LENGTH_LONG).show();
                    //get the jason file object from php
                    jObj = new JSONObject(data);
                    JSONArray inputObject =jObj.getJSONArray("input"); // JSON Array
                    // looping through All inputs
                    y=new double[inputObject.length()];
                    x=new int[inputObject.length()];

                    for (int i = 0; i  <inputObject.length(); i++)
                    {

                        input = inputObject.getJSONObject(i);
                        //input for graph
                        a = Double.parseDouble(input.getString("input_glucose"));
                        b = Integer.parseInt(input.getString("day"));
                        y[i] = a;
                        x[i] = b;
                        //Toast.makeText(getBaseContext(),input.getString("input_glucose"),Toast.LENGTH_LONG).show();


                    }

                    //create x,y for graph
                    DataPoint[] d=new DataPoint[x.length];
                    for (int i=0; i <x.length ; i++)
                    {
                        d[i]= new DataPoint(x[i], y[i]);

                    }
                    //create garph
                    if(graphView.equals("Line Graph"))
                    {
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(d);
                        // styling series
                        series.setTitle("Random Curve 1");
                        series.setColor(Color.RED);
                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(10);
                        series.setThickness(8);
                        graph.addSeries(series);
                    }
                    else if(graphView.equals("Bar Graph"))
                    {
                        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(d);
                        graph.addSeries(series);
                        // styling
                        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                            @Override
                            public int get(DataPoint data) {
                                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                            }
                        });

                        series.setSpacing(50);

                        // draw values on top
                        series.setDrawValuesOnTop(true);
                        series.setValuesOnTopColor(Color.RED);
                        //series.setValuesOnTopSize(50);

                    }

                }
                catch(Exception ex)
                {
                    Log.e("Exception", "This is my user inputs exception" + ex);
                    Toast.makeText(getBaseContext(),"No enough data for graph",Toast.LENGTH_LONG).show();
                }



            }
        });


    }
    //this method is called on the onPause state and stores important data
    @Override
    public void onSaveInstanceState (Bundle outState)
    {

        super.onSaveInstanceState(outState);
        outState.putString("graphView",graphView);

    }
    //this method is called on the onResume state and loads important data
    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState)
    {

        super.onRestoreInstanceState(savedInstanceState);
        graphView =savedInstanceState.getString("graphView");


    }
}
