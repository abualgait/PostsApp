package com.abualgait.msayed.thiqah.datasource

import com.abualgait.msayed.thiqah.datasource.di.testApp
import org.junit.Test
import org.koin.core.context.startKoin

class DryRunTest : BaseUnitTest() {

    @Test
    fun testConfiguration() {
        startKoin { modules(testApp) }
    }

}