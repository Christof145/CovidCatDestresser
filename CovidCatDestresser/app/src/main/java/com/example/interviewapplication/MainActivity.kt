package com.example.interviewapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    //Ui variables
    private lateinit var getNewCatButton: FloatingActionButton
    private lateinit var getCatInfoButton: FloatingActionButton
    private lateinit var catImageView: ImageView
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNewCatButton = findViewById(R.id.getNewCatButton)
        getCatInfoButton = findViewById(R.id.getCatInfoButton)
        catImageView = findViewById(R.id.catImage)

        getCatImage(resources.getString(R.string.api_url))

        getNewCatButton.setOnClickListener {
            getCatImage(resources.getString(R.string.api_url))
        }
    }

    //
    private fun getCatImage(url: String) {
        requestQueue = Volley.newRequestQueue(this)
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val catData = response.getJSONObject(0)
                val catUrl: String = catData.getString("url")
                Picasso.get().load(catUrl).into(catImageView)
                //On button click send and display data
                getCatInfoButton.setOnClickListener {
                    val breedInfo = catData.getJSONArray("breeds")
                    if (breedInfo.isNull(0)) {
                        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
                    } else {
                        val breedData = breedInfo.getJSONObject(0)
                        //Sends data to selected Intent
                        val intent = Intent(this, Info::class.java)
                        intent.putExtra("name", breedData.getString("name"))
                        intent.putExtra("origin", breedData.getString("origin"))
                        intent.putExtra("description", breedData.getString("description"))
                        intent.putExtra("temperament", breedData.getString("temperament"))
                        intent.putExtra("catUrl", catUrl)
                        startActivity(intent)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
}