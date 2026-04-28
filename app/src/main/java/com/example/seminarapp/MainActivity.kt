package com.example.seminarapp

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!SessionManager.isLogin(this)) {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recyclerSeminar)

        val name = SessionManager.getName(this)

        val seminars = listOf(
            Seminar("Project UI Design", "Weekly Meeting", "04:00 - 05:00", "Completed"),
            Seminar("Mobile Dev", "Sprint Review", "02:00 - 03:00", "Pending"),
            Seminar("Cyber Security", "Workshop", "01:00 - 02:00", "Completed")
        )

        val items = mutableListOf<MainItem>()
        items.add(MainItem.Header(name))
        items.add(MainItem.Feature)
        items.add(MainItem.Upcoming)
        items.add(MainItem.SectionTitle("Jadwal Seminar"))
        items.addAll(seminars.map { MainItem.SeminarItem(it) })

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = MainAdapter(items) {
            startActivity(Intent(this, FormActivity::class.java))
        }
        recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 20
            }
        })

        findViewById<ImageView>(R.id.navLogout).setOnClickListener {

            // Hapus session
            SessionManager.logout(this)

            // Pindah ke LoginActivity dan clear stack
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }
    }
}