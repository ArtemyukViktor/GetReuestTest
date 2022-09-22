package com.example.getreuesttest.room

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.getreuesttest.converters.Converter
import com.example.getreuesttest.pojoWeather.WeatherPojo

@Database(entities = [WeatherPojo::class], version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catimgDao(): AppDao
    internal class PopulateDbAsyn(catDatabase: AppDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val appDao: AppDao

        init {
            appDao = catDatabase!!.catimgDao()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            appDao.deleteAll()
            return null
        }
    }

    companion object {
        private const val DATABASE_NAME = "weather"

        @Volatile
        var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context!!,
                            AppDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        var callback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsyn(INSTANCE)
            }
        }
    }
}