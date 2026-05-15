package com.nammashale.inventory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val tvCreateAccount =
            findViewById<TextView>(R.id.tvCreateAccount)

        tvCreateAccount.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CreateAccountActivity::class.java
                )
            )
        }

        btnLogin.setOnClickListener {

            val email =
                etEmail.text.toString().trim()

            val password =
                etPassword.text.toString().trim()

            val sharedPref =
                getSharedPreferences(
                    "UserData",
                    MODE_PRIVATE
                )

            val savedEmail =
                sharedPref.getString(
                    "email",
                    null
                )

            val savedPassword =
                sharedPref.getString(
                    "password",
                    null
                )

            if (savedEmail == null ||
                savedPassword == null
            ) {

                Toast.makeText(
                    this,
                    "Please create account first",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (
                email.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (
                email == savedEmail &&
                password == savedPassword
            ) {

                Toast.makeText(
                    this,
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Invalid Credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}