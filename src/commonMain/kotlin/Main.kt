import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*

@Composable
fun HelloWorld() {
    val listItems = object : List<LocalDate> by ArrayList() {
        val calculated = hashMapOf<Int, LocalDate>()

        override val size: Int = Int.MAX_VALUE
        override fun get(index: Int): LocalDate {
            return calculated[index] ?: kotlin.run {
                val zeroDate = Instant.fromEpochMilliseconds(0)
                val systemTZ = TimeZone.currentSystemDefault()
                val instant = zeroDate.plus(index, DateTimeUnit.MONTH, systemTZ)
                val result = instant.toLocalDateTime(systemTZ).date

                calculated[index] = result
                result
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
            .safeAreaPaddings()
            .background(Color.White)
    ) {
        HorizontalPager(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.Center),
            initialIndex = 624
        ) { constraints ->
            items(listItems) {
                Column(
                    Modifier
                        .width(constraints.maxWidth)
                        .fillMaxHeight()
                ) {
                    MonthView(Modifier.fillMaxSize(), it, it.daysUntil(it.plus(1, DateTimeUnit.MONTH)))
                }
            }
        }
    }

}