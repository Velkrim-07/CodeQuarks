package com.example.codequarks

import android.app.Activity
import android.widget.Button
import android.widget.TextView
import com.example.codequarks.MainActivity
import com.example.codequarks.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots

class QuizActivity(var MainActivity: Activity, var database: FirebaseDatabase, var path: String) {
    fun onCreateQuiz(){
        val quizTextView : TextView = MainActivity.findViewById(R.id.Prompt)
        var quizRef = database.getReference(path).child("Quiz").child("Prompt")
        quizRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                quizTextView.text = value //sets the text view so the quiz prompt

            }
            override fun onCancelled(error: DatabaseError){
                quizTextView.text = "Failed to load data"
            }
        })
        val Button1 : Button = MainActivity.findViewById(R.id.Choice1)
        val Button2 : Button = MainActivity.findViewById(R.id.Choice2)
        val Button3 : Button = MainActivity.findViewById(R.id.Choice3)
        val Button4 : Button = MainActivity.findViewById(R.id.Choice4)
        Button1.text = "Correct"
        Button2.text = "Wrong"
        Button3.text = "Wrong2"
        Button4.text = "Wrong3"
    }
}