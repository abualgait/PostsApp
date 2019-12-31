package com.abualgait.msayed.thiqah.datasource.datasource

import com.abualgait.msayed.thiqah.datasource.di.testApp
import com.abualgait.msayed.thiqah.shared.network.ApiRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class RepositoryTest : AutoCloseKoinTest() {

    val repository by inject<ApiRepository>()

    @Before
    fun before() {
        startKoin { modules(testApp) }
    }


    @Test
    fun testCachedSearch() {

        val posts1 = repository.getPosts().blockingFirst()
        val posts2 = repository.getPosts().blockingFirst()
        assertEquals(posts1, posts2)
    }

    @Test
    fun testGetPostsSuccess() {
        repository.getPosts().test()
        val test = repository.getPosts().test()
        test.awaitTerminalEvent()
        test.assertComplete()
    }

    @Test
    fun testGetPostsFailed() {
        val test = repository.getPosts().test()
        test.awaitTerminalEvent()
        test.assertValue { list -> list.isEmpty() }
    }
}