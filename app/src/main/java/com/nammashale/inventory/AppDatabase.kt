package com.nammashale.inventory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SchoolAssetEntity::class],
    version = 2
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao

    companion object {

        private var INSTANCE:
                AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "school_inventory_db"
                    )

                        .fallbackToDestructiveMigration()

                        .build()

                INSTANCE = instance

                instance
            }
        }
    }
}