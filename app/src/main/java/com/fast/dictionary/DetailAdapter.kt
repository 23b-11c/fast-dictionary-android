package com.fast.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fast.dictionary.extensions.inflate


class DetailAdapter : ListAdapter<DetailAdapterItem, RecyclerView.ViewHolder>(DIFF_UTIL) {

    companion object {

        private const val VIEW_TYPE_WORD = R.layout.item_detail_word
        private const val VIEW_TYPE_DEFINITION = R.layout.item_detail_definition
        private const val VIEW_TYPE_TYPE = R.layout.item_detail_type

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<DetailAdapterItem>() {
            override fun areItemsTheSame(
                oldItem: DetailAdapterItem,
                newItem: DetailAdapterItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DetailAdapterItem,
                newItem: DetailAdapterItem
            ): Boolean = oldItem == newItem
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        VIEW_TYPE_WORD ->
    }
        parent.inflate(viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is DetailAdapterItem.Word -> VIEW_TYPE_WORD
            is DetailAdapterItem.Type -> VIEW_TYPE_TYPE
            else -> VIEW_TYPE_DEFINITION
        }

    private class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private class TypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}