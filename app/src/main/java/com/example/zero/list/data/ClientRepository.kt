package com.example.zero.list.data

import com.example.zero.Client

interface ClientRepository {
    fun getClient(): List<Client>
}