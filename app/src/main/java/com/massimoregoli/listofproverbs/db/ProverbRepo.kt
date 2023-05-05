package com.massimoregoli.listofproverbs.db

import androidx.lifecycle.LiveData

class ProverbRepo(private val proverbDatabaseDao: DaoProverb) {

//    val readAllData: LiveData<MutableList<Proverb>> = proverbDatabaseDao.getAll()

    suspend fun insert(proverb: Proverb) {
        proverbDatabaseDao.insert(proverb)
    }

    suspend fun update(proverb: Proverb) {
        proverbDatabaseDao.update(proverb)
    }

    suspend fun delete(proverb: Proverb) {
        proverbDatabaseDao.delete(proverb)
    }

    fun readByTag(s: String, f: Int): LiveData<MutableList<Proverb>> {
        return proverbDatabaseDao.getAllByTagAndFavoriteQuery(s, f)
    }
}