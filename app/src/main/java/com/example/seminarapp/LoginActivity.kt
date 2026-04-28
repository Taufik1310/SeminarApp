package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val rememberCheck = findViewById<CheckBox>(R.id.rememberCheck)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val goToRegister = findViewById<TextView>(R.id.goToRegister)

        goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        emailInput.addTextChangedListener {
            if (!it.toString().contains("@")) {
                emailInput.error = "Email tidak valid"
            } else {
                emailInput.error = null
            }
        }

        loginButton.setOnClickListener {

            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isEmpty()) {
                emailInput.error = "Email wajib diisi"
                return@setOnClickListener
            }

            if (!email.contains("@")) {
                emailInput.error = "Email tidak valid"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordInput.error = "Password wajib diisi"
                return@setOnClickListener
            }

            // LOGIN CHECK (AMBIL DARI REGISTER)
            val pref = getSharedPreferences("user", MODE_PRIVATE)

            val savedEmail = pref.getString("email", "")
            val savedPassword = pref.getString("password", "")
            val savedName = pref.getString("name", "")

            if (email == savedEmail && password == savedPassword) {

                SessionManager.saveLogin(this, savedName ?: "", email)

                startActivity(Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })

            } else {
                Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}