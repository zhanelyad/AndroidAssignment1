package com.example.assignment1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment1.data.UserProfile
import com.example.assignment1.data.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class Follower(
    val id: Int,
    val name: String,
    val avatar: Int,
    var isFollowed: Boolean = false
)

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {


    val profiles: StateFlow<List<UserProfile>> =
        repo.getAllProfiles().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addProfile(profile: UserProfile) = viewModelScope.launch { repo.insertProfile(profile) }
    fun removeProfile(profile: UserProfile) = viewModelScope.launch { repo.deleteProfile(profile) }

    var name by mutableStateOf("Zhanel Dyusseyeva")
        private set
    var bio by mutableStateOf("Android learner ✨\nAI Tea lover 🍵")
        private set
    var isFollowed by mutableStateOf(false)
        private set

    private val _followers = mutableStateListOf(
        Follower(1, "Alan", R.drawable.avatar1),
        Follower(2, "Aidana", R.drawable.avatar2),
        Follower(3, "Aiken", R.drawable.avatar3),
        Follower(4, "Aikyn", R.drawable.avatar4),
        Follower(5, "Aisulu", R.drawable.avatar5)
    )
    val followers: List<Follower> get() = _followers

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    fun toggleFollow() {
        isFollowed = !isFollowed
        viewModelScope.launch {
            _events.emit(if (isFollowed) "You followed $name" else "You unfollowed $name")
        }
    }

    fun updateName(newName: String) {
        name = newName
        notify("Name updated")
    }

    fun updateBio(newBio: String) {
        bio = newBio
        notify("Bio updated")
    }

    fun removeFollower(f: Follower) {
        _followers.remove(f)
        notify("${f.name} removed")
    }

    fun addFollower(f: Follower) {
        _followers.add(f)
        notify("${f.name} restored")
    }

    private fun notify(msg: String) = viewModelScope.launch { _events.emit(msg) }
}
