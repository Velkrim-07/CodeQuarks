package com.example.codequarks

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.nfc.Tag
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.codequarks.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var button: Button
    lateinit var ImageView: ImageView
    val REQUEST_IMAGE_CAPTURE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Button, Textview, and Database declaration
        val anotherButton: Button = findViewById(R.id.displayButton) //Button to display the data in the database
        val myTextView = findViewById<TextView>(R.id.textView) //TextView to show the entry in the database
        val database = Firebase.database //Database connection

        val myList = listOf("Variable", "Classes", "Functions", "Overview", "message") //List of all the keys for the database entries
        val randomIndex = Random.nextInt(myList.size) //random index of the array to apply randomness
        val randomElement = myList[randomIndex] //Get the random key from the array
        val myRef = database.getReference(randomElement).child("Desc") //get the entry from the database

        anotherButton.setOnClickListener {// Displays the Description of the topic to the user
            myRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue<String>()
                    myTextView.text = value
                }
                override fun onCancelled(error: DatabaseError){
                    myTextView.text = "Failed to load data"
                }
            })
        }


        ImageView = findViewById(R.id.imageSave) //Grabs the image view with the id
        button = findViewById(R.id.CameraButton) //Button for taking a picture

        button.setOnClickListener {//When the button is clicked initiate the camera
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException){
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap //save the data as a bitmap to be plotted onto the image view
            ImageView.setImageBitmap(imageBitmap) //Plot the bitmap on the image view
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}