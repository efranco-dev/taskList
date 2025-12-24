package br.com.axweb.tasklist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import br.com.axweb.tasklist.ui.theme.TaskListTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen() {
    val taskList = remember { mutableStateListOf<TaskItem>() }
    var newTask by remember { mutableStateOf("") }
    var showEditTaskSheet by remember { mutableStateOf(false) }
    var currentEditTitle by remember { mutableStateOf("") }
    var taskToEdit by remember { mutableStateOf<TaskItem?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar tarefa")
            }
        },
        topBar = {
            TopAppBar(title = { Text(text = "Minhas Tarefas") })
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerpadding ->

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    newTask = ""
                },
                title = { Text("Nova tarefa") },
                text = {
                    OutlinedTextField(
                        value = newTask,
                        onValueChange = { newTask = it },
                        label = { Text("Digite uma nova tarefa") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (newTask.isNotBlank()) {
                            val taskAdd = TaskItem(title = newTask)
                            taskList.add(taskAdd)
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Tarefa adionada com sucesso!",
                                    duration = SnackbarDuration.Short,
                                    actionLabel = "Desfazer"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    taskList.remove(taskAdd)
                                }
                            }
                            newTask = ""
                            showDialog = false
                        }
                    }) {
                        Text("Adicionar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        newTask = ""
                    }) {
                        Text("Cancelar")
                    }
                },
                properties =
                    DialogProperties(
                        dismissOnClickOutside = false,
                        dismissOnBackPress = true
                    )
            )
        }
        LazyColumn(
            modifier = Modifier.padding(innerpadding)
        ) {
            items(
                items = taskList,
                key = { task -> task.id }) { task ->
                TaskCard(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    task = task,
                    onDeleteClick = {
                        taskList.remove(task)
                    },
                    onEditClick = {
                        showEditTaskSheet = true
                        currentEditTitle = task.title
                        taskToEdit = task
                    }
                )
            }
        }
        if (showEditTaskSheet) {
            EditTaskBottonSheet(
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = {
                    showEditTaskSheet = false
                    taskToEdit = null
                },
                onConfirmClick = {
                    taskToEdit?.let { originalTask ->
                        if (currentEditTitle.isNotBlank()) {
                            val taskIndex = taskList.indexOf(originalTask)
                            if (taskIndex != -1) {
                                taskList[taskIndex] =
                                    originalTask.copy(title = currentEditTitle)
                            }
                        }
                    }
                    showEditTaskSheet = false
                    taskToEdit = null
                },
                onTaskValueChange = { newTask ->
                    currentEditTitle = newTask
                },
                taskValue = currentEditTitle
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TaskListPreview() {
    TaskListTheme {
        TaskListScreen()
    }
}