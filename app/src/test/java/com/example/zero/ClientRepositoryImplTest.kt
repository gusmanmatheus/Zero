package com.example.zero

import com.example.zero.list.Gender
import com.example.zero.list.data.ClientDataSource
import com.example.zero.list.data.ClientRepository
import com.example.zero.list.data.ClientRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.every
 import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import javax.sql.DataSource


class ClientRepositoryImplTest {
    @MockK
    private lateinit var mockDataSource :ClientDataSource
    private lateinit var  clientRepository: ClientRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
         clientRepository = ClientRepositoryImpl(mockDataSource)
    }

    @Test
    fun `getClient should return empty list when the data source return empty list`() {
        every { mockDataSource.getClients() } returns emptyList()
        val clients = clientRepository.getClient()
        Assertions.assertTrue(clients.isEmpty())
    }

    @Test
    fun `getClient should returns just one client when dataSource returns just a unique client`() {
        val mockClients = listOf("Joao;M")
        every { mockDataSource.getClients() } returns mockClients

        val clients = clientRepository.getClient()

        Assertions.assertEquals(1, clients.size)
        Assertions.assertEquals("Joao", clients[0].name)
        Assertions.assertEquals(Gender.MALE, clients[0].gender)
    }

    @Test
    fun `getClient should returns multiple clients with incremented counter for duplicates`() {
        val mockClients = listOf("Joao;M", "Joao;M", "Alice;F")
        every { mockDataSource.getClients() } returns mockClients

        val clients = clientRepository.getClient()
        //in this case the list had been ordered when repository return this
        Assertions.assertEquals(2, clients.size)
        Assertions.assertEquals("Joao", clients[1].name)
        Assertions.assertEquals(Gender.MALE, clients[1].gender)
        Assertions.assertEquals(2, clients[1].counter)
        Assertions.assertEquals("Alice", clients[0].name)
        Assertions.assertEquals(Gender.FEMALE, clients[0].gender)

    }

    @Test
    fun `getClient should returns multiple clients with incremented counter for duplicates but the position is not is ordered by default yet`() {
        val mockClients = listOf("Joao;M", "Alice;F", "Joao;M")
        every { mockDataSource.getClients() } returns mockClients

        val clients = clientRepository.getClient()
        //in this case the list had been ordered when repository return this
        Assertions.assertEquals(2, clients.size)
        Assertions.assertEquals("Joao", clients[1].name)
        Assertions.assertEquals(Gender.MALE, clients[1].gender)
        Assertions.assertEquals(2, clients[1].counter)
        Assertions.assertEquals("Alice", clients[0].name)
        Assertions.assertEquals(Gender.FEMALE, clients[0].gender)

    }
}