package com.example.fitnessapp.Screens;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.Model.Exercise;
import com.example.fitnessapp.R;
import com.example.fitnessapp.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    ArrayList<Exercise> exerciseArrayList=new ArrayList<Exercise>();
    RecyclerView recyclerView;
    private Dialog loadingDialog;
    ArrayList<String> exerciseIdList=new ArrayList<String>();
    ArrayList<Exercise> favoriteExercises = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
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
        exerciseArrayList.clear();
        exerciseIdList.clear();
        favoriteExercises.clear();
        exerciseArrayList= Constant.getExerciseData();
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance()
                .getReference("UserData")
                .child(Constant.getUserId(this))
                .child("FavExerciseData");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    exerciseIdList.add(dataSnapshot1.child("ExerciseId").getValue(String.class));
                }
                // Filter exerciseArrayList to contain only favorite exercises

                for (Exercise exercise : exerciseArrayList) {
                    if (exerciseIdList.contains(exercise.getExerciseId())) {
                        favoriteExercises.add(exercise);
                    }
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
            View v= LayoutInflater.from(FavoriteActivity.this)
                    .inflate(R.layout.item_exercise,parent,false);
            return  new ArrayAdapter.ImageViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {


            holder.btn_add_fav.setVisibility(View.GONE);
            holder.btn_remove_fav.setVisibility(View.VISIBLE);
            holder.exerciseName.setText("Exercise : "+favoriteExercises.get(position).getExerciseName());
            holder.equipments.setText("Equipments : "+favoriteExercises.get(position).getEquipments());
            holder.level.setText("Level : "+favoriteExercises.get(position).getLevel());
            holder.bodyParts.setText("BodyParts : "+favoriteExercises.get(position).getBodyParts());
            holder.exercise_img.setImageDrawable(getResources().getDrawable(favoriteExercises.get(position).getExerciseImg()));


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(FavoriteActivity.this,ExerciseDetailActivity.class)
                            .putExtra("url",favoriteExercises.get(position).getLink()));
                }
            });

            holder.btn_remove_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeFavExercise(favoriteExercises.get(position).getExerciseId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return favoriteExercises.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {

            TextView exerciseName,bodyParts,equipments,level;
            CardView cardView;
            ImageView btn_remove_fav,btn_add_fav,exercise_img;
            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                exerciseName=itemView.findViewById(R.id.exerciseName);
                cardView=itemView.findViewById(R.id.cardView);
                bodyParts=itemView.findViewById(R.id.bodyParts);
                level=itemView.findViewById(R.id.level);
                equipments=itemView.findViewById(R.id.equipments);
                btn_remove_fav=itemView.findViewById(R.id.btn_remove_fav);
                btn_add_fav=itemView.findViewById(R.id.btn_add_fav);
                exercise_img=itemView.findViewById(R.id.exercise_img);
            }
        }
    }




    public void removeFavExercise(String exerciseId){
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance()
                .getReference("UserData")
                .child(Constant.getUserId(this))
                .child("FavExerciseData").child(exerciseId);
        databaseReference.removeValue();
        getData();
    }
}