package com.example.sniketn_pc.diabeticmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sniketn-Pc on 25/03/2017.
 */

public class UserInputAdapter extends ArrayAdapter {

    List list = new ArrayList();
    public UserInputAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(UserInputs object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        UserInputsHolder userInputsHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            userInputsHolder = new UserInputsHolder();
            userInputsHolder.tx_day =(TextView)row.findViewById(R.id.tx_day);
            userInputsHolder.tx_time =(TextView)row.findViewById(R.id.tx_time);
            userInputsHolder.tx_input_glucose =(TextView)row.findViewById(R.id.tx_input_glucose);
            userInputsHolder.tx_input_insulin=(TextView)row.findViewById(R.id.tx_input_insulin);
            userInputsHolder.tx_input_note =(TextView)row.findViewById(R.id.tx_input_note);
            row.setTag(userInputsHolder);
        }
        else
        {
            userInputsHolder = (UserInputsHolder)row.getTag();
        }

        UserInputs userInputs = (UserInputs)this.getItem(position);
        userInputsHolder.tx_day.setText(userInputs.getDay());
        userInputsHolder.tx_time.setText(userInputs.getTime());
        userInputsHolder.tx_input_glucose.setText(userInputs.getInput_glucose());
        userInputsHolder.tx_input_insulin.setText(userInputs.getInput_insulin());
        userInputsHolder.tx_input_note.setText(userInputs.getInput_note());

        return row;
    }

    static class UserInputsHolder
    {
        TextView tx_day,tx_time,tx_input_glucose,tx_input_insulin,tx_input_note;

    }
}
