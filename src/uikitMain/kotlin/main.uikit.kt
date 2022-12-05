import androidx.compose.ui.main.defaultUIKitMain
import androidx.compose.ui.window.Application

fun main() {
    defaultUIKitMain("MyGym", Application("MyGym") {
        HelloWorld()
    })
}
