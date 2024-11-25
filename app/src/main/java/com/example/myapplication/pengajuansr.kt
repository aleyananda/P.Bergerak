package com.example.myapplication

import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.myapplication.databinding.ActivityPengajuansrBinding

class pengajuansr : AppCompatActivity() {

    private lateinit var binding: ActivityPengajuansrBinding
    private var pdfFileUris = arrayOfNulls<Uri>(4)
    private var currentFileIndex: Int = -1
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pengajuansr)

        init()
        initClickListeners()

    }

    private fun init() {
        binding = ActivityPengajuansrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageReference = FirebaseStorage.getInstance().reference.child("sr")
        databaseReference = FirebaseDatabase.getInstance().reference.child("sr")
    }

    private fun initClickListeners() {
        binding.uploadBtnsr.setOnClickListener { currentFileIndex = 0
            launcher.launch("application/pdf")
        }
        binding.uploadBtn2sr.setOnClickListener {
            currentFileIndex = 1
            launcher.launch("application/pdf")
        }
        binding.uploadBtn3sr.setOnClickListener {
            currentFileIndex = 2
            launcher.launch("application/pdf")
        }
        binding.uploadBtn4sr.setOnClickListener {
            currentFileIndex = 3
            launcher.launch("application/pdf")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sr)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mencari ImageButton berdasarkan id dan menambahkan OnClickListener
        val imgButton = findViewById<ImageButton>(R.id.btnBackSR)
        imgButton.setOnClickListener {
            // Membuat intent untuk berpindah ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        // Tombol upload
        binding.uploadBtn5sr.setOnClickListener {
            if (pdfFileUris.all { it != null }) { // Periksa apakah semua file sudah dipilih
                uploadAllFilesToFirebase()
            } else {
                Toast.makeText(this, "Please complete all Files first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                // Tentukan file yang dipilih berdasarkan tombol yang diklik
                val selectedFileIndex = currentFileIndex // Variabel untuk melacak tombol yang diklik
                pdfFileUris[selectedFileIndex] = uri

                val fileName = DocumentFile.fromSingleUri(this, uri)?.name
                when (selectedFileIndex) {
                    0 -> binding.fileName1sr.text = fileName ?: "No file selected"
                    1 -> binding.fileName2sr.text = fileName ?: "No file selected"
                    2 -> binding.fileName3sr.text = fileName ?: "No file selected"
                    3 -> binding.fileName4sr.text = fileName ?: "No file selected"
                }
            }
        }
    private fun uploadAllFilesToFirebase() {
        // Upload setiap file ke Firebase
        pdfFileUris.forEachIndexed { index, uri ->
            val fileName = "PDF_${index + 1}_${System.currentTimeMillis()}"
            val mStorageRef = storageReference.child(fileName)

            uri?.let {
                mStorageRef.putFile(it).addOnSuccessListener {
                    mStorageRef.downloadUrl.addOnSuccessListener { downloadUri ->

                        val pdfFile = PdfFile(fileName, downloadUri.toString())
                        databaseReference.push().setValue(pdfFile)
                            .addOnSuccessListener {
                                Toast.makeText(this, "PDF $index uploaded successfully", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { err ->
                                Toast.makeText(this, err.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                    }
                }.addOnFailureListener { err ->
                    Toast.makeText(this, err.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

