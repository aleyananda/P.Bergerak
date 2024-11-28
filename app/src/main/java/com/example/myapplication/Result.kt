package com.example.myapplication

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Result : AppCompatActivity() {

    private lateinit var downloadManager: DownloadManager

    private val fileUrls = listOf(
        "https://firebasestorage.googleapis.com/v0/b/msibapp-968a5.firebasestorage.app/o/HasilSPTJM.pdf?alt=media&token=eddf0c6a-0e91-44b2-ae37-a8cafc37404a",
        "https://firebasestorage.googleapis.com/v0/b/msibapp-968a5.firebasestorage.app/o/HasilSR.pdf?alt=media&token=c54871c2-b3a6-4c5a-94c7-065b3ebac72b"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.result)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Mencari ImageButton berdasarkan id dan menambahkan OnClickListener
        val imgButton = findViewById<ImageButton>(R.id.imgbtn_home)
        imgButton.setOnClickListener {
            // Membuat intent untuk berpindah ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val btnYellow1 = findViewById<Button>(R.id.btnYellow1)
        val btnYellow2 = findViewById<Button>(R.id.btnYellow2)

        btnYellow1.setOnClickListener { downloadPdf(fileUrls[0], "HasilSPTJM.pdf") }
        btnYellow2.setOnClickListener { downloadPdf(fileUrls[1], "HasilSR.pdf") }
    }



    private fun downloadPdf(downloadUrl: String, fileName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val downloadUri = Uri.parse(downloadUrl)
                val request = DownloadManager.Request(downloadUri).apply {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    setAllowedOverRoaming(false)
                    setTitle(fileName)
                    setMimeType("application/pdf")
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                }

                downloadManager.enqueue(request)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Result, "Downloading $fileName", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Result, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}