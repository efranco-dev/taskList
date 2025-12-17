package br.com.axweb.tasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.axweb.tasklist.ui.theme.TaskListTheme

@Composable
fun TaskList() {
    val taskList = remember { mutableStateListOf<String>() }
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
                    taskList.add(newTask)
                    newTask = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Adicionar")
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn{
            items(taskList){task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(text = task,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp))
                }
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