import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun HelloWorld() {
    val listItems = object : List<Int> by ArrayList() {
        override val size: Int = 2000
        override fun get(index: Int) = Random(index).nextInt()
    }

    HorizontalPager(
        Modifier.fillMaxWidth().height(200.dp),
        initialIndex = 0
    ) { constraints ->
        item {
            Text(
                "FIRST",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.width(constraints.maxWidth)
            )
        }
        items(listItems) {
            Box(
                Modifier
                    .width(constraints.maxWidth)
                    .fillMaxHeight()
                    .background(Color(it))
            ) {
                Text(it.toString())
            }
        }
    }
}