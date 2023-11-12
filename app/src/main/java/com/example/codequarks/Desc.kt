package com.example.codequarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Desc : Fragment() {

    val mainActivity: MainActivity = MainActivity()
    val database = Firebase.database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val DescTextView: TextView = mainActivity.findViewById(R.id.Prompt)
        var DescRef = database.getReference("message").child("Desc")
        DescRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                DescTextView.text =
                    value //sets the text view so the description is displayed to the user
            }

            override fun onCancelled(error: DatabaseError) {
                DescTextView.text = "Failed to load data"
            }
        })
        return inflater.inflate(R.layout.fragment_desc, container, false)
    }
}
