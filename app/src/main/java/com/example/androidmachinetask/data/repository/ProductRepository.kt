package com.example.androidmachinetask.data.repository

import android.util.Log
import com.example.androidmachinetask.data.local.AppDatabase
import com.example.androidmachinetask.data.model.CartItem
import com.example.androidmachinetask.data.model.Product
import com.example.androidmachinetask.data.remote.api.ProductApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ProductApiService,
    private val appDatabase: AppDatabase
) {
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            Log.d("ProductRepository", "Fetching products from API")
            val response = apiService.getProducts()
            Log.d("ProductRepository", "API response received: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                response.body()?.let { products ->
                    Log.d("ProductRepository", "Products received: ${products.size}")
                    // Save to local database
                    appDatabase.productDao().insertAll(products)
                    Result.success(products)
                } ?: run {
                    Log.e("ProductRepository", "Empty response from API")
                    // Try to get from local database
                    val localProducts = appDatabase.productDao().getAllProducts()
                    if (localProducts.isNotEmpty()) {
                        Log.d("ProductRepository", "Returning local products: ${localProducts.size}")
                        Result.success(localProducts)
                    } else {
                        Result.failure(Exception("Empty response from API and no local data"))
                    }
                }
            } else {
                Log.e("ProductRepository", "API call failed: ${response.code()} - ${response.message()}")
                // Try to get from local database
                val localProducts = appDatabase.productDao().getAllProducts()
                if (localProducts.isNotEmpty()) {
                    Log.d("ProductRepository", "Returning local products after API failure: ${localProducts.size}")
                    Result.success(localProducts)
                } else {
                    Result.failure(Exception("Failed to load products from API and no local data"))
                }
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Exception while fetching products", e)
            // Try to get from local database
            try {
                val localProducts = appDatabase.productDao().getAllProducts()
                if (localProducts.isNotEmpty()) {
                    Log.d("ProductRepository", "Returning local products after exception: ${localProducts.size}")
                    Result.success(localProducts)
                } else {
                    Result.failure(e)
                }
            } catch (dbException: Exception) {
                Log.e("ProductRepository", "Exception while fetching from local database", dbException)
                Result.failure(e)
            }
        }
    }

    suspend fun getProductById(id: Int): Product? {
        return appDatabase.productDao().getProductById(id)
    }

    suspend fun addToCart(cartItem: CartItem) {
        appDatabase.cartDao().insertCartItem(cartItem)
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        appDatabase.cartDao().updateCartItem(cartItem)
    }

    suspend fun removeFromCart(productId: Int) {
        appDatabase.cartDao().deleteCartItem(productId)
    }

    suspend fun getCartItems(): List<CartItem> {
        return appDatabase.cartDao().getAllCartItems()
    }

    suspend fun getCartItemCount(): Int {
        return appDatabase.cartDao().getCartItemCount()
    }

    suspend fun clearCart() {
        appDatabase.cartDao().clearCart()
    }
}