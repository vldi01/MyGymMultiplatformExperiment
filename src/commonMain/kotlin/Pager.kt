import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlin.math.abs

@Composable
fun HorizontalPager(
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    content: LazyListScope.(constraints: BoxWithConstraintsScope) -> Unit,
) {
    val state = rememberLazyListState(initialIndex)

    val speeds = remember { MutableList(5) { 0f } }
    val isDragged by state.interactionSource.collectIsDraggedAsState()
    var index by remember { mutableStateOf(state.firstVisibleItemIndex) }

    LaunchedEffect(isDragged) {
        if (!isDragged) {
            // Calculate median of speeds
            val speed = speeds[speeds.size / 2]
            val firstVisible = state.firstVisibleItemIndex

            // If speed is to low scroll back
            // Speed could e so low when we are in the end of the list and try to overscroll it
            if (abs(speed) < 2) {
                state.animateScrollToItem(index)
                return@LaunchedEffect
            }

            when {
                // When scrolled to left
                firstVisible < index -> {
                    if (speed >= 0) {
                        index = (index - 1).coerceAtLeast(0)
                    }
                    state.animateScrollToItem(index)
                }
                // When scrolled to right
                firstVisible >= index -> {
                    if (speed < 0) {
                        index = (index + 1).coerceAtMost(state.layoutInfo.totalItemsCount)
                    }
                    state.animateScrollToItem(index)
                }
            }
        }
    }

    // Needed for reacting for scroll event and calculating scroll speed
    val nestedScrollConnection = Modifier.nestedScroll(object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            speeds.add(available.x)
            speeds.removeAt(0)
            return Offset.Zero
        }
    })

    BoxWithConstraints(modifier) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .then(nestedScrollConnection),
            state = state,
        ) {
            content(this@BoxWithConstraints)
        }
    }
}