package com.example.myapplication

import android.app.ProgressDialog
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
import java.util.UUID
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView


class editprofile : AppCompatActivity() {

    lateinit var btnChoose: Button
    lateinit var btnSave: Button
    lateinit var ProfileIv: ImageView
    var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

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

        // Button to open HomeActivity
        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


        btnChoose = findViewById(R.id.buttonChoose)
        btnSave = findViewById(R.id.buttonSave)
        ProfileIv = findViewById(R.id.ProfileIv)

        btnChoose.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Image to Upload"), 0
            )
        }

        btnSave.setOnClickListener {
            if (fileUri != null) {
                uploadImage()
            } else {
                Toast.makeText(
                    applicationContext, "Please Select Image to Upload",
                    Toast.LENGTH_LONG
                ).show()
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
                    Log.e("Exception", "Error: " + e)
                }
            }
        }

        fun uploadImage() {
            if (fileUri != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading Image...")
                progressDialog.setMessage("Processing...")
                progressDialog.show()

                val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                    .child("images")
                ref.putFile(fileUri!!).addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded Successfully", Toast.LENGTH_LONG)
                        .show()
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File Upload Failed...", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

}
