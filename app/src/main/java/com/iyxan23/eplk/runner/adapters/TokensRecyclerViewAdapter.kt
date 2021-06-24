package com.iyxan23.eplk.runner.adapters

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.iyxan23.eplk.runner.R
import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.lexer.models.Token

class TokensRecyclerViewAdapter(
    private var tokens: ArrayList<Token>,
    var code: String
) : RecyclerView.Adapter<TokensRecyclerViewAdapter.ViewHolder>() {

    fun update(tokens: ArrayList<Token>) {
        this.tokens = tokens
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.token_item_rv, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val token = tokens[position]

        holder.tokenName.text = token.token.name
        if (token.token == Tokens.EOF) {
            holder.codeHighlight.text = "EOF"
        } else {
            holder.codeHighlight.text =
                cutText(
                    code,
                    token.startPosition.index,
                    token.endPosition.index,
                    ContextCompat.getColor(holder.itemView.context, R.color.material_on_background_emphasis_medium)
                )
        }
    }

    override fun getItemCount() = tokens.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeHighlight:  TextView = itemView.findViewById(R.id.highlight_code)
        val tokenName:      TextView = itemView.findViewById(R.id.token_name)
    }

    private fun cutText(
        text: String,
        front: Int,
        back: Int,
        defaultColor: Int,
        offsetFront: Int = 2,
        offsetBack: Int = 2,
        color: ForegroundColorSpan = ForegroundColorSpan(Color.parseColor("#ED7417")) // orange
    ): SpannableStringBuilder {

        val defaultForeground = ForegroundColorSpan(defaultColor)
        val result = SpannableStringBuilder()
        val length = text.length

        val offsetFrontPosition = if (front - offsetFront > 0) front - offsetFront else 0
        val offsetBackPosition  = if (back + offsetBack < length - 1) back + offsetBack else length - 1

        result.append(text.substring(offsetFrontPosition, front), defaultForeground, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        result.setSpan(RelativeSizeSpan(.75f), 0, front - offsetFrontPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        result.append(text.substring(front, back), color, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        result.append(text.substring(back, offsetBackPosition + 1), defaultForeground, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (offsetBackPosition != length - 1) result.setSpan(RelativeSizeSpan(.75f), offsetBackPosition - back + 1, result.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return result
    }
}