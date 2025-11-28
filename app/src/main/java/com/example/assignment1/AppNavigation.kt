package com.example.assignment1

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment1.ui.screens.EditProfileScreen
import com.example.assignment1.ui.screens.ProfileScreen

@Composable
fun AppNavigation(nav: NavHostController, vm: ProfileViewModel) {
    NavHost(nav, startDestination = "profile") {

        composable("profile") {
            ProfileScreen(vm) {
                nav.navigate("edit")
            }
        }

        composable("edit") {
            EditProfileScreen(vm) { nav.popBackStack() }
        }
    }
}
