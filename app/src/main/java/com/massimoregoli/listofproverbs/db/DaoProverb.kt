package com.massimoregoli.listofproverbs.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface DaoProverb {
    @Insert
    suspend fun insertAll(proverbs: List<Proverb>)
    @Insert
    suspend fun insert(proverb: Proverb)
    @Update
    suspend fun update(proverb: Proverb)
    @Delete
    suspend fun delete(proverb: Proverb)


    @Query("SELECT * FROM Proverb ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomProverb(): Proverb
    @Query("SELECT * FROM Proverb")
    fun getAll(): LiveData<MutableList<Proverb>>
    @Query("SELECT * FROM Proverb WHERE text LIKE :search")
    fun loadAllByTag(search: String): LiveData<MutableList<Proverb>>
    @Query("SELECT * FROM Proverb WHERE favorite = 1")
    fun loadAllByFavorite(): LiveData<MutableList<Proverb>>
    @Update
    suspend fun favorite(item: Proverb)
    @RawQuery(observedEntities = [Proverb::class])
    fun getAllByTagAndFavorite(query: SupportSQLiteQuery): LiveData<MutableList<Proverb>>

    fun getAllByTagAndFavoriteQuery(s: String, f: Int = 0): LiveData<MutableList<Proverb>> {
        var query = "SELECT * FROM Proverb WHERE text LIKE '%$s%'"
        if (f == 1)
            query += " AND favorite = 1"
        val simpleSQLiteQuery = SimpleSQLiteQuery(query, arrayOf<Proverb>())
        return getAllByTagAndFavorite(simpleSQLiteQuery)
    }
}