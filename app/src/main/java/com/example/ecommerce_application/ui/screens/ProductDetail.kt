package com.example.ecommerce_application.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.ecommerce_application.domain.model.Product
import com.example.ecommerce_application.ui.ProductViewModel
import com.example.ecommerce_application.ui.components.TopAppBar
import com.example.ecommerce_application.ui.theme.ECommerce_ApplicationTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    modifier: Modifier=Modifier,
    productId: Int,
    onBackClick: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),

    ) {
    val product = viewModel.getProduct(productId)

    Scaffold(
        topBar = {
            TopAppBar(
                onBackClick = { onBackClick()}
            )
        }
    ) { contentPadding ->

        if (product != null) {
            ProductDetails(
                product = product,
                modifier = modifier.padding(contentPadding)
            )
        }
    }

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetails(
    product: Product,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize() // Fill the entire screen
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp) // Add padding around the Column
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(200.dp) // Adjust size as needed
                    .clip(RoundedCornerShape(8.dp)) // Optional: round corners of image
                    .background(MaterialTheme.colorScheme.background) // Optional: background color
            ) {
                val imageUrl = if (product.images.isNotEmpty()) {
                    product.images[0] // Display the first image in the list
                } else {
                    "" // Fallback empty string if no images are available
                }

                GlideImage(
                    model = imageUrl,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,

                    )

            }

            Spacer(modifier = Modifier.height(16.dp)) // Space between image and text

            // Product Name
            Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )

            Spacer(modifier = Modifier.height(8.dp)) // Space between name and price

            // Product Price
            Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )

            Spacer(modifier = Modifier.height(8.dp)) // Space between price and description

            // Product Description
            Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
        }
    }
}

