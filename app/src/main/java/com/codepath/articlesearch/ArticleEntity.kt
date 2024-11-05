package com.codepath.articlesearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "headline") val date: String?,
    @ColumnInfo(name = "articleAbstract") val sleepTotal: String?,
    @ColumnInfo(name = "byline") val sleepNotes: String?
)