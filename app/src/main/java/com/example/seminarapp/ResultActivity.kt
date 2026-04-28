package com.example.seminarapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!SessionManager.isLogin(this)) {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
            return
        }

        setContentView(R.layout.activity_result)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val gender = intent.getStringExtra("gender")
        val seminar = intent.getStringExtra("seminar")

        bindRow(R.id.itemName, "Nama", name)
        bindRow(R.id.itemEmail, "Email", email)
        bindRow(R.id.itemPhone, "No HP", phone)
        bindRow(R.id.itemGender, "Jenis Kelamin", gender)
        bindRow(R.id.itemSeminar, "Seminar", seminar)

        findViewById<MaterialButton>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun bindRow(viewId: Int, title: String, value: String?) {
        val view = findViewById<View>(viewId)

        val titleTv = view.findViewById<TextView>(R.id.title)
        val valueTv = view.findViewById<TextView>(R.id.value)

        titleTv.text = title
        valueTv.text = value ?: "-"
    }
}