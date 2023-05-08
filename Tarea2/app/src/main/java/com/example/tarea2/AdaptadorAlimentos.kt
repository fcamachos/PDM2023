package com.example.tarea2

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class AdaptadorAlimentos(contexto: Context, val alimentos: List<String>) : ArrayAdapter<String>(contexto, android.R.layout.simple_list_item_1, alimentos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vista = super.getView(position, convertView, parent)
        val textView = vista.findViewById<TextView>(android.R.id.text1)

        // Cambiar el tamaño del texto
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F) // Cambiar el tamaño a 20sp

        // Cambiar el color del texto
        textView.setTextColor(ContextCompat.getColor(context, R.color.white)) // Cambiar el color a un color personalizado


        textView.text = alimentos[position]
        return vista
    }
}

