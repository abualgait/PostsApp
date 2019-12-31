package com.abualgait.msayed.thiqah.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class PostPOJO(
        @PrimaryKey
        @SerializedName("id")
        var id: Int,
        @SerializedName("userId")
        var userId: Int,
        @SerializedName("title")
        var title: String,
        @SerializedName("body")
        var body: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostPOJO

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "PostPOJO(id=$id, userId=$userId, title='$title', body='$body')"
    }
}

