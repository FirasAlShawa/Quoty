package com.firasalshawa.quoty.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.firasalshawa.quoty.models.QuoteResponse

@Database(
    entities = [QuoteResponse::class],
    version = 5
)
abstract class QuoteDatabase() : RoomDatabase() {

    abstract fun getQuoteDao():QuoteDao

    companion object{
        @Volatile
        private var instance : QuoteDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                QuoteDatabase::class.java,
                "qoutes_db.db"
            )
                .fallbackToDestructiveMigration()
                .build()

    }

}