package com.example.sniketn_pc.diabeticmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class tableFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String JASON_STRING = "json_string";
    private Context context;
    private String jason_string;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private UserInputAdapter userInputAdapter;
    private ListView listView ;
    View view =getView();

    public static tableFragment newInstance(String json_string) {
        tableFragment fragment = new tableFragment();
        Bundle args = new Bundle();
        args.putString(JASON_STRING, json_string);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();//for toast message
        Bundle args = getArguments();
        jason_string = args.getString(JASON_STRING);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.display_listview_layout, container, false);
        listView =(ListView)view.findViewById(R.id.listView);
        userInputAdapter = new UserInputAdapter(view.getContext(),R.layout.row_layout);
        listView.setAdapter(userInputAdapter);
        String day, time, input_glucose, input_insulin, input_note;
        UserInputs inputs = new UserInputs("day ", " time ", " input_glucose ", " input_insulin ", " input_note ");
        userInputAdapter.add(inputs);

        try {
            jsonObject = new JSONObject(jason_string);
            jsonArray =jsonObject.getJSONArray("input");
            int count =0 ;
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




        return view;


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(JASON_STRING, jason_string);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }





}
