package com.iyxan23.eplk.adapters

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iyxan23.eplk.R
import com.iyxan23.eplk.Tokens
import com.iyxan23.eplk.Utils
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
                cutText(code, token.startPosition.index, token.endPosition.index)
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
        offsetFront: Int = 0,
        offsetBack: Int = 0,
        color: ForegroundColorSpan = ForegroundColorSpan(0xED7417) // orange
    ): SpannableStringBuilder {
        val result = SpannableStringBuilder()
        val length = text.length

        val offsetFrontPosition = if (front - offsetFront > 0) front - offsetFront else 0
        val offsetBackPosition  = if (back + offsetBack > length) back - offsetBack   else length

        result.append(text.substring(offsetFrontPosition, front))
        result.append(text.substring(front, back), color, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        result.append(text.substring(back, offsetBackPosition))

        return result
    }
}