package com.abualgait.msayed.thiqah.shared.util

import android.app.Activity
import androidx.annotation.ColorRes
import com.abualgait.msayed.thiqah.R
import com.andrognito.flashbar.Flashbar


object FlashbarUtil {

    fun show(
            message: String,
            @ColorRes color: Int = R.color.colorPrimary,
            activity: Activity
    ) {
        try {
            Flashbar.Builder(activity)
                    .message(message)
                    .gravity(Flashbar.Gravity.TOP)
                    .duration(6000)
                    .messageAppearance(R.style.TextStyleFlashBar)
                    .backgroundColorRes(color)
                    .dismissOnTapOutside()
                    .enableSwipeToDismiss()
                    .build()
                    .show()
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
}
