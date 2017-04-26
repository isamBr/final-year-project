package com.example.sniketn_pc.diabeticmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class View_results extends AppCompatActivity {

    private static final String JASON_STRING_KEY = "json_string";
    private String jason_string,year;
    private String[] month;
    private SharedPreferences preferences;
    private String user_email, method;
    private Spinner ET_MONTH, ET_YEAR;
    private ArrayAdapter<CharSequence> adapter, adapter_y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
        //spinner for choice month
        ET_MONTH = (Spinner)findViewById(R.id.spinnerMonth);
        adapter = ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_MONTH.setAdapter(adapter);
        ET_MONTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month= parent.getSelectedItem().toString().split("-");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                month= parent.getSelectedItem().toString().split("-");

            }
        });
        //spinner for choice year
        ET_YEAR = (Spinner)findViewById(R.id.spinnerYear);
        adapter_y = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
        adapter_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ET_YEAR.setAdapter(adapter_y);
        ET_YEAR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year= parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                year= parent.getSelectedItem().toString();
            }
        });

        preferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        user_email = preferences.getString("email", user_email);
        //Toast.makeText(getBaseContext(),user_email, Toast.LENGTH_LONG).show();
        Button getTable = (Button)findViewById(R.id.button);
        getTable.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                method="display_table";
                try{

                    BackgroundTask backgroundTask = new BackgroundTask(getBaseContext());
                    jason_string =backgroundTask.execute(method,user_email,month[0],year).get();
                    if(jason_string.length()==2|| jason_string=="")
                    {
                        Toast.makeText(getBaseContext(),"No Data ",Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        FragmentManager fragmentManager =getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        tableFragment tableFragment2 = tableFragment.newInstance(jason_string);
                        fragmentTransaction.replace(R.id.fragment_container,tableFragment2);
                        fragmentTransaction.commit();
                        /*Intent intent = new Intent(View_results.this,DisplayListView.class);
                        intent.putExtra("jason_data",jason_string);
                        startActivity(intent);*/

                    }

                 }
                catch(Exception ex)
                {
                    Log.e("Exception", "View result exception" + ex);
                }


            }
        });
        Button menu = (Button)findViewById(R.id.buttonReturn);
        menu.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(View_results.this,MenuOption.class);
                startActivity(intent);
                finish();

            }
        });



    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(JASON_STRING_KEY, jason_string);

    }

}
