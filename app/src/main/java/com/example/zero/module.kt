package com.example.zero

import com.example.zero.list.ClientsAdapter
import com.example.zero.list.MainViewModel
import com.example.zero.list.data.ClientDataSource
import com.example.zero.list.data.ClientDataSourceImpl
import com.example.zero.list.data.ClientRepository
import com.example.zero.list.data.ClientRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single <ClientDataSource>{
        ClientDataSourceImpl()
    }
   single <ClientRepository>{
       ClientRepositoryImpl(get())
   }
    single { ClientsAdapter() }
    viewModel {
        MainViewModel(get())
    }
}