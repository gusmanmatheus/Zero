package com.example.zero.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zero.R
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.zero.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val adapter by inject<ClientsAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupRecyclerView()
        clicksListener()
        viewModel.getClients()
    }

    private fun clicksListener() {
        binding.orderAm.setOnClickListener {
            viewModel.changeOrder()
        }
    }

    private fun setupObservers() {
        clientsObserver()
        isOrderByNameObserver()
    }

    private fun clientsObserver() {
        viewModel.clients.observe(this) {
            adapter.setData(it)
         }
    }

    private fun isOrderByNameObserver() {
        viewModel.isOrderByName.observe(this) {
            val order = if (it) "nome" else "quantidade"
            binding.orderAm.text = getString(R.string.order_button, order)
            viewModel.getClientsOrdered()

        }
    }

    private fun setupRecyclerView() {
        binding.recyclerAm.adapter = adapter
        binding.recyclerAm.layoutManager = LinearLayoutManager(this)
    }
}