package com.nammashale.inventory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_account)

        val etName =
            findViewById<EditText>(R.id.etName)

        val etSchoolId =
            findViewById<EditText>(R.id.etSchoolId)

        val etEmail =
            findViewById<EditText>(R.id.etEmail)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnCreate =
            findViewById<Button>(R.id.btnCreate)

        val btnBack =
            findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }

        btnCreate.setOnClickListener {

            val name =
                etName.text.toString()

            val schoolId =
                etSchoolId.text.toString()

            val email =
                etEmail.text.toString()

            val password =
                etPassword.text.toString()

            if (
                name.isEmpty() ||
                schoolId.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val sharedPref =
                    getSharedPreferences(
                        "UserData",
                        MODE_PRIVATE
                    )

                val editor =
                    sharedPref.edit()

                editor.putString(
                    "schoolId",
                    schoolId
                )

                editor.putString(
                    "email",
                    email
                )

                editor.putString(
                    "password",
                    password
                )

                editor.apply()

                Toast.makeText(
                    this,
                    "Account Created",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )

                finish()
            }
        }
    }
}