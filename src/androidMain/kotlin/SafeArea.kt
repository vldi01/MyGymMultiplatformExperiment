import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
actual fun Modifier.safeAreaPaddings(): Modifier = this.systemBarsPadding()