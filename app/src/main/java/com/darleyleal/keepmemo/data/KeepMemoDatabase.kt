package com.darleyleal.keepmemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class KeepMemoDatabase : RoomDatabase() {
    abstract fun keepMemoDAO(): KeepMemoDatabaseDao

    companion object {
        private lateinit var INSTANCE: KeepMemoDatabase

        fun getDatabase(context: Context): KeepMemoDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(KeepMemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        KeepMemoDatabase::class.java,
                        "keepMemoApp"
                    ).addMigrations(migration)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        private val migration: Migration = object: Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DELETE FROM keepMemoDatabase")
            }
        }
    }
}