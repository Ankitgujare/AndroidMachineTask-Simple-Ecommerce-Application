package com.example.androidmachinetask.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androidmachinetask.R
import com.example.androidmachinetask.navigation.AppNavigation
import com.example.androidmachinetask.navigation.Screen
import com.example.androidmachinetask.ui.viewmodel.ProductViewModel

@Composable
fun MainScreen(viewModel: ProductViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    val cartItemCount by viewModel.cartItemCount.collectAsState()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge {
                                        Text(text = cartItemCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cart),
                                contentDescription = "Cart"
                            )
                        }
                    },
                    label = { Text("Cart") },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        navController.navigate(Screen.ProductList.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        // Pass the padding values to AppNavigation to ensure proper spacing
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}