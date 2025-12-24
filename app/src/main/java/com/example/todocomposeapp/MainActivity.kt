package com.example.todocomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

data class Todo(val id: Int, val text: String, val time: String)

@Composable
fun TodoApp() {
    var task by remember { mutableStateOf("") }
    var todos by remember { mutableStateOf(listOf<Todo>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = task,
                onValueChange = { task = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter task") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (task.isNotBlank()) {
                    val time = SimpleDateFormat(
                        "hh:mm a, dd/MM",
                        Locale.getDefault()
                    ).format(Date())

                    todos = todos + Todo(todos.size + 1, task, time)
                    task = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(todos) { todo ->
                TodoItem(todo) {
                    todos = todos.filter { it.id != todo.id }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3F5B8B)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(todo.time, fontSize = 12.sp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    todo.text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}
