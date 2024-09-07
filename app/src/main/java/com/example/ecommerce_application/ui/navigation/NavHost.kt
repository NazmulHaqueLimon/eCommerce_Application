package com.example.ecommerce_application.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce_application.ui.screens.ProductCategoriesScreen
import com.example.ecommerce_application.ui.screens.ProductDetailsScreen
import com.example.ecommerce_application.ui.screens.ProductListScreen
import com.example.ecommerce_application.ui.screens.UserProfileScreen

@Composable
fun AppNavHost(
    startDestination: String = "categoriesScreen",
){
    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {

        composable(
            "categoriesScreen"
        ) {
            ProductCategoriesScreen(
                onCategoryItemClick = {
                    navController.navigate("productListScreen/${it.name}")
                },
                onUserClick = {
                    navController.navigate("userProfileScreen")
                              },
                viewModel = hiltViewModel()
            )
        }
        composable(
            "productListScreen/{category}"
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            category?.let {
                ProductListScreen(
                    onProductClick = { productId ->
                        navController.navigate("productDetailsScreen/$productId")
                    },
                    onBackClick = {
                        navController.navigateUp() },

                    viewModel = hiltViewModel(),

                )
            }
        }

        composable("productDetailsScreen/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt()
            productId?.let {
                ProductDetailsScreen(
                    viewModel = hiltViewModel(),
                    productId = it,
                    onBackClick = {
                        navController.navigateUp()
                                  },
                )
            }
        }


        composable("userProfileScreen") { backStackEntry ->

            UserProfileScreen(
                onCaptureImage = {},
                onChooseImage = {},
                onBackClick = {
                    navController.navigateUp()
                },
                viewModel = hiltViewModel()
            )
        }
    }
}