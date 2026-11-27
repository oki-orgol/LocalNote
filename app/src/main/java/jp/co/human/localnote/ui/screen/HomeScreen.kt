package jp.co.human.localnote.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import jp.co.human.localnote.database.entity.UserEntity
import jp.co.human.localnote.database.provider.DbProvider

@Composable
fun HomeScreen(userId: String, onLogout: () -> Unit, onProfile: () -> Unit){
    val context = LocalContext.current
    val dao = remember { DbProvider.get(context).userDao()}
    val user = remember { mutableStateOf<UserEntity?>(null) }

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()){
            user.value = dao.findById(userId)
        }
    }

    ScreenFrame {
        Text("ホーム")

        Text(
            "ID: $userId",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End)
        Text(
            "名前: ${user.value?.name ?:"読み込み中..."}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End)

        Button(onClick = onProfile) {
            Text("プロフィール編集")
        }

        Button(onClick = onLogout) {
            Text("ログアウト")
        }
    }
}
