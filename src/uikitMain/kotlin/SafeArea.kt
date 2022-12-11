import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import kotlinx.cinterop.useContents
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.safeAreaInsets
import kotlin.math.roundToInt


@Composable
actual fun Modifier.safeAreaPaddings(): Modifier {
    val window: UIWindow = UIApplication.sharedApplication.windows[0] as UIWindow

    var topHeight = 0
    var bottomHeight = 0

    window.safeAreaInsets.useContents {
        topHeight = top.roundToInt()
        bottomHeight = bottom.roundToInt()
    }

    return this.then(
        padding(
            top = LocalDensity.current.run { topHeight.toDp() },
            bottom = LocalDensity.current.run { bottomHeight.toDp() },
        )
    )
}