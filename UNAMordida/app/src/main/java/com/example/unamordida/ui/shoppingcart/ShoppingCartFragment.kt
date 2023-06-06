package com.example.unamordida.ui.shoppingcart

import DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unamordida.R
import com.example.unamordida.databinding.FragmentShoppingCartBinding
import com.example.unamordida.ui.home.Item
import com.example.unamordida.ui.home.ItemAdapter

class ShoppingCartFragment : Fragment() {
    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemList: ArrayList<Item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyCartTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val shoppingCartViewModel = ViewModelProvider(this).get(ShoppingCartViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        _binding = FragmentShoppingCartBinding.bind(rootView) // Enlace de datos

        recyclerView = rootView.findViewById(R.id.recyclerView_cart)
        emptyCartTextView = rootView.findViewById(R.id.text_notifications)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
//        val button = binding.add_button
//        button.text = "Eliminar"
        loadData()
    }

    private fun dataInitialize() {
        itemList = arrayListOf<Item>()
        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.writableDatabase

        val listener = object : ItemAdapter.OnItemClickListener {
            override fun onItemClick(item: Item) {
                // Lógica para manejar el clic en un elemento del carrito
            }

            override fun addToCart(item: Item) {
                // TODO: Lógica para agregar un elemento al carrito
            }
        }

        val adapter = ItemAdapter(itemList, listener)
        //adapter.setOnItemClickListener(listener) // Establecer el listener en el adaptador
        binding.recyclerViewCart.adapter = adapter
    }

    private fun loadData() {
        itemList.clear()

        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM carrito_bd", null)
        val nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)
        val priceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE)
        val descriptionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)
        val imageResourceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RESOURCE)

        while (cursor.moveToNext()) {
            val name = cursor.getString(nameIndex)
            val price = cursor.getString(priceIndex)
            val description = cursor.getString(descriptionIndex)
            val imageResource = cursor.getInt(imageResourceIndex)

            itemList.add(Item(name, price, description, imageResource))
        }

        cursor.close()
        db.close()

        updateUI()
    }



    private fun updateUI() {
        if (itemList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyCartTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyCartTextView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //deleteCartDatabase()        // Liberar los recursos, si es necesario
    }

    private fun deleteCartDatabase() {
        val dbHelper = DatabaseHelper(requireContext())
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM carrito_bd")
        db.close()
    }
}
