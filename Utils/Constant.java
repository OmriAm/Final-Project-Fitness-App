package com.example.fitnessapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.fitnessapp.Model.Exercise;
import com.example.fitnessapp.R;

import java.util.ArrayList;

public class Constant {
    public static boolean getUserLoginStatus(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("user", false);
    }
    public static void setUserLoginStatus(Context context , boolean s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("user", s).commit();
    }

    public static String getUsername(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("name","");
    }

    public static void setUsername(Context context , String s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("name", s).commit();
    }
    public static String getUserId(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("id","");
    }
    public static void setUserId(Context context , String s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("id", s).commit();
    }

    public  static ArrayList<Exercise> getExerciseData(){
        ArrayList<Exercise> exerciseArrayList=new ArrayList<Exercise>();
        exerciseArrayList.clear();

        exerciseArrayList.add(new Exercise("Bird-dog","Abs, Back Butt/Hips","No Equipment","Intermediate","ab0012"
        , R.drawable.exercise1
        ,"https://www.acefitness.org/resources/everyone/exercise-library/14/bird-dog/"));
        exerciseArrayList.add(new Exercise("Back Squat","Butt/Hips, Legs Thighs","Barbell","Advance","ab0013",R.drawable.exercise2
        ,"https://www.acefitness.org/resources/everyone/exercise-library/11/back-squat/"));
        exerciseArrayList.add(new Exercise("Cat-Cow","Back/Chest","No Equipment","Beginner","ab0014"
        ,R.drawable.exercise1,"https://www.acefitness.org/resources/everyone/exercise-library/15/cat-cow/"));

        exerciseArrayList.add(new Exercise("Cobra Exercise","Abs/Back","No Equipment","Beginner","ab0015",R.drawable.exercise4
        ,"https://www.acefitness.org/resources/everyone/exercise-library/16/cobra/"));

        exerciseArrayList.add(new Exercise("CKC Parascapular Exercise","Chest/Shoulder","No Equipment","Intermediate","ab0016",R.drawable.exercise5
        ,"https://www.acefitness.org/resources/everyone/exercise-library/259/ckc-parascapular-exercises/"));

        exerciseArrayList.add(new Exercise("Bear Crawl Exercise","Full body/Integrate","No Equipment","Intermediate","ab0017",R.drawable.exercise6
        ,"https://www.acefitness.org/resources/everyone/exercise-library/150/bear-crawl/"));


        return exerciseArrayList;

    }

}
