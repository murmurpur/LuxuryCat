package com.example.luxurycat.model

data class AddCatModel(
    val catName: String? = "",
    val catDescription: String? = "",
    val catCoverImg: String? = "",
    val catCategory: String? = "",
    val catId: String? = "",
    val catMrp: String? = "",
    val catSp: String? = "",
    val catImages: ArrayList<String>
)