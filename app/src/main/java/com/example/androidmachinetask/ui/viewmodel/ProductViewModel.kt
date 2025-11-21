package com.example.androidmachinetask.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmachinetask.data.model.CartItem
import com.example.androidmachinetask.data.model.Product
import com.example.androidmachinetask.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> = _cartItemCount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.getProducts()
                .onSuccess { productList ->
                    _products.value = productList
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isLoading.value = false
        }
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getCartItems()
            _cartItemCount.value = repository.getCartItemCount()
        }
    }

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            val cartItem = CartItem(
                id = product.id,
                title = product.title,
                price = product.price,
                image = product.image,
                quantity = quantity
            )
            repository.addToCart(cartItem)
            loadCartItems() // Refresh cart items
        }
    }

    fun updateCartItemQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            val cartItem = _cartItems.value.find { it.id == productId }
            if (cartItem != null) {
                val updatedCartItem = cartItem.copy(quantity = quantity)
                repository.updateCartItem(updatedCartItem)
                loadCartItems() // Refresh cart items
            }
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
            loadCartItems() // Refresh cart items
        }
    }

    fun getSelectedProduct(productId: Int): Product? {
        return _products.value.find { it.id == productId }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}