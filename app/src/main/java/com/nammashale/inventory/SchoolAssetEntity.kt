package com.nammashale.inventory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")

data class SchoolAssetEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val assetName: String,

    val quantity: String,

    val category: String,

    val status: String,

    val description: String,

    val imageUri: String?,

    val uploadDateTime: String
)