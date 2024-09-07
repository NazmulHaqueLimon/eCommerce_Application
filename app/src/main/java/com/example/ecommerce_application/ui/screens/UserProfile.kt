package com.example.ecommerce_application.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.ecommerce_application.R
import com.example.ecommerce_application.domain.model.Product
import com.example.ecommerce_application.domain.model.User
import com.example.ecommerce_application.ui.ProductViewModel
import com.example.ecommerce_application.ui.components.TopAppBar
import com.example.ecommerce_application.ui.theme.ECommerce_ApplicationTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    modifier: Modifier=Modifier,
    onCaptureImage: () -> Unit,
    onChooseImage: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),

    ) {

    var currentUser = User(
        id = 1,
        name = "John Doe",
        address = "1234 Elm Street, Springfield",
        contact = "+1234567890",
        image = "https://example.com/images/user.png" // Example URL for user image
    )


    Scaffold(
        topBar = {
            TopAppBar(
                onBackClick = { onBackClick()}
            )
        }
    ) { contentPadding ->

        UserProfile(
            user = currentUser, // Pass a user object here
            modifier = Modifier.padding(contentPadding),
            onCaptureImage = {
                // Implement the logic for capturing the image from the camera
            },
            onChooseImage = {
                // Implement the logic for selecting the image from the gallery
            },
            onSave = { updatedUser ->
                // Save the updated user information (e.g., save to database, state, etc.)
                currentUser = updatedUser // If currentUser is mutable
            }
        )
    }

}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserProfile(
    user: User?,
    modifier: Modifier = Modifier,
    onCaptureImage: () -> Unit,
    onChooseImage: () -> Unit,
    onSave: (User) -> Unit
) {
    var userImage by rememberSaveable  { mutableStateOf(user?.image) }
    var userName by rememberSaveable  { mutableStateOf(user?.name.orEmpty()) }
    var userAddress by rememberSaveable  { mutableStateOf(user?.address.orEmpty()) }
    var userContact by rememberSaveable  { mutableStateOf(user?.contact.orEmpty()) }

    Box(
        modifier = modifier.fillMaxSize() // Fill the entire screen
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp) // Add padding around the Column
        ) {
            // User Image with Capture or Choose Buttons
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(200.dp) // Adjust size as needed
                    .clip(CircleShape) // Circular shape for user image
                    .background(MaterialTheme.colorScheme.background) // Optional: background color
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user), // Replace with your drawable resource ID
                    contentDescription = "Default User Image",
                    modifier = Modifier.padding(32.dp).fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
//                if (userImage != null) {
//                    GlideImage(
//                        model = userImage,
//                        contentDescription = "User Image",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                }else{
//                    Image(
//                        painter = painterResource(id = R.drawable.user), // Replace with your drawable resource ID
//                        contentDescription = "Default User Image",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                }
                // Button to capture or choose image
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    IconButton(onClick = onCaptureImage) {
                        Image(
                            painter = painterResource(id = R.drawable.camera), // Replace with your drawable resource ID
                            contentDescription = "Default User Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    IconButton(onClick = onChooseImage) {
                        Image(
                            painter = painterResource(id = R.drawable.camera), // Replace with your drawable resource ID
                            contentDescription = "camera",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Space between image and input fields

            // Username Input Field
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp)) // Space between fields

            // Address Input Field
            OutlinedTextField(
                value = userAddress,
                onValueChange = { userAddress = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp)) // Space between fields

            // Contact Input Field
            OutlinedTextField(
                value = userContact,
                onValueChange = { userContact = it },
                label = { Text("Contact") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space before the save button

            // Save Button
            Button(
                onClick = {
                    val updatedUser = userImage?.let {
                        user?.copy(
                            image = it,
                            name = userName,
                            address = userAddress,
                            contact = userContact
                        )
                    }
                    if (updatedUser != null) {
                        onSave(updatedUser)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Save")
            }
        }
    }
}


