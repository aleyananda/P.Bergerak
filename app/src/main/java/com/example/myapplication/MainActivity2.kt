package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ExpandableListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    lateinit var expandableListView: ExpandableListView
    var header: MutableList<String> = ArrayList()
    val childItem: MutableList<MutableList<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        // Find the ImageButton and set the click listener inside onCreate
        val edit: ImageButton = findViewById(R.id.imageButton4)
        edit.setOnClickListener {
            // Intent to open HomeActivity
            val intent = Intent(this@MainActivity2, HomeActivity::class.java)
            startActivity(intent)
        }
        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            // Membuat intent untuk berpindah ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
