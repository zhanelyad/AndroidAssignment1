package com.example.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.ui.theme.Assignment1Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                ProfileCard()
            }
        }
    }
}

@Composable
fun ProfileCard(){
    var isFollowing = remember{ mutableStateOf(false)}
    var followers = remember{ mutableStateOf(200)}

    Box (
        modifier = Modifier.fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2196F3), Color(0xFFBBDEFB))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(36.dp, 180.dp),


            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.profilephoto),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Zhanel Dyusseyeva",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Android learner\nCompose beginner",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,

                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly

                )


                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "Posts",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "25",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "Followers",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "${followers.value}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "Following",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "117",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Button(
                    onClick = {
                        if(isFollowing.value){
                            followers.value--
                        } else{
                            followers.value++
                        }
                        isFollowing.value = !isFollowing.value },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFollowing.value) Color.Gray else Color.Blue
                    )
                ) {
                    Text(
                        text = if (isFollowing.value) "Following" else "Follow",
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    Assignment1Theme {
        ProfileCard()
    }
}
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = Modifier
//            .background(Color.Yellow)
//            .padding(16.dp),
//        color = Color.Blue,
//        fontSize = 16.sp
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Assignment1Theme {
//        Greeting("Zhanel")
//    }
//}