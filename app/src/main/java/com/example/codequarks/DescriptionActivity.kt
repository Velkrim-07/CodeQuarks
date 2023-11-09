package com.example.codequarks

import android.app.Activity
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class DescriptionActivity(var MainActivity: Activity, var database: FirebaseDatabase, var path: String) {
    fun onCreateDescription(){
        val DescTextView : TextView = MainActivity.findViewById(R.id.Prompt)
        var DescRef = database.getReference(path).child("Desc")
        DescRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                DescTextView.text = value //sets the text view so the description is displayed to the user
            }
            override fun onCancelled(error: DatabaseError){
                DescTextView.text = "Failed to load data"
            }
        })
    }
}