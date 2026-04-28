package com.example.seminarapp

import android.content.Intent
import android.widget.Toast
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!SessionManager.isLogin(this)) {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
            return
        }

        setContentView(R.layout.activity_form)

        val nameInput = findViewById<TextInputEditText>(R.id.inputName)
        val emailInput = findViewById<TextInputEditText>(R.id.inputEmail)
        val phoneInput = findViewById<TextInputEditText>(R.id.inputPhone)

        val nameLayout = findViewById<TextInputLayout>(R.id.nameLayout)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val phoneLayout = findViewById<TextInputLayout>(R.id.phoneLayout)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val checkAgree = findViewById<CheckBox>(R.id.checkAgree)
        val spinner = findViewById<Spinner>(R.id.spinnerSeminar)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // Spinner data
        val seminars = listOf(
            "UI/UX Design",
            "Mobile Development",
            "Cyber Security",
            "Data Science",
            "Cloud Computing"
        )
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, seminars)

        // REAL-TIME VALIDATION
        nameInput.addTextChangedListener {
            nameLayout.error = if (it.isNullOrEmpty()) "Nama wajib diisi" else null
        }

        emailInput.addTextChangedListener {
            emailLayout.error = if (!it.toString().contains("@")) "Email tidak valid" else null
        }

        phoneInput.addTextChangedListener {
            val text = it.toString()
            phoneLayout.error = when {
                text.isEmpty() -> "Nomor HP wajib diisi"
                !text.matches(Regex("^[0-9]+$")) -> "Hanya angka"
                !text.startsWith("08") -> "Harus diawali 08"
                text.length !in 10..13 -> "Panjang 10-13 digit"
                else -> null
            }
        }

        btnSubmit.setOnClickListener {

            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()

            var valid = true

            if (name.isEmpty()) {
                nameLayout.error = "Nama wajib diisi"
                valid = false
            }

            if (!email.contains("@")) {
                emailLayout.error = "Email tidak valid"
                valid = false
            }

            if (phone.isEmpty() || !phone.startsWith("08") || phone.length !in 10..13) {
                phoneLayout.error = "Nomor HP tidak valid"
                valid = false
            }

            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                valid = false
            }

            if (!checkAgree.isChecked) {
                Toast.makeText(this, "Harus menyetujui data", Toast.LENGTH_SHORT).show()
                valid = false
            }

            if (!valid) return@setOnClickListener

            // DIALOG KONFIRMASI
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah data yang Anda isi sudah benar?")
                .setPositiveButton("Ya") { _, _ ->

                    val gender = findViewById<RadioButton>(
                        radioGroup.checkedRadioButtonId
                    ).text.toString()

                    val intent = Intent(this, ResultActivity::class.java).apply {
                        putExtra("name", nameInput.text.toString())
                        putExtra("email", emailInput.text.toString())
                        putExtra("phone", phoneInput.text.toString())
                        putExtra("gender", gender)
                        putExtra("seminar", spinner.selectedItem.toString())
                    }

                    startActivity(intent)
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }
}