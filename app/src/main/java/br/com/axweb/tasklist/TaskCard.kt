package br.com.axweb.tasklist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun TaskCard(
    modifier: Modifier = Modifier,
    task: TaskItem,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    var isTaskCompleted by remember { mutableStateOf(task.isCompleted) }
    val styleText = if (isTaskCompleted) TextDecoration.LineThrough else TextDecoration.None
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                isTaskCompleted = !isTaskCompleted
                task.isCompleted = isTaskCompleted
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0XFF388E3C)
            )
        )




            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = styleText
            )
          /*  Text(
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
            }*/
            IconButton(
                onClick = onEditClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar tarefa"
                )
            }
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete tarefa"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    TaskListTheme {
        TaskCard(
            modifier = Modifier,
            TaskItem(
                title = "Minha task", isCompleted = false
            ),
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}

