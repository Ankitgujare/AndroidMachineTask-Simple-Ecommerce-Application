package com.example.androidmachinetask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidmachinetask.data.model.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart")
    suspend fun getAllCartItems(): List<CartItem>

    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun getCartItemById(id: Int): CartItem?

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteCartItem(id: Int)

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Query("SELECT COUNT(*) FROM cart")
    suspend fun getCartItemCount(): Int
}