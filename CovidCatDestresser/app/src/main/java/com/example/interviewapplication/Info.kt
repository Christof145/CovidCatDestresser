package com.example.interviewapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class Info : AppCompatActivity() {
    //Ui variables
    private lateinit var catImage: ImageView
    private lateinit var catName: TextView
    private lateinit var catOrigin: TextView
    private lateinit var catDescription: TextView
    private lateinit var catTemperament: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //Get sent data from MainActivity
        val data = intent

        catImage = findViewById(R.id.catImage)
        catName = findViewById(R.id.catName)
        catOrigin = findViewById(R.id.catOrigin)
        catDescription = findViewById(R.id.catDescription)
        catTemperament = findViewById(R.id.catTemperament)

        //Set data to received data
        Picasso.get().load(data.getStringExtra("catUrl")).into(catImage)
        catName.text = data.getStringExtra("name")
        catOrigin.text = data.getStringExtra("origin")
        catDescription.text = data.getStringExtra("description")
        catTemperament.text = data.getStringExtra("temperament")
    }
}