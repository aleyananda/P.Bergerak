package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button and set the click listener
        val loginToTemplateButton: Button = findViewById(R.id.logintotemplate)
        loginToTemplateButton.setOnClickListener {
            // Intent to open TemplateActivity
            val intent = Intent(this@MainActivity, Template::class.java)
            startActivity(intent)
        }
    }
}
