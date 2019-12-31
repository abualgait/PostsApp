package com.abualgait.msayed.thiqah.datasource.di


import com.abualgait.msayed.thiqah.datasource.util.TestSchedulerProvider
import com.abualgait.msayed.thiqah.shared.koin.appModule
import com.abualgait.msayed.thiqah.ui.postdetailsactivity.postDetailsActivityModule
import com.abualgait.msayed.thiqah.ui.postsactivity.postsActivityModule
import org.koin.dsl.module


val testRxModule = module { single { TestSchedulerProvider() } }

val testApp = appModule + postsActivityModule + postDetailsActivityModule + testRxModule