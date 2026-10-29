package goldrush.skeedromano.official

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import goldrush.skeedromano.official.ui.composable.approot.AppRoot
import goldrush.skeedromano.official.ui.theme.GSROLAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GSROLAppTheme {
                AppRoot()
            }
        }
    }
}