package com.massimoregoli.listofproverbs.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categories(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var text: String
)