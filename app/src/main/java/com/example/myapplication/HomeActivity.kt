package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val hometoedit: Button = findViewById(R.id.editbutton)
        hometoedit.setOnClickListener {
            // Membuat intent untuk berpindah ke edit
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        val hometotemplatebutton: ImageButton = findViewById(R.id.templatebutton)
        hometotemplatebutton.setOnClickListener {
            // Intent to open HomeActivity
            val intent = Intent(this@HomeActivity, Template::class.java)
            startActivity(intent)
        }
        val hometoresult: ImageButton = findViewById(R.id.hasilbutton)
        hometoresult.setOnClickListener {
            // Intent to open HomeActivity
            val intent = Intent(this@HomeActivity, Result::class.java)
            startActivity(intent)
        }
    }
}



