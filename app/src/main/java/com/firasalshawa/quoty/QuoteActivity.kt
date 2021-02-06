package com.firasalshawa.quoty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.firasalshawa.quoty.UI.QuoteViewModel
import com.firasalshawa.quoty.UI.QuoteViewModelProviderFactory
import com.firasalshawa.quoty.db.QuoteDatabase
import com.firasalshawa.quoty.models.QuoteResponse
import com.firasalshawa.quoty.repository.QuoteRepository
import com.firasalshawa.quoty.util.Resource
import kotlinx.android.synthetic.main.activity_main.*

class QuoteActivity : AppCompatActivity() {

    lateinit var viewModel : QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbarNav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val repository = QuoteRepository(QuoteDatabase(this))
        val viewModelProviderFactory = QuoteViewModelProviderFactory(application,repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(QuoteViewModel::class.java)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}