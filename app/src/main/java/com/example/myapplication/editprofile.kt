package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.myapplication.databinding.ActivityEditprofileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class editprofile : AppCompatActivity() {

    private lateinit var binding: ActivityEditprofileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri

    lateinit var btnChoose: Button
    lateinit var btnSave: Button
    lateinit var ProfileIv: ImageView
    var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.buttonSave.setOnClickListener {
            val nama = binding.nameEt.text.toString()
            val nim = binding.nimEt.text.toString()
            val jurusan = binding.jurusanEt.text.toString()
            val angkatan = binding.angkatanEt.text.toString()
            val semester = binding.autoComplete.text.toString()

            if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty() || angkatan.isEmpty() || semester.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fileUri == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(nama, nim, jurusan, angkatan, semester)

            val uid = auth.currentUser?.uid
            if (uid != null) {
                // Mulai proses upload gambar terlebih dahulu
                storageReference = FirebaseStorage.getInstance()
                    .getReference("Users/$uid/profile_image.jpg")

                val uploadTask = storageReference.putFile(fileUri!!)
                uploadTask.addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()

                        // Perbarui data pengguna di Firebase Database setelah gambar diunggah
                        user.profileImageUrl = imageUrl // Tambahkan URL gambar ke data pengguna
                        databaseReference.child(uid).setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()

                                // Pindah ke HomeActivity setelah semua selesai
                                val intent = Intent(this@editprofile, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Failed to update profile data", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val items = listOf("Semester 1","Semester 2","Semester 3","Semester 4",
            "Semester 5","Semester 6","Semester 7","Semester 8")

        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete)
        val adapter = ArrayAdapter(this,R.layout.list_item,items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(this,"Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }

        // ImageButton to open HomeActivity
        val edit: ImageButton = findViewById(R.id.imageButton4)
        edit.setOnClickListener {
            val intent = Intent(this@editprofile, HomeActivity::class.java)
            startActivity(intent)
        }

        btnChoose = findViewById(R.id.buttonChoose)
        btnSave = findViewById(R.id.buttonSave)
        ProfileIv = findViewById(R.id.ProfileIv)

        // Image selection
        btnChoose.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Image to Upload"), 0
            )
        }
    }


    private fun uploadProfilePic() {
        if (fileUri != null) {
            storageReference = FirebaseStorage.getInstance()
                .getReference("Users/" + auth.currentUser?.uid + "/profile_image.jpg")

            storageReference.putFile(fileUri!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        updateImageUrl(imageUrl)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this@editprofile, "Failed to upload the image", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this@editprofile, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateImageUrl(imageUrl: String) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            databaseReference.child(uid).child("profileImageUrl")
                .setValue(imageUrl).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Profile image updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to update image URL", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
                ProfileIv.setImageBitmap(bitmap)

            } catch (e: Exception) {
                Log.e("onActivityResult", "Error loading image", e)
            }
        } else {
            Log.w("onActivityResult", "No image selected")
        }
    }
}

