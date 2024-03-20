package com.example.zero.list.data

class ClientDataSourceImpl(): ClientDataSource {
    override fun getClients(): List<String> {
        return MOCKClients
    }
}