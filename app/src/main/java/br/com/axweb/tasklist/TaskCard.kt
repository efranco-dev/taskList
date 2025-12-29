package br.com.axweb.tasklist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import br.com.axweb.tasklist.ui.theme.TaskListTheme

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: TaskItem,
    onDeleteClick: (TaskItem) -> Unit,
    onEditClick: () -> Unit
) {
    var isTaskCompleted by remember { mutableStateOf(task.isCompleted) }
    val styleText = if (isTaskCompleted) TextDecoration.LineThrough else TextDecoration.None
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDeleteClick(task)
            }
            true
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_task),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            lerp(
                                Color.LightGray,
                                stop = Color.Red,
                                fraction = dismissState.progress
                            )
                        )
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(8.dp),
                    tint = Color.White
                )
            }
        }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
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

                IconButton(
                    onClick = onEditClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_task)
                    )
                }
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

