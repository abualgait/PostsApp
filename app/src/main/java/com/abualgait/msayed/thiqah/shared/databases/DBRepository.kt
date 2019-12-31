package com.abualgait.msayed.thiqah.shared.databases


class DBRepository(private val db: AppDatabase) {

    fun getPostDao(): PostItemDao? {
        return db.postItemDao()
    }



}
