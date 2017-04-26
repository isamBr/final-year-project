package com.example.sniketn_pc.diabeticmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {
    String jason_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    UserInputAdapter userInputAdapter;
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listview_layout);
        listView =(ListView)findViewById(R.id.listView);
        userInputAdapter = new UserInputAdapter(this,R.layout.row_layout);
        listView.setAdapter(userInputAdapter);
        jason_string = getIntent().getExtras().getString("jason_data");
        Toast.makeText(getBaseContext(),jason_string,Toast.LENGTH_LONG).show();
        try {
            jsonObject = new JSONObject(jason_string);
            jsonArray =jsonObject.getJSONArray("input");
            int count =0 ;
            String day,time,input_glucose,input_insulin,input_note;
            UserInputs inputs =new UserInputs("day "," time "," input_glucose "," input_insulin "," input_note ");
            userInputAdapter.add(inputs);
            while (count <jsonArray.length())
            {
                JSONObject o =jsonArray.getJSONObject(count);
                day=o.getString("day");
                time =o.getString("time");
                input_glucose=o.getString("input_glucose");
                input_insulin=o.getString("input_insulin");
                input_note=o.getString("input_note");
                inputs =new UserInputs(day,time,input_glucose,input_insulin,input_note);
                userInputAdapter.add(inputs);
                count ++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
