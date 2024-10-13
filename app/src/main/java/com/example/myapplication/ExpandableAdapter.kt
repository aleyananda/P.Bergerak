package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class ExpandableAdapter(
    private val context: Context,
    private val header: MutableList<String>,
    private val childitem: MutableList<MutableList<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return header.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return childitem[p0].size
    }

    override fun getGroup(p0: Int): Any {
        return header[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return childitem[p0][p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_semester, null)
        }

        convertView?.let {
            val itemGroup = it.findViewById<TextView>(R.id.groupHeader)
            itemGroup.text = getGroup(p0) as String
        }

        return convertView!!
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_item_smt, null)
        }

        convertView?.let {
            val childItem = it.findViewById<TextView>(R.id.childItem)
            childItem.text = getChild(p0, p1) as String
        }

        return convertView!!
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}