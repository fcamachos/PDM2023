package com.example.unamordida.ui.home

import DatabaseHelper
import DatabaseHelper.Companion.TABLE_NAME
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unamordida.R
import com.example.unamordida.databinding.FragmentHomeBinding
import java.util.*

private const val COLUMN_NAME = "name"
private const val COLUMN_PRICE = "price"
private const val COLUMN_DESCRIPTION = "description"
private const val COLUMN_IMAGE_RESOURCE = "image_resource"


class HomeFragment : Fragment(), ItemAdapter.OnItemClickListener {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var itemList: ArrayList<Item>

    lateinit var imageId: Array<Int>
    lateinit var name: Array<String>
    lateinit var price: Array<String>
    lateinit var description: Array<String>

    lateinit var layoutManager: LinearLayoutManager

    private lateinit var dbHelper : DatabaseHelper

    private var indexItemRV = 0
    private var topViewRV = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp)

        dbHelper = DatabaseHelper(requireContext())
        dbHelper.writableDatabase // Esto activará el método onCreate() si la base de datos no existe
        return root
    }

    private fun scrollToPosition(position: Int, offset: Int = 0){
        recyclerView.stopScroll()
        (recyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(position,offset)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ItemAdapter(itemList,this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(item: Item) {
        // Lógica del click en el botón
        Log.d("HomeFragment", "Agregando a carrito")
        addToCart(item)
    }

    override fun addToCart(item: Item) {
        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, item.name)
            put(COLUMN_PRICE, item.price)
            put(COLUMN_DESCRIPTION, item.description)
            put(COLUMN_IMAGE_RESOURCE, item.image)
        }

        val newRowId = db.insert("carrito_bd", null, values)

        if (newRowId != -1L) {
            // El item se agregó correctamente al carrito de compras
            Log.d("HomeFragment", "El item se agregó correctamente al carrito de compras: ${item.name}")
        } else {
            // Ocurrió un error al agregar el item al carrito de compras
            Log.e("HomeFragment", "Ocurrió un error al agregar el item al carrito de compras")
        }

        db.close()
    }

    private fun dataInitialize(){

        itemList = arrayListOf<Item>()
        //val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.writableDatabase


        //db.delete("items", null, null)

        // Inserta elementos de ejemplo en la base de datos
//        db.execSQL("INSERT INTO items (name, price, description, image_resource) VALUES ('Item 1', '$100.00', 'Descripción del Item 1', ${R.drawable.exlibris})")
//        db.execSQL("INSERT INTO items (name, price, description, image_resource) VALUES ('Item 2', '$200.00', 'Descripción del Item 2', ${R.drawable.wallets})")
//        db.execSQL("INSERT INTO items (name, price, description, image_resource) VALUES ('Item 3', '$300.00', 'Descripción del Item 3', ${R.drawable.dinner_icon})")

        // Consulta los elementos insertados en la base de datos y agrégalos al itemList
        val cursor = db.rawQuery("SELECT * FROM items", null)
        val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
        val priceIndex = cursor.getColumnIndex(COLUMN_PRICE)
        val descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION)
        val imageResourceIndex = cursor.getColumnIndex(COLUMN_IMAGE_RESOURCE)
        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            val price = cursor.getString(priceIndex)
            val description = cursor.getString(descriptionIndex)
            val imageResource = cursor.getInt(imageResourceIndex)

            itemList.add(Item(name, price, description, imageResource))
        }

        cursor.close()
        db.close()
    }





}