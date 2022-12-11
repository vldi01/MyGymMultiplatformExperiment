import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.datetime.LocalDate
import kotlin.math.ceil

@Composable
fun MonthView(modifier: Modifier = Modifier, date: LocalDate, days: Int) {
    val rows = remember(date, days) { ceil((date.dayOfMonth + days) / 7.0).toInt() }
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${date.month.name} of ${date.year}",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )

        BoxWithConstraints(Modifier.fillMaxSize()) {
            LazyVerticalGrid(columns = GridCells.Fixed(7), userScrollEnabled = false) {
                items(Array(date.dayOfMonth) { -1 } + Array(days) { it }) {
                    Box(
                        modifier = Modifier
                            .width(this@BoxWithConstraints.maxWidth / 7)
                            .height(this@BoxWithConstraints.maxHeight / rows)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = if (it != -1) it.toString() else "",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}