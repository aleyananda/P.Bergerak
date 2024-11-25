package com.example.myapplication

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.annotation.SuppressLint
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

class Template : AppCompatActivity() {

    private lateinit var downloadManager: DownloadManager

    private val fileUrls = listOf(
        "https://firebasestorage.googleapis.com/v0/b/msibapp-968a5.firebasestorage.app/o/TemplateSPTJM.pdf?alt=media&token=905eaf44-9275-4b5f-bb92-87e6072d0922",
        "https://firebasestorage.googleapis.com/v0/b/msibapp-968a5.firebasestorage.app/o/TemplateSR.pdf?alt=media&token=2c72bb2f-bb4e-400a-a80b-1b66643272c3",
        "https://firebasestorage.googleapis.com/v0/b/msibapp-968a5.firebasestorage.app/o/TemplateSOP.pdf?alt=media&token=e72a448e-c83d-4174-93f7-0cf3746ab906"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_template)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.template)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgButton = findViewById<ImageButton>(R.id.imgbuttonHome)
        imgButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadBtn1 = findViewById<Button>(R.id.downloadBtn1)
        val downloadBtn2 = findViewById<Button>(R.id.downloadBtn2)
        val downloadBtn3 = findViewById<Button>(R.id.downloadBtn3)

        downloadBtn1.setOnClickListener { downloadPdf(fileUrls[0], "TemplateSPTJM.pdf") }
        downloadBtn2.setOnClickListener { downloadPdf(fileUrls[1], "TemplateSR.pdf") }
        downloadBtn3.setOnClickListener { downloadPdf(fileUrls[2], "TemplateSOP.pdf") }
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
                    Toast.makeText(this@Template, "Downloading $fileName", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Template, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}