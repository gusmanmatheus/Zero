package com.example.zero.list

import androidx.lifecycle.Observer
import com.example.zero.Client
import com.example.zero.InstantExecutorExtension
import com.example.zero.list.Gender.*
import com.example.zero.list.data.ClientRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {
    @MockK
    private lateinit var mockRepository: ClientRepository

    private lateinit var viewModel: MainViewModel
    private lateinit var clientsMutableLiveData: Observer<List<Client>>
    private lateinit var isOrderedByNameLiveData: Observer<Boolean>

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun `test unique getClients`() {
        val listOfClientMock = listOf(Client("Marcos", MALE, 1))
        every { mockRepository.getClient() } returns listOfClientMock
        clientsMutableLiveData = mockk {
            every { onChanged(listOfClientMock) } just Runs
        }
        viewModel.getClients()
        viewModel.clients.observeForever(clientsMutableLiveData)
        verify { clientsMutableLiveData.onChanged(listOfClientMock) }
        assertEquals(mockRepository.getClient(), listOfClientMock)
    }

    @Test
    fun `test multiples getClients`() {
        val listOfClientMock = listOf(
            Client("Marcos", MALE, 2),
            Client("Carlos", MALE, 1),
            Client("Amanda", FEMALE, 1)
        )
        every { mockRepository.getClient() } returns listOfClientMock
        clientsMutableLiveData = mockk<Observer<List<Client>>>(relaxed = true)

        viewModel.getClients()
        viewModel.clients.observeForever(clientsMutableLiveData)

        verify { clientsMutableLiveData.onChanged(listOfClientMock) }
        assertEquals(mockRepository.getClient(), listOfClientMock)
    }

    @Test
    fun `getClientsOrdered sorts clients by name`() {
        val unsortedClients = listOf(Client("Maria", FEMALE, 1), Client("Ana", FEMALE, 2))
        every { mockRepository.getClient() } returns unsortedClients

        viewModel.getClients()
        viewModel.getClientsOrdered()

        val sortedClients = viewModel.clients
        assertEquals("Ana", sortedClients.value?.get(0)?.name ?: "")
    }

    @Test
    fun `getClientsOrdered sorts clients by counter`() {
        val unsortedClients = listOf(Client("Ana", FEMALE, 1), Client("Maria", FEMALE, 2))
        every { mockRepository.getClient() } returns unsortedClients

        viewModel.getClients()
        viewModel.changeOrder()
        viewModel.getClientsOrdered()

        val sortedClients = viewModel.clients
        assertEquals("Maria", sortedClients.value?.get(0)?.name ?: "")
    }

    @Test
    fun `test empty getClients`() {
        every { mockRepository.getClient() } returns emptyList()
        clientsMutableLiveData = mockk {
            every { onChanged(emptyList()) } just Runs
        }
        viewModel.getClients()
        viewModel.clients.observeForever(clientsMutableLiveData)
        verify { clientsMutableLiveData.onChanged(emptyList()) }
        assertEquals(emptyList<Client>(), viewModel.clients.value)
    }

    @Test
    fun `test multiple clients`() {
        val listOfClientsMock = listOf(
            Client("Marcos", MALE, 1),
            Client("Maria", FEMALE, 2)
        )
        every { mockRepository.getClient() } returns listOfClientsMock
        clientsMutableLiveData = mockk {
            every { onChanged(listOfClientsMock) } just Runs
        }
        viewModel.getClients()
        viewModel.clients.observeForever(clientsMutableLiveData)
        verify { clientsMutableLiveData.onChanged(listOfClientsMock) }
        assertEquals(listOfClientsMock, viewModel.clients.value)
    }

    @Test
    fun `test multiple clients and ordering by counter`() {
        val isOrderedMock =false
        val listOfClientsMock = listOf(
            Client("Marcos", MALE, 1),
            Client("Maria", FEMALE, 2)
        )
        val listOfClientsReverseMock = listOfClientsMock.sortedByDescending { it.counter }

        every { mockRepository.getClient() } returns listOfClientsMock
        viewModel.getClients()

        clientsMutableLiveData = mockk {
            every { onChanged(listOfClientsReverseMock) } just Runs
        }
        isOrderedByNameLiveData = mockk {
            every { onChanged(isOrderedMock) } just Runs
        }
        viewModel.changeOrder()
        viewModel.getClientsOrdered()
        viewModel.clients.observeForever(clientsMutableLiveData)
        viewModel.isOrderByName.observeForever(isOrderedByNameLiveData)
        verify { clientsMutableLiveData.onChanged(listOfClientsReverseMock) }
        every { isOrderedByNameLiveData.onChanged(isOrderedMock) }
        assertEquals(listOfClientsReverseMock, viewModel.clients.value)
    }
}
