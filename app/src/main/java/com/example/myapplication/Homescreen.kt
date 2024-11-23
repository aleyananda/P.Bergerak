package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton


class Homescreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homescreen)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homescreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Find the ImageButton and set the click listener
        val homeToMainButton: ImageButton = findViewById(R.id.hometomain)
        homeToMainButton.setOnClickListener {
            // Intent to open MainActivity
            val intent = Intent(this@Homescreen, Register::class.java)
            startActivity(intent)
        }
    }
}
