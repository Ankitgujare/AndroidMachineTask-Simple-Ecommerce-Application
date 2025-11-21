package com.example.androidmachinetask.data.local

import androidx.room.TypeConverter
import com.example.androidmachinetask.data.model.Rating
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromRating(rating: Rating?): String? {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingString: String?): Rating? {
        return Gson().fromJson(ratingString, Rating::class.java)
    }
}