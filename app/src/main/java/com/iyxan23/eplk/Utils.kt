package com.iyxan23.eplk

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

object Utils {
    fun cutText(
        text: String,
        front: Int,
        back: Int,
        offsetFront: Int = 2,
        offsetBack: Int = 2,
        color: ForegroundColorSpan = ForegroundColorSpan(0xED7417) // orange
    ): SpannableStringBuilder {
        val result = SpannableStringBuilder()
        val length = text.length

        val offsetFrontPosition = if (front - offsetFront <= 0) front - offsetFront else 0
        val offsetBackPosition  = if (back + offsetBack >= length) back - offsetBack   else length

        result.append("...")
        result.append(text.substring(offsetFrontPosition..front))
        result.append(text.substring(front..back), color, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        result.append(text.substring(back..offsetBackPosition))
        result.append("...")

        return result
    }
}