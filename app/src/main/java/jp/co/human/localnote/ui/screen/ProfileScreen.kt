package jp.co.human.localnote.ui.screen

import android.widget.Button
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.tooling.ComposeToolingApi
import androidx.compose.ui.platform.LocalContext
import jp.co.human.localnote.database.provider.DbProvider
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(userId: String, onBack: () -> Unit, onLogout: () -> Unit) {
    val context = LocalContext.current
    val dao = remember { DbProvider.get(context).userDao() }
    val scope = rememberCoroutineScope()
    val name = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("")}
    val ready = remember { mutableStateOf(false)}

    LaunchedEffect(userId){
        val user = dao.findById(userId)
        if (user == null){
            message.value = "ユーザーが見つかりません"
            ready.value = false
        } else {
            name.value = user.name
            ready.value = true
        }
    }

    ScreenFrame{
        Text("プロフィール")
        Text("ID:" + userId)

        if (!ready.value){
            Text(message.value)
            Button(onClick = onBack) { Text("ログアウトしてホームへ戻る") }
        } else {
            TextField(
                name.value, { name.value = it },
                label = { Text("名前") }, enabled = ready.value
            )

            Button(enabled = ready.value, onClick = {
                scope.launch {
                    if (name.value.isBlank()) {
                        message.value = "名前を入力して下さい"
                    } else {
                        val rows = dao.updateName(
                            userId, name.value.trim()
                        )
                        message.value = if (rows == 1) "保存しました"
                        else "保存できませんでした"
                    }
                }
            }) {
                Text("保存")
            }
            Text(message.value)
            TextButton(onClick = onBack) { Text("戻る") }
        }
    }
}