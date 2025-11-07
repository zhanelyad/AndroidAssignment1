package com.example.assignment1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignment1.ui.theme.PrimaryColor
import com.example.assignment1.ui.theme.SecondaryColor
import com.example.assignment1.ui.theme.OnPrimaryColor


data class Post(val image: Int, val caption: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onOpenProfile: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home", color = OnPrimaryColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor),
                actions = {
                    Button(
                        onClick = onOpenProfile,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryColor,
                            contentColor = PrimaryColor
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Profile")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(PrimaryColor, SecondaryColor)
                    )
                )
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column {
                        Text(
                            "Stories",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = OnPrimaryColor
                        )
                        Spacer(Modifier.height(8.dp))

                        val stories = listOf(
                            R.drawable.avatar1,
                            R.drawable.avatar2,
                            R.drawable.avatar3,
                            R.drawable.avatar4,
                            R.drawable.avatar5,
                            R.drawable.avatar6
                        )

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(stories) { story ->
                                Image(
                                    painter = painterResource(id = story),
                                    contentDescription = "Story",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(SecondaryColor)
                                )
                            }
                        }
                    }
                }

                val posts = listOf(
                    Post(R.drawable.post1, "BOO"),
                    Post(R.drawable.post2, "Happy Halloween"),
                    Post(R.drawable.post3, "Travelling"),
                    Post(R.drawable.post4, "With friends")
                )

                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = post.image),
                                contentDescription = "Post Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                post.caption,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }


            }
        }
    }
}
