package com.example.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.assignment1.ui.theme.Assignment1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.tooling.preview.Preview

data class Follower(val id: Int, val name: String, val avatar: Int, var isFollowed: Boolean = false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                ProfilePage()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage() {
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbar) },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            ProfileCard(snackbar, scope)
        }
    }
}

@Composable
fun ProfileCard(snackbar: SnackbarHostState, scope: CoroutineScope) {
    var followed by rememberSaveable { mutableStateOf(false) }
    var followers by rememberSaveable { mutableStateOf(200) }

    var followersList by remember {
        mutableStateOf(
            listOf(
                Follower(1, "Alan", R.drawable.avatar1),
                Follower(2, "Aidana", R.drawable.avatar2),
                Follower(3, "Aiken", R.drawable.avatar3),
                Follower(4, "Aikyn", R.drawable.avatar4),
                Follower(5, "Aisulu", R.drawable.avatar5)
            )
        )
    }

    val stories = listOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4,
        R.drawable.avatar5,
        R.drawable.avatar6
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF8A2BE2),
                        Color(0xFFE6E6FA)
                    )
                )
            )
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Stories",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(stories) { story ->
                        Image(
                            painter = painterResource(id = story),
                            contentDescription = "Story",
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profilephoto),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Zhanel Dyusseyeva", fontWeight = FontWeight.Bold)
                        Text("Android learner ✨\nAi Tea lover 🍵", textAlign = TextAlign.Center)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                followed = !followed
                                followers += if (followed) 1 else -1
                                scope.launch {
                                    snackbar.showSnackbar(
                                        if (followed) "You followed Zhanel" else "You unfollowed Zhanel"
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (followed)
                                    Color(0xFFE6E6FA)
                                else
                                    Color(0xFF8A2BE2),
                                contentColor = if (followed)
                                    Color(0xFF4B0082)
                                else
                                    Color.White
                            )
                        ) {
                            Text(if (followed) "Unfollow" else "Follow")
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("Followers: $followers")
                    }
                }
            }

            item {
                Text(
                    "Followers List",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            items(followersList, key = { it.id }) { follower ->
                var offsetX by remember { mutableStateOf(0f) }
                var isFollowed by remember { mutableStateOf(follower.isFollowed) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    if (offsetX < -100) {
                                        val removed = follower
                                        followersList = followersList - removed
                                        scope.launch {
                                            val result = snackbar.showSnackbar(
                                                message = "${removed.name} removed",
                                                actionLabel = "Undo"
                                            )
                                            if (result == SnackbarResult.ActionPerformed) {
                                                followersList = followersList + removed
                                            }
                                        }
                                    }
                                    offsetX = 0f
                                },
                                onDrag = { _, dragAmount ->
                                    offsetX += dragAmount.x
                                }
                            )
                        }
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = follower.avatar),
                        contentDescription = follower.name,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(follower.name, fontWeight = FontWeight.Bold)
                        Text("Active now", style = MaterialTheme.typography.bodySmall)
                    }
                    Button(
                        onClick = {
                            isFollowed = !isFollowed
                            scope.launch {
                                snackbar.showSnackbar(
                                    if (isFollowed) "You followed ${follower.name}" else "You unfollowed ${follower.name}"
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFollowed)
                                Color(0xFFE6E6FA)
                            else
                                Color(0xFF8A2BE2),
                            contentColor = if (isFollowed)
                                Color(0xFF4B0082)
                            else
                                Color.White
                        )
                    ) {
                        Text(if (isFollowed) "Unfollow" else "Follow")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    Assignment1Theme {
        ProfilePage()
    }
}
