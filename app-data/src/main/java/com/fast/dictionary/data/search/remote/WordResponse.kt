package com.fast.dictionary.data.search.remote

import com.fast.dictionary.model.Word
import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("word") val word: String,
    @SerializedName("verbs") val verbs: List<DefinitionResponse> = listOf(),
    @SerializedName("nouns") val nouns: List<DefinitionResponse> = listOf()
) {
    fun toWord() = Word(
        word = word,
        verbs = verbs.map { it.toDefinition() },
        nouns = nouns.map { it.toDefinition() }
    )
}