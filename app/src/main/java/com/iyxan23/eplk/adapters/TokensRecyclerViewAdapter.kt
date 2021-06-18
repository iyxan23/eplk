package com.iyxan23.eplk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iyxan23.eplk.R
import com.iyxan23.eplk.Utils
import com.iyxan23.eplk.lexer.models.Token

class TokensRecyclerViewAdapter(
    private var tokens: ArrayList<Token>,
    private var code: String
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
        holder.codeHighlight.text = Utils.cutText(code, token.startPosition.index, token.endPosition.index)
    }

    override fun getItemCount() = tokens.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeHighlight:  TextView = itemView.findViewById(R.id.highlight_code)
        val tokenName:      TextView = itemView.findViewById(R.id.token_name)
    }
}