package com.example.interviewapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class SignUp : AppCompatActivity() {
    //Ui variables
    private lateinit var email: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var btnSignUp: Button
    private lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email = findViewById(R.id.email)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSignUp = findViewById(R.id.buttonSignUp)
        loginText = findViewById(R.id.logintext)

        //Go to login page
        loginText.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //SignUp button
        btnSignUp.setOnClickListener {
            val usernameToPass = username.text
            val emailToPass = email.text
            val passwordToPass = password.text

            //Check filled in username and password and email
            if (!usernameToPass.isNullOrEmpty() && !passwordToPass.isNullOrEmpty() && !emailToPass.isNullOrEmpty()) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    val field = arrayOfNulls<String>(3)
                    field[0] = "email"
                    field[1] = "username"
                    field[2] = "password"
                    //Creating array for data
                    val data = arrayOfNulls<String>(3)
                    data[0] = emailToPass.toString()
                    data[1] = usernameToPass.toString()
                    data[2] = passwordToPass.toString()
                    //Using ip address (same network accessibility), also using PHP api to send and receive data
                    val putData = PutData(
                        "http://192.168.178.72/LoginRegistration/signup.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result.equals("Sign Up Success")) {
                                val intent = Intent(this, Login::class.java)
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
