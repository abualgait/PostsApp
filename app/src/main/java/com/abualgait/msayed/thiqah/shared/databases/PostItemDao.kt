package com.abualgait.msayed.thiqah.shared.databases

import androidx.room.*
import com.abualgait.msayed.thiqah.shared.data.model.PostPOJO

@Dao
interface PostItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: PostPOJO)

    @Update
    fun updateItem(item: PostPOJO)

    @Delete
    fun deleteItem(item: PostPOJO)

    @Query("SELECT * FROM PostPOJO WHERE id == :id")
    fun getItemById(id: Int): PostPOJO

    @Query("SELECT * FROM PostPOJO")
    fun getItems(): List<PostPOJO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertALLItems(items: MutableList<PostPOJO>): List<Long>

    @Query("DELETE FROM PostPOJO")
    fun deletePosts()

}