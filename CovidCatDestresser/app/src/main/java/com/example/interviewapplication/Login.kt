package com.example.interviewapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class Login : AppCompatActivity() {
    //Ui variables
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUpText: TextView
    private lateinit var btnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSignIn = findViewById(R.id.buttonSignIn)
        signUpText = findViewById(R.id.signUpText)

        //Go to signup page
        signUpText.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val usernameToPass = username.text
            val passwordToPass = password.text

            //Check filled in username and password
            if (!usernameToPass.isNullOrEmpty() && !passwordToPass.isNullOrEmpty()) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(2)
                    field[0] = "username"
                    field[1] = "password"
                    //Creating array for data
                    val data = arrayOfNulls<String>(2)
                    data[0] = usernameToPass.toString()
                    data[1] = passwordToPass.toString()
                    //Using ip address (same network accessibility), also using PHP api to send and receive data
                    val putData = PutData(
                        "http://192.168.178.72/LoginRegistration/login.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result.equals("Login Success")) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}