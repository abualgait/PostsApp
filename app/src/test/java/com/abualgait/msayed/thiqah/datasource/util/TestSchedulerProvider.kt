package com.abualgait.msayed.thiqah.datasource.util

import com.abualgait.msayed.thiqah.shared.rx.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}