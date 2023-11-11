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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Quiz.newInstance] factory method to
 * create an instance of this fragment.
 */
class Quiz : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val database = Firebase.database
    val mainActivity : MainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Quiz.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Quiz().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}