package com.example.fitnessapp.Screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.Utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    String selectedItem;

    EditText et_user_age,et_exercise_time;
    RadioButton btnHome,btnGym,btnYes,btnNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        et_user_age=findViewById(R.id.et_user_age);
        et_exercise_time=findViewById(R.id.et_exercise_time);

        btnHome=findViewById(R.id.btnHome);
        btnGym=findViewById(R.id.btnGym);
        btnYes=findViewById(R.id.btnYes);
        btnNo=findViewById(R.id.btnNo);
        spinner=findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.item_array));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        // Set a listener to handle item selections
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the spinner
                selectedItem = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });
    }

    public void saveRecord(View view){

        if(et_user_age.getText().toString().isEmpty()){
            et_user_age.setError("required");
        }else if(et_exercise_time.getText().toString().isEmpty()){
            et_exercise_time.setError("required");
        }else {


            DatabaseReference databaseReference =  FirebaseDatabase.getInstance()
                    .getReference("UserData")
                    .child(Constant.getUserId(this))
                    .child("QuestionData").child("0A12B");
            databaseReference.child("UserAge").setValue(et_user_age.getText().toString());
            databaseReference.child("ExerciseTime").setValue(et_exercise_time.getText().toString());
            databaseReference.child("ExerciseGoal").setValue(selectedItem);
            if(btnGym.isChecked()){
                databaseReference.child("DoExercise").setValue("Gym");
            }
            else {
                databaseReference.child("DoExercise").setValue("Home");            }

            if(btnYes.isChecked()){
                databaseReference.child("TrainedBefore").setValue("Yes");
            }
            else {
                databaseReference.child("TrainedBefore").setValue("No");
            }

            finish();


        }


    }
}