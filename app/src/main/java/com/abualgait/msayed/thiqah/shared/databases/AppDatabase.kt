package com.abualgait.msayed.thiqah.shared.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO

@Database(entities = [PostPOJO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postItemDao(): PostItemDao


}