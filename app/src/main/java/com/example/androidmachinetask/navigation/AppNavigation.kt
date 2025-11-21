package com.example.androidmachinetask.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidmachinetask.ui.screens.CartScreen
import com.example.androidmachinetask.ui.screens.ProductDetailScreen
import com.example.androidmachinetask.ui.screens.ProductListScreen
import com.example.androidmachinetask.ui.viewmodel.ProductViewModel

sealed class Screen(val route: String) {
    object ProductList : Screen("product_list")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()
    var isProductsLoaded by remember { mutableStateOf(false) }
    
    // Load products when the app starts
    LaunchedEffect(Unit) {
        if (products.isEmpty()) {
            viewModel.loadProducts()
        } else {
            isProductsLoaded = true
        }
    }
    
    // Check if products are loaded
    LaunchedEffect(products) {
        if (products.isNotEmpty()) {
            isProductsLoaded = true
        }
    }
    
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Screen.ProductList.route
        ) {
            composable(Screen.ProductList.route) {
                ProductListScreen(
                    onProductClick = { product ->
                        navController.navigate(Screen.ProductDetail.createRoute(product.id))
                    }
                )
            }
            
            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(navArgument("productId") { defaultValue = -1 })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                
                // Ensure products are loaded before trying to find the selected product
                if (isProductsLoaded) {
                    val selectedProduct = products.find { it.id == productId }
                    
                    if (selectedProduct != null) {
                        ProductDetailScreen(
                            product = selectedProduct,
                            onBackClick = { navController.popBackStack() },
                            viewModel = viewModel
                        )
                    } else {
                        // Show error screen or navigate back if product not found
                        ProductDetailScreen(
                            product = com.example.androidmachinetask.data.model.Product(
                                id = -1,
                                title = "Product Not Found",
                                price = 0.0,
                                description = "The requested product could not be found.",
                                category = "Unknown",
                                image = "",
                                rating = com.example.androidmachinetask.data.model.Rating(0.0, 0)
                            ),
                            onBackClick = { navController.popBackStack() },
                            viewModel = viewModel
                        )
                    }
                } else {
                    // Show loading state while products are being loaded
                    ProductDetailScreen(
                        product = com.example.androidmachinetask.data.model.Product(
                            id = -2,
                            title = "Loading...",
                            price = 0.0,
                            description = "Loading product details...",
                            category = "Unknown",
                            image = "",
                            rating = com.example.androidmachinetask.data.model.Rating(0.0, 0)
                        ),
                        onBackClick = { navController.popBackStack() },
                        viewModel = viewModel
                    )
                }
            }
            
            composable(Screen.Cart.route) {
                CartScreen(viewModel = viewModel)
            }
        }
    }
}