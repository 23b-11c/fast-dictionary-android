package com.fast.dictionary.data.search.remote

import com.fast.dictionary.model.Definition
import com.google.gson.annotations.SerializedName

data class DefinitionResponse(
    @SerializedName("definition") val definition: String,
    @SerializedName("examples") val examples: List<String>,
    @SerializedName("synonyms") val synonyms: List<String>
) {
    fun toDefinition() = Definition(
        definition = definition,
        examples = examples,
        synonyms = synonyms
    )
}