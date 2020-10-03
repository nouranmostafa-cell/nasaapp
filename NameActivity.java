package com.astrounants.stemnasa2020;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NameActivity extends AppCompatActivity {



    private static final String TAG = "NameActivity";
    private static final String Key_Name = "name";
    private static final String Key_MissionName = "Mission Name";
    public static final String passWeight = "passWeight";


    String weigth;

    EditText EditName;
    EditText EditMission;

    String Mission;
    String Name;

    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);


        EditName = findViewById(R.id.personName);
        EditMission = findViewById(R.id.missionname);

    }
public void openDialog(){

        ExampleDialog  exampleDialog = new ExampleDialog();
    exampleDialog.show(getSupportFragmentManager(),"exampleDialog");

}
public void openSchedule(){

   Intent intent = new Intent(this, Schedule.class);
     //Intent intent = new Intent(this, Register.class);

    intent.putExtra(passWeight, weigth);
    startActivity(intent);
}

/** public void update(View v){



        Map<String, Object> data = new HashMap<>();
        data.put(Key_Name, Name);
        data.put(Key_Weight, Weight);

        DataRef.set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NameActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NameActivity.this, "Cannot Connect", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

    }**/



    public void Go(View v){

        Name=  EditName.getText().toString();
        Mission =  EditMission.getText().toString();
        DocumentReference DataRef = db.collection(Mission).document(Name);

        DataRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){

                            Toast.makeText(NameActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                            // get weigth and display it on next screen
                             weigth = documentSnapshot.getString("Weight");
                             openSchedule();


                        }else {
                            Toast.makeText(NameActivity.this, "Info doesn't exist", Toast.LENGTH_SHORT).show();
                            //show dialog
                               openDialog();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    public  void  registerFirst(View v){


         openRegister();
    }
    public void openRegister(){
    Intent intent = new Intent(this, Registeration.class);
    startActivity(intent);}
}