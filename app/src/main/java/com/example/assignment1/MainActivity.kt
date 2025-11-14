package com.example.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment1.data.AppDatabase
import com.example.assignment1.data.UserRepository
import com.example.assignment1.ui.theme.Assignment1Theme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(this)
        val repository = UserRepository(db.userDao())

        setContent {
            Assignment1Theme {
                val navController = rememberNavController()
                val vm: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(repository))
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    vm.events.collect { msg: String ->
                        snackbarHostState.showSnackbar(msg)
                    }
                }

                Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("home") { HomeScreen(onOpenProfile = { navController.navigate("profile") }) }
                        composable("profile") { ProfileScreen(vm = vm, onEdit = { navController.navigate("edit") }) }
                        composable("edit") { EditProfileScreen(vm = vm, onDone = { navController.popBackStack() }) }
                    }
                }
            }
        }
    }
}


class ProfileViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
