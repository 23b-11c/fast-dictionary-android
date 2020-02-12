package com.fast.dictionary.model

data class Definition(
    val definition: String,
    val examples: List<String>,
    val synonyms: List<String>
)