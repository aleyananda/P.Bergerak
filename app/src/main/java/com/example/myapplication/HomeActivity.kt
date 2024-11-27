package com.example.myapplication

import android.content.Intent
import com.bumptech.glide.Glide
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.LayoutHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var nameTextView: TextView
    private lateinit var nimTextView: TextView
    private lateinit var jurusanTextView: TextView
    private lateinit var angkatanTextView: TextView
    private lateinit var semesterTextView: TextView
    private lateinit var profileImageView: ImageView

    private lateinit var binding: LayoutHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        // Inisialisasi TextView
        nameTextView = findViewById(R.id.fileNameEdit1)
        nimTextView = findViewById(R.id.fileNameEdit2)
        jurusanTextView = findViewById(R.id.fileNameEdit3)
        angkatanTextView = findViewById(R.id.fileNameEdit4)
        semesterTextView = findViewById(R.id.fileNameEdit5)
        profileImageView = findViewById(R.id.imageView10)


        // Memeriksa apakah uid valid
        if (uid != null) {
            databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            nameTextView.text = "Nama: ${user.nama}"
                            nimTextView.text = "NIM: ${user.nim}"
                            jurusanTextView.text = "Jurusan: ${user.jurusan}"
                            angkatanTextView.text = "Angkatan: ${user.angkatan}"
                            semesterTextView.text = "${user.semester}"

                            // Set image URL menggunakan Glide
                            user.profileImageUrl?.let { imageUrl ->
                                Glide.with(this@HomeActivity)
                                    .load(imageUrl)
                                    .into(profileImageView)
                            }
                        }

                    } else {
                        Toast.makeText(this@HomeActivity, "No data available", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@HomeActivity, "Failed to read data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }


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
            val hometopetunjuk: ImageButton = findViewById(R.id.petunjuk)
            hometopetunjuk.setOnClickListener {
                // Intent to open HomeActivity
                val intent = Intent(this@HomeActivity, Petunjuk::class.java)
                startActivity(intent)
            }

        }
    }




