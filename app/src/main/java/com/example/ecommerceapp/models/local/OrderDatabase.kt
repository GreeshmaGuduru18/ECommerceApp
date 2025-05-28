package com.example.ecommerceapp.models.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OrderEntity::class], version = 2)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        private var INSTANCE: OrderDatabase? = null

        fun getInstance(context: Context): OrderDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "ecommerce_db"
                ).fallbackToDestructiveMigration()
                .build()
            }
            return INSTANCE!!
        }
    }
}
