package cn.ac.lz233.tarnhelm.ui.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardView(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: CardElevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
    colors: CardColors = CardDefaults.elevatedCardColors(),
    content: @Composable ColumnScope.() -> Unit
) = Card(
    modifier = modifier,
    shape = shape,
    border = null,
    elevation = elevation,
    colors = colors,
    content = content
)