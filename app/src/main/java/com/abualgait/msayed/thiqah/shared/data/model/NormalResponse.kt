package com.abualgait.msayed.thiqah.shared.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class NormalResponse : Serializable {

    @SerializedName("status")
    var status: String = ""

    @SerializedName("code")
    var code: Int = 0

    @SerializedName("message")
    var message: String = ""

    @SerializedName("next_offset")
    var nextOffset: Int = 0


    override fun toString(): String {
        return "NormalResponse(status='$status', code='$code', message='$message', nextOffset='$nextOffset')"
    }
}