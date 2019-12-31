package com.abualgait.msayed.thiqah.shared.data


import com.abualgait.msayed.thiqah.shared.databases.DBRepository
import com.abualgait.msayed.thiqah.shared.network.ApiRepository
import com.abualgait.msayed.thiqah.shared.rx.SchedulerProvider
import com.abualgait.msayed.thiqah.shared.util.SharedPref

open class DataManager(
        val pref: SharedPref,
        val api: ApiRepository,
        val database: DBRepository,
        val schedulerProvider: SchedulerProvider
)
