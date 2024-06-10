package com.example.fitnessapp.Screens;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.Utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.UUID;

public class AddExerciseActivity extends AppCompatActivity {
            EditText et_exercise_name,et_exercise_description,et_exercise_duration;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        /////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        et_exercise_name =findViewById(R.id.et_exercise_name);
        et_exercise_description =findViewById(R.id.et_exercise_description);
        et_exercise_duration =findViewById(R.id.et_exercise_duration);

    }

    public void saveRecord(View view){
         if(et_exercise_name.getText().toString().isEmpty()){
             et_exercise_name.setError("required");
         }else  if(et_exercise_description.getText().toString().isEmpty()){
             et_exercise_description.setError("required");
         }else  if(et_exercise_duration.getText().toString().isEmpty()){
             et_exercise_duration.setError("required");
         }else {
             try {
                 loadingDialog.show();
                 String id =createFavId().substring(0,8);
                 DatabaseReference databaseReference =  FirebaseDatabase.getInstance()
                         .getReference("UserData")
                         .child(Constant.getUserId(this))
                         .child("ExerciseData").child(id);
                 databaseReference.child("ExerciseId").setValue(id);
                 databaseReference.child("ExerciseTime").setValue(new Date().getTime());
                 databaseReference.child("ExerciseName").setValue(et_exercise_name.getText().toString());
                 databaseReference.child("ExerciseDescription").setValue(et_exercise_description.getText().toString());
                 databaseReference.child("ExerciseDuration").setValue(et_exercise_duration.getText().toString());
                  loadingDialog.dismiss();
                  finish();
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }


         }
    }

    public String createFavId() throws Exception{
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}