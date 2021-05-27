package io.github.ytam.rickandmorty.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import io.github.ytam.rickandmorty.R

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navigationController)
    }

    // back button toolbar
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)
    }
}
