package com.fast.dictionary

sealed class DetailAdapterItem(val id: String) {
    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean = false

    data class Word(
        val title: String
    ) : DetailAdapterItem(title)

    data class Definition(
        val title: String,
        val examples: List<String>,
        val synonyms: List<String>
    ) : DetailAdapterItem(title)

    data class Type(
        val title: String
    ) : DetailAdapterItem(title)
}