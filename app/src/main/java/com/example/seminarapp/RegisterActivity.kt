package com.example.seminarapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val confirmInput = findViewById<TextInputEditText>(R.id.confirmPasswordInput)

        val nameLayout = findViewById<TextInputLayout>(R.id.nameLayout)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val confirmLayout = findViewById<TextInputLayout>(R.id.confirmLayout)

        val genderGroup = findViewById<RadioGroup>(R.id.genderGroup)
        val hobbyGroup = findViewById<ChipGroup>(R.id.hobbyGroup)
        val spinner = findViewById<Spinner>(R.id.spinner)

        val btnRegister = findViewById<Button>(R.id.registerButton)
        val goToLogin = findViewById<TextView>(R.id.goToLogin)

        // Spinner Data
        val list = arrayOf("Mahasiswa", "Dosen", "Umum")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)

        emailInput.addTextChangedListener {
            if (!it.toString().contains("@")) {
                emailInput.error = "Email tidak valid"
            } else {
                emailInput.error = null
            }
        }

        btnRegister.setOnClickListener {

            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val pass = passwordInput.text.toString()
            val confirm = confirmInput.text.toString()

            var isValid = true

            // RESET ERROR
            nameLayout.error = null
            emailLayout.error = null
            passwordLayout.error = null
            confirmLayout.error = null

            // VALIDASI
            if (name.isEmpty()) {
                nameLayout.error = "Nama wajib diisi"
                isValid = false
            }

            if (!email.contains("@")) {
                emailLayout.error = "Email tidak valid"
                isValid = false
            }

            if (pass.length < 6) {
                passwordLayout.error = "Minimal 6 karakter"
                isValid = false
            }

            if (pass != confirm) {
                confirmLayout.error = "Password tidak sama"
                isValid = false
            }

            if (genderGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (hobbyGroup.checkedChipIds.isEmpty()) {
                Toast.makeText(this, "Pilih minimal 1 hobi", Toast.LENGTH_SHORT).show()
                isValid = false
            }

            if (!isValid) return@setOnClickListener

            // SIMPAN DATA
            SessionManager.saveLogin(this, name, email)

            val pref = getSharedPreferences("user", MODE_PRIVATE)
            pref.edit().apply {
                putString("email", email)
                putString("password", pass)
                putString("name", name)
                apply()
            }

            Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()

            finish()
        }

        goToLogin.setOnClickListener {
            finish()
        }
    }
}