package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
TextView tvDate,tvBMI,tvOutput;
EditText etWeight, etHeight;
Button btnCalc , btnRe;
float BMI;
String datetime ="";
String msg = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOutput = findViewById(R.id.output);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        etWeight= findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalc);
        btnRe = findViewById(R.id.buttonRe);


        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strWeight = etWeight.getText().toString();
                float weight = Float.parseFloat(strWeight);

                String strHeight = etHeight.getText().toString();
                float height = Float.parseFloat(strHeight);

                BMI = weight/(height*height);

                if(BMI<18.5){
                    msg ="You are underweight";
                }else if(BMI<25){
                   msg="Your BMI is normal";

                }else if(BMI<30){
                    msg="You are overeweight";
                }else{
                    msg="You are obese";
                }

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculated Date:"+datetime);
                tvBMI.setText("Last Calculated BMI:"+BMI);
                etWeight.setText("");
                etHeight.setText("");
                tvOutput.setText(msg);

            }
        });

        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = null;
                BMI = 0;
                datetime = null;
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
                tvOutput.setText(null);
                etWeight.setText(null);
                etHeight.setText(null);


            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putFloat("BMI",BMI);
        prefEdit.putString("datetime",datetime);
        prefEdit.putString("msg",msg);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Float saveBMI = prefs.getFloat("BMI",0);
        String savedate = prefs.getString("datetime","");
        String msg = prefs.getString("msg","");
        tvBMI.setText("Last Calculated BMI:"+Float.toString(saveBMI));
        tvDate.setText("Last Calculated Date:"+savedate);
        tvOutput.setText(msg);


    }


}
