package com.example.ecommerce_application.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_application.ui.ProductViewModel
import com.example.ecommerce_application.ui.components.HomeTopAppBar
import com.example.ecommerce_application.ui.components.ProductListItem
import com.example.ecommerce_application.ui.components.TopAppBar
import com.example.ecommerce_application.ui.theme.ECommerce_ApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel(),
    category: String? = null, // Make category nullable
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    // Collect the product list from the ViewModel
    val products by viewModel.productList.collectAsState()

    // Collect the category list from the ViewModel (optional, in case needed for future logic)
    val categories by viewModel.categoryList.collectAsState()

    // Filter products based on the category, if provided
    val filteredProducts = if (category != null && category != "null") {
        products.filter { it.category == category }
    } else {
        products // If no category is provided, show all products
    }

    Scaffold(
        topBar = {
            TopAppBar(onBackClick = onBackClick)
        }
    ) { contentPadding ->
        Box(
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            // Check if the product list is empty and show a message if needed
            if (filteredProducts.isEmpty()) {
                Text(
                    text = "No products available",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Display each product in a clickable row
                    items(filteredProducts) { product ->
                        ProductListItem(
                            product = product,
                            onProductClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}


