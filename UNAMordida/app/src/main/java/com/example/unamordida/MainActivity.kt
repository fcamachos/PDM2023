package com.example.unamordida

import android.app.ActionBar.LayoutParams
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unamordida.databinding.ActivityMainBinding
import com.example.unamordida.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var isDrawerOpened = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Habilita la Action Bar
        //setSupportActionBar(findViewById(R.id.toolbar))
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigation_view)


        //val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        // Agregar el botón de menú hamburger en la ActionBar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp)

        // Agregar listener para el menú drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if(!isDrawerOpened) false
            else {
                // Manejar eventos del menú drawer aquí
                when(menuItem.itemId){
                    R.id.menu_history -> {
                        // TODO configuración
                        Log.d("HomeActivity", "onOptionsItemSelected: menu_history")
                        true
                    }
                    R.id.menu_wallets -> {
                        // TODO configuración
                        Log.d("HomeActivity", "onOptionsItemSelected: menu_wallets")
                        true
                    }
                    R.id.menu_adress -> {
                        // TODO configuración
                        Log.d("HomeActivity", "onOptionsItemSelected: menu_adress")
                        true
                    }
                    else -> false//borrar
                }
                drawerLayout.closeDrawers()
                true
            }
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

            }

            override fun onDrawerOpened(drawerView: View) {
                // super hardcodeado
                Log.d("MainActivity", "drawer: opened")
                isDrawerOpened = true
                drawerLayout.layoutParams.width = Resources.getSystem().displayMetrics.widthPixels
                drawerLayout.layoutParams.height = Resources.getSystem().displayMetrics.heightPixels
                drawerLayout.requestLayout()

            }

            override fun onDrawerClosed(drawerView: View) {
                Log.d("MainActivity", "drawer: closed")
                drawerLayout.layoutParams.width = 1
                drawerLayout.layoutParams.height = 1
                drawerLayout.requestLayout()

            }

            override fun onDrawerStateChanged(newState: Int) {
                Log.d("MainActivity", "drawer: state changed")
            }
        })



        //replaceFragment(HomeFragment())
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//        navHostFragment.view?.setOnTouchListener { _, event ->
//            // Manejar eventos de clic aquí
//            // Retornar "true" para indicar que se ha manejado el evento y evitar que se propague al fragmento
//            true
//        }


    }

    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.drawer_layout,homeFragment)
        fragmentTransaction.commit()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_search -> {
                // TODO búsqueda
                Log.d("HomeActivity", "onOptionsItemSelected: action_search")
                true
            }
            R.id.action_filter -> {
                // TODO filtro
                Log.d("HomeActivity", "onOptionsItemSelected: action_filter")
                true
            }
            R.id.action_settings -> {
                // TODO configuración
                Log.d("HomeActivity", "onOptionsItemSelected: action_settings")
                true
            }
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }




}