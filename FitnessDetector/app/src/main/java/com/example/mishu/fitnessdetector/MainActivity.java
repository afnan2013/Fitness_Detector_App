package com.example.mishu.fitnessdetector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText weight,height;
    private TextView result,normalBmi;
    private Button calculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText)findViewById(R.id.et_weight);
        height = (EditText)findViewById(R.id.et_height);
        result = (TextView)findViewById(R.id.tv_bmi);
        normalBmi = (TextView)findViewById(R.id.tv_normalBMI);
        calculate = (Button)findViewById(R.id.btn_bmi);

        normalBmi.setVisibility(View.INVISIBLE);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heightStr = height.getText().toString();
                String weightStr = weight.getText().toString();
                calculateBMI(heightStr,weightStr);
                normalBmi.setVisibility(View.VISIBLE);
            }
        });


    }


    public void calculateBMI(String heightStr,String weightStr) {


        if ( !("".equals(heightStr) && "".equals(weightStr))) {
            double heightValue = Double.parseDouble(heightStr) * .3048;
            double weightValue = Double.parseDouble(weightStr);

            double bmi = weightValue / (heightValue * heightValue);
            float bmif = (float)bmi;
            displayBMI(bmif);
        }
    }

    private void displayBMI(float bmi) {
        String bmiLabel = "";

        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "very_severely_underweight";
        } else if (Float.compare(bmi, 15f) > 0  &&  Float.compare(bmi, 16f) <= 0) {
            bmiLabel = "severely_underweight";
        } else if (Float.compare(bmi, 16f) > 0  &&  Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = "underweight";
        } else if (Float.compare(bmi, 18.5f) > 0  &&  Float.compare(bmi, 25f) <= 0) {
            bmiLabel = "normal";
        } else if (Float.compare(bmi, 25f) > 0  &&  Float.compare(bmi, 30f) <= 0) {
            bmiLabel = "overweight";
        } else if (Float.compare(bmi, 30f) > 0  &&  Float.compare(bmi, 35f) <= 0) {
            bmiLabel = "obese_class_i";
        } else if (Float.compare(bmi, 35f) > 0  &&  Float.compare(bmi, 40f) <= 0) {
            bmiLabel = "obese_class_ii";
        } else {
            bmiLabel = "obese_class_iii";
        }

        bmiLabel = "BMI Result : "+bmi + "\n\n" +"Status: "+bmiLabel;
        result.setText(bmiLabel);
    }
}
