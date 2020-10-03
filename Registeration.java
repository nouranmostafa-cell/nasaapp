package com.astrounants.stemnasa2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity {





    private static final String TAG = "Registeration";

    private static final String Key_age = "AGE";
    private static final String Key_gender = "GENDER";
    private static final String Key_weight = "WEIGHT";
    private static final String Key_Height = "HEIGHT";
    private static final String Key_sleepinghours = "SLEEPING HOURS";
    private static final String Key_numbernaps = "NUMBER OF NAPS";
    private static final String Key_napsduration= "NAP'S DURATION";





    EditText EditName;
    EditText EditMission;
    EditText EditAge;
    EditText EditWeight;
    EditText EditHeight;

    RadioGroup radioGroup ;
    RadioButton radioButton;
    String gender;
    String Mission;
    String Name;
    String age;
    String Weight;
    String Height;
    String SleepingHoursValue;
    String NumberNapsValue;
    String DurationNapValue;

    double Calculatecalories;
    String calories ;
    double W;
    int H;
    int A;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        Spinner spinner1 =(Spinner) findViewById(R.id.spinnersleepinghours);
        ArrayAdapter<String> Hours = new ArrayAdapter<String>(Registeration.this,
                android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.usualSleepingHours));
        Hours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(Hours);
       SleepingHoursValue = spinner1.getSelectedItem().toString();

        Spinner spinner2 =(Spinner) findViewById(R.id.numberofnaps);
        ArrayAdapter<String> TimesOfNap = new ArrayAdapter<String>(Registeration.this,
                android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.numberOfNaps));
        TimesOfNap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(TimesOfNap);
       NumberNapsValue  = spinner2.getSelectedItem().toString();

        Spinner spinner3 =(Spinner) findViewById(R.id.DurationOfNap);
        ArrayAdapter<String> DurNap = new ArrayAdapter<String>(Registeration.this,
                android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.DurationNap));
        DurNap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(DurNap);
        DurationNapValue  = spinner3.getSelectedItem().toString();

        radioGroup = findViewById(R.id.gender);
        EditName = findViewById(R.id.Name);
        EditMission = findViewById(R.id.Mission);
        EditAge = findViewById(R.id.age);
        EditWeight= findViewById(R.id.weight);
        EditHeight= findViewById(R.id.height) ;

    }

    public void openSchedule(){

        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }



    public void selectGender(View v){
        int radioid= radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);
       gender= radioButton.getText().toString();

    }

    public void Register(View v){

         age = EditAge.getText().toString();
         Name = EditName.getText().toString();
         Mission = EditMission.getText().toString();
        Weight = EditWeight.getText().toString();
        Height = EditHeight.getText().toString();

        //calculating Calories :

        W = Double.parseDouble(Weight);
        H =Integer.parseInt(Height);
        A = Integer.parseInt(age);
        if(gender=="Female"){
            Calculatecalories =  655 + (9.6 * W) + (1.7 * H) - (4.7 * A) ;
        }
        else{
            Calculatecalories = 66 + (13.7 * W) + (5 * H) - (6.8 * A) ;
        }
        calories = String.valueOf(Calculatecalories);

        //Adding Data to firebase

        Map<String, Object> data = new HashMap<>();

        data.put(Key_age, age);
        data.put(Key_weight, Weight);
        data.put(Key_Height, Height);
        data.put(Key_gender, gender);
        data.put(Key_sleepinghours, SleepingHoursValue);
       data.put(Key_numbernaps, NumberNapsValue);
        data.put(Key_napsduration,DurationNapValue);

        db.collection(Mission).document(Name).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Registeration.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        //openSchedule();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registeration.this, "Cannot Connect", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });



    }


}