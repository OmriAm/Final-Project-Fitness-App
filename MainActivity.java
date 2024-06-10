package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.Model.MyExercise;
import com.example.fitnessapp.Screens.AccountActivity;
import com.example.fitnessapp.Screens.AddExerciseActivity;
import com.example.fitnessapp.Screens.ExerciseDetailActivity;
import com.example.fitnessapp.Screens.FavoriteActivity;
import com.example.fitnessapp.Screens.QuestionActivity;
import com.example.fitnessapp.Screens.ViewExerciseActivity;
import com.example.fitnessapp.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     ArrayList<MyExercise> myExerciseArrayList=new ArrayList<MyExercise>();
    RecyclerView recyclerView;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /////loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onStart() {
        getData();
        super.onStart();
    }
    public void getData(){
        loadingDialog.show();
        myExerciseArrayList.clear();
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance()
                .getReference("UserData")
                .child(Constant.getUserId(this))
                .child("ExerciseData");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    myExerciseArrayList.add(new MyExercise(
                            dataSnapshot1.child("ExerciseName").getValue(String.class)
                            ,dataSnapshot1.child("ExerciseDescription").getValue(String.class)
                            ,dataSnapshot1.child("ExerciseDuration").getValue(String.class)
                            ,dataSnapshot1.child("ExerciseTime").getValue(Long.class)
                            ,dataSnapshot1.child("ExerciseId").getValue(String.class)
                    ));
                }

                recyclerView.setAdapter(new ArrayAdapter());
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                loadingDialog.dismiss();
            }
        });

    }


    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHolder> {

        public ArrayAdapter(){
        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_my_exercise,parent,false);
            return  new ArrayAdapter.ImageViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {


            holder.exerciseName.setText("Exercise : "+myExerciseArrayList.get(position).getExerciseName());
            holder.duration.setText("Duration : "+myExerciseArrayList.get(position).getExerciseDuration());
            holder.description.setText("Description : "+myExerciseArrayList.get(position).getExerciseDescription());
               holder.time.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", myExerciseArrayList.get(position).getExerciseTime()));


        }

        @Override
        public int getItemCount() {
            return myExerciseArrayList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {

            TextView exerciseName,duration,description,time;
            CardView cardView;
            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                exerciseName=itemView.findViewById(R.id.exerciseName);
                cardView=itemView.findViewById(R.id.cardView);
                duration=itemView.findViewById(R.id.duration);
                time=itemView.findViewById(R.id.time);
                description=itemView.findViewById(R.id.description);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutUser) {
            Constant.setUserLoginStatus(MainActivity.this,false);

            startActivity(new Intent(MainActivity.this, AccountActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            return true;
        }
        if (id == R.id.fav_exercise) {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            return true;
        }
        if (id == R.id.view_exercise) {
            startActivity(new Intent(MainActivity.this, ViewExerciseActivity.class));
            return true;
        }
        if (id == R.id.view_question) {
            startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addExercise(View view){
        startActivity(new Intent(this, AddExerciseActivity.class));
    }
}