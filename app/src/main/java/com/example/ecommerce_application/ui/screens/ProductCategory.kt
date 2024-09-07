package com.example.ecommerce_application.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_application.domain.model.Category
import com.example.ecommerce_application.ui.ProductViewModel
import com.example.ecommerce_application.ui.components.HomeBottomBar
import com.example.ecommerce_application.ui.components.HomeTopAppBar
import com.example.ecommerce_application.ui.components.ProductCategoryItem
import com.example.ecommerce_application.ui.theme.ECommerce_ApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCategoriesScreen(
    modifier: Modifier=Modifier,
    onCategoryItemClick: (Category) -> Unit,
    onUserClick: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),

    ) {
    val categories by viewModel.categoryList.collectAsState()

    Scaffold(
        topBar = {
            HomeTopAppBar(
                onUserClick = { onUserClick() }
            )
        },

    ) { contentPadding ->

        ProductCategoriesGrid(
            categories = categories,
            onCategoryClick = onCategoryItemClick,
            modifier = modifier.padding(contentPadding)
        )
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCategoriesGrid(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize() // Fill the entire screen
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 items per row
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center the grid horizontally
            ) {
                items(categories) { category ->
                    ProductCategoryItem(
                        categoryName = category.name,
                        totalItems = category.products.size,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }

        }
    }
}

