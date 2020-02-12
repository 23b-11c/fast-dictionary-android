package com.fast.dictionary.model

data class Word(
    val word: String,
    val verbs: List<Definition> = listOf(),
    val nouns: List<Definition> = listOf()
)