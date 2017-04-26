package com.example.sniketn_pc.diabeticmanagement;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MenuOption extends AppCompatActivity {

    private RadioGroup group;
    private Button select, choice;
    private int index;
    private String user_email;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);
        group =(RadioGroup)findViewById(R.id.RadioGroup1);
        select= (Button)findViewById(R.id.Button_select_option);



        //save user email to shared preferences
        preferences = getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        user_email = preferences.getString("email", user_email);
        //Toast.makeText(getBaseContext(),user_email, Toast.LENGTH_LONG).show();


        select.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                index =group.getCheckedRadioButtonId();
                choice =(RadioButton)findViewById(index);

                if (choice.getText().toString().equals("Daily input"))
                {

                    //start dialy input activity
                    Intent mp = new Intent(MenuOption.this,dailyInputActivity.class);
                    startActivity(mp);
                    finish();


                }
                if (choice.getText().toString().equals("View monthly results"))
                {

                    //start view result activity
                    Intent display = new Intent(MenuOption.this,View_results.class);
                    startActivity(display);
                    finish();


                }
                if (choice.getText().toString().equals("Monthly input graph"))
                {

                    //start view result graph activity
                    Intent display = new Intent(MenuOption.this,View_results_graph.class);
                    startActivity(display);
                    finish();
                }
                if (choice.getText().toString().equals("Diabetic details"))
                {

                    //start diabetic details activity
                    Intent display = new Intent(MenuOption.this,Diabetic_details.class);
                    startActivity(display);
                    finish();


                }
                if (choice.getText().toString().equals("Location"))
                {

                    //start Location activity
                    Intent display = new Intent(MenuOption.this,MapsActivity.class);
                    startActivity(display);



                }
                if (choice.getText().toString().equals("Diabetes News"))
                {

                    //start Location activity
                    Intent display = new Intent(MenuOption.this,DiabetesNews.class);
                    startActivity(display);



                }


            }
        });

    }
}
