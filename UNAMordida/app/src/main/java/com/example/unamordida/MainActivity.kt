package com.example.unamordida

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.unamordida.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout


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
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        // Agregar listener para el menú drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
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
            }
            drawerLayout.closeDrawers()
            true
        }

        // Agregar el botón de menú hamburger en la ActionBar
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp)


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