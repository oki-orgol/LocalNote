package jp.co.human.localnote.ui.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(onLogout: () -> Unit){
    ScreenFrame {
        Text("ホーム")
        Button(onClick = onLogout) {
            Text("ログアウト")
        }
    }
}
