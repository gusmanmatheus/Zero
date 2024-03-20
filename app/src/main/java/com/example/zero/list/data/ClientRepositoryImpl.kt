package com.example.zero.list.data

import com.example.zero.Client
import com.example.zero.list.toGender

class ClientRepositoryImpl(private val dataSource: ClientDataSource) : ClientRepository {
    override fun getClient(): List<Client> {
        val list: MutableList<Client> = mutableListOf()
        dataSource.getClients().sortedBy { it }.map {
            val data = it.split(';')
            if (list.isEmpty() || (list.last().name != data[0] && list.last().name != data[1])) {
                list.add(Client(data[0], data[1].toGender(), 1))
            } else
                list.last().counter++

        }
        return list
    }
}