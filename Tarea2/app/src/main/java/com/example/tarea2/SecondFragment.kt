package com.example.tarea2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.tarea2.databinding.FragmentSecondBinding



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val alimentos = listOf("Manzanas", "Plátanos", "Naranjas", "Fresas", "Mangos")

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentSecondBinding.inflate(inflater, container, false)

      (activity as AppCompatActivity).supportActionBar?.show()

      return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adaptador = AdaptadorAlimentos(requireContext(), alimentos)
        val listView = binding.listView
        listView.adapter = adaptador

    }

    fun onCreateOptionsMenu(menu: Menu): Boolean{
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}