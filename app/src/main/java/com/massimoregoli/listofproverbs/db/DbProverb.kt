package com.massimoregoli.listofproverbs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(
    entities = [Proverb::class, Categories::class],
    version = 1
)
abstract class DbProverb : RoomDatabase() {
    companion object {
        private var db: DbProverb? = null


        fun getInstance(context: Context): DbProverb {
            if (db == null) {
                db = databaseBuilder(
                    context,
                    DbProverb::class.java, "proverbs.db"
                ).createFromAsset("proverbs.db")
                    .build()
            }
            return db as DbProverb
        }
    }

    abstract fun proverbDao(): DaoProverb
}