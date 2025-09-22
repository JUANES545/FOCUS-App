package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SettingSwitchRow(
    title: String,
    subtitle: String,
    state: MutableState<Boolean>
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            Switch(
                checked = state.value,
                onCheckedChange = { state.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFF66B0E), // Accent color
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}

@Preview
@Composable
fun PreviewSettingSwitchRow() {
    val state = remember { mutableStateOf(true) }
    SettingSwitchRow(
        title = "Notificaciones",
        subtitle = "Activar o desactivar notificaciones",
        state = state
    )
}
