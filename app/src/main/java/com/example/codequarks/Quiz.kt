package com.example.codequarks

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Quiz : Fragment() {
    val database = Firebase.database
    val mainActivity : MainActivity = MainActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val quizTextView : TextView = mainActivity.findViewById(R.id.Prompt)
        val quizRef = database.getReference("message").child("Quiz").child("Prompt")
        quizRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                quizTextView.text = value //sets the text view so the quiz prompt

            }
            override fun onCancelled(error: DatabaseError){
                quizTextView.text = "Failed to load data"
            }
        })

        val Button1 : Button = mainActivity.findViewById(R.id.Choice1)
        val Button2 : Button = mainActivity.findViewById(R.id.Choice2)
        val Button3 : Button = mainActivity.findViewById(R.id.Choice3)
        val Button4 : Button = mainActivity.findViewById(R.id.Choice4)
        Button1.text = "Correct"
        Button2.text = "Wrong"
        Button3.text = "Wrong2"
        Button4.text = "Wrong3"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }
}