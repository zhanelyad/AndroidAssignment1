package com.example.assignment1

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.assignment1.ui.theme.PrimaryColor
import com.example.assignment1.ui.theme.SecondaryColor
import com.example.assignment1.ui.theme.OnPrimaryColor
import com.example.assignment1.ui.theme.OnSecondaryColor
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(vm: ProfileViewModel, onEdit: () -> Unit) {
    val followers = vm.followers
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", color = OnPrimaryColor) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor),
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = OnPrimaryColor)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(PrimaryColor, SecondaryColor)
                    )
                )
                .padding(padding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.profilephoto),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)

                            )
                            Spacer(Modifier.width(24.dp))
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(vm.name, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(4.dp))
                                Text(vm.bio, textAlign = TextAlign.Start)
                                Spacer(Modifier.height(12.dp))
                                FollowButton(vm, snackbarHostState, scope)
                            }
                        }
                    }
                }
                item {
                    Text(
                        "Followers",
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSecondaryColor
                    )
                }

                items(followers, key = { it.id }) { follower ->
                    FollowerRow(
                        follower = follower,
                        onRemove = {
                            vm.removeFollower(follower)
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "${follower.name} removed",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    vm.addFollower(follower)
                                }
                            }
                        }
                    )
                    Divider(color = OnSecondaryColor.copy(alpha = 0.2f))
                }
            }
        }
    }
}

@Composable
fun FollowButton(vm: ProfileViewModel, snackbarHost: SnackbarHostState, scope: CoroutineScope) {
    val color = animateColorAsState(
        if (vm.isFollowed) SecondaryColor else PrimaryColor
    )
    Button(
        onClick = {
            vm.toggleFollow()
            scope.launch {
                val message = if (vm.isFollowed) "You followed ${vm.name}" else "You unfollowed ${vm.name}"
                val result = snackbarHost.showSnackbar(message, actionLabel = "Undo")
                if (result == SnackbarResult.ActionPerformed) {
                    vm.toggleFollow() // Undo action
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color.value,
            contentColor = if (vm.isFollowed) OnSecondaryColor else OnPrimaryColor
        )
    ) {
        Text(if (vm.isFollowed) "Unfollow" else "Follow")
    }
}

@Composable
fun FollowerRow(follower: Follower, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = follower.avatar),
            contentDescription = follower.name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(12.dp))
        Text(
            follower.name,
            style = MaterialTheme.typography.bodyLarge,
            color = OnSecondaryColor,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = OnSecondaryColor)
        }
    }
}
