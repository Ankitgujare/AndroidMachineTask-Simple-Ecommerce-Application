package com.example.androidmachinetask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidmachinetask.data.local.dao.CartDao
import com.example.androidmachinetask.data.local.dao.ProductDao
import com.example.androidmachinetask.data.model.CartItem
import com.example.androidmachinetask.data.model.Product

@Database(
    entities = [Product::class, CartItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecommerce_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}