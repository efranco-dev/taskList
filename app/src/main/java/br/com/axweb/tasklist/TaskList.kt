package br.com.axweb.tasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.axweb.tasklist.ui.theme.TaskListTheme

@Composable
fun TaskList() {
    val taskList = remember { mutableStateListOf<Taskitem>() }
    var newTask by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Minhas Tarefas",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newTask,
            onValueChange = { newTask = it },
            label = { Text("Digite uma nova tarefa") }
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                if (newTask.isNotBlank()) {
                    taskList.add(Taskitem(title = newTask))
                    newTask = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Adicionar")
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(taskList) { task ->
                TaskCard(task)
            }
        }
    }
}

@Composable
private fun TaskCard(task: Taskitem) {
    var isTaskCompleted by remember { mutableStateOf(task.isCompleted) }
    val styleText = if (isTaskCompleted) TextDecoration.LineThrough else TextDecoration.None
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = styleText
            )
            Text(
                text = if (isTaskCompleted) "Concluida" else "Pendente",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isTaskCompleted) Color(0XFF388E3C) else Color.Black
            )
            IconButton(
                onClick = {
                    isTaskCompleted = !isTaskCompleted
                    task.isCompleted = isTaskCompleted
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = if (isTaskCompleted) "Tarefa concluida" else "Marcar como concluida",
                    tint = if (isTaskCompleted) Color(0XFF388E3C) else Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskListPreview() {
    TaskListTheme {
        TaskList()
    }
}