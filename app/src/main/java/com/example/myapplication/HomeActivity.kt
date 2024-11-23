package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.LayoutHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: LayoutHomeBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.Logoutbutton.setOnClickListener {
            auth.signOut()
            Intent(this@HomeActivity, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val hometoedit: Button = findViewById(R.id.editbutton)
        hometoedit.setOnClickListener {
            // Membuat intent untuk berpindah ke edit
            val intent = Intent(this, editprofile::class.java)
            startActivity(intent)
        }
        val hometosr: ImageButton = findViewById(R.id.srbutton)
        hometosr.setOnClickListener {
            // Intent to open HomeActivity
            val intent = Intent(this@HomeActivity, pengajuansr::class.java)
            startActivity(intent)
        }
        val hometosptjm: ImageButton = findViewById(R.id.sptjmbutton)
        hometosptjm.setOnClickListener {
            // Intent to open HomeActivity
            val intent = Intent(this@HomeActivity, pengajuansptjm::class.java)
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



