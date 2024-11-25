package com.example.myapplication

data class PdfFile(val fileName : String , val downloadUrl : String){
    constructor() : this("","")
}
