package com.example.codequarks

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.codequarks.databinding.ActivityMainBinding
import com.example.codequarks.ui.home.HomeFragmentDirections
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var cameraButton: Button
    lateinit var displayButton: Button
    lateinit var ImageView: ImageView
    val REQUEST_IMAGE_CAPTURE = 100
    var randomElement: String = ""
    val database = Firebase.database //Database connection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavView

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
        displayButton =
            findViewById(R.id.menu_Description_Button) //Button to display the data in the database
        //TextView to show the entry in the database

        val myList = listOf(
            "Variable",
            "Classes",
            "Functions",
            "Overview",
            "message"
        ) //List of all the keys for the database entries
        val randomIndex =
            Random.nextInt(myList.size) //random index of the array to apply randomness
        randomElement = myList[randomIndex] //Get the random key from the array
        val myRef = database.getReference(randomElement)
            .child("Desc")//get the description of the entry from the database

        //ImageView = findViewById(R.id.imageSave) //Grabs the image view with the id
        cameraButton = findViewById(R.id.menu_Camera_Button) //Button for taking a picture

        cameraButton.setOnClickListener {//When the button is clicked initiate the camera
            setContentView(R.layout.fragment_camera)
            /* val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException){
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }*/
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap =
                data?.extras?.get("data") as Bitmap //save the data as a bitmap to be plotted onto the image view
            ImageView.setImageBitmap(imageBitmap) //Plot the bitmap on the image view
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun goToDesc(view: View) {
        /*var navController : NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_home) as? NavHostFragment
        navHostFragment?.let{navHostFragment ->
            navController = NavHostFragment.findNavController(navHostFragment)
            val action = HomeFragmentDirections.actionNavigationHomeToDesc()
            if (navController.currentDestination != null) {
                navController.navigate(action)
            }
            else
            {
                displayButton.text = "Fuck You"
            }
        } ?: run {

        }*/
        setContentView(R.layout.fragment_desc)
        val DescTextView: TextView = findViewById(R.id.Desc)
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
    }

    fun goToQuiz(view: View) {
        setContentView(R.layout.fragment_quiz)
        val quizTextView: TextView = findViewById(R.id.Prompt)
        val quizRef = database.getReference("message").child("Quiz").child("Prompt")
        quizRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                quizTextView.text = value //sets the text view so the quiz prompt

            }

            override fun onCancelled(error: DatabaseError) {
                quizTextView.text = "Failed to load data"
            }
        })

        val Button1: Button = findViewById(R.id.Choice1)
        val Button2: Button = findViewById(R.id.Choice2)
        val Button3: Button = findViewById(R.id.Choice3)
        val Button4: Button = findViewById(R.id.Choice4)
        Button1.text = "Correct"
        Button2.text = "Wrong"
        Button3.text = "Wrong2"
        Button4.text = "Wrong3"
    }

    fun goToCamera(view: View) {
        setContentView(R.layout.fragment_camera)

        ImageView = findViewById(R.id.imageSave) //Grabs the image view with the id
        val takePicture: Button = findViewById(R.id.Camera) //Button for taking a picture

        takePicture.setOnClickListener {//When the button is clicked initiate the camera
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        fun goBackHome(view: View) {
            setContentView(R.layout.activity_main)
        }

    }
}