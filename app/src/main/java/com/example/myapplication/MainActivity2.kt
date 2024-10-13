package com.example.myapplication

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {

    lateinit var expandableListView: ExpandableListView
    var header :MutableList<String> =ArrayList()
    val childItem: MutableList<MutableList<String>> =ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        expandableListView = findViewById(R.id.expandableListView)

        val header1:MutableList<String> = ArrayList()
        header1.add("childItem 1")
        header1.add("childItem 2")
        header1.add("childItem 3")

        val header2:MutableList<String> = ArrayList()
        header2.add("childItem 1")
        header2.add("childItem 2")
        header2.add("childItem 3")

        header.add("header 1")
        header.add("header 2")

        childItem.add(header1)
        childItem.add(header2)

        expandableListView.setAdapter(ExpandableAdapter(this@MainActivity2,header,childItem))
    }
}