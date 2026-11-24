package jp.co.human.localnote.ui.screen

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import jp.co.human.localnote.database.provider.DbProvider

@Composable
fun RegisterScreen(onDone: () -> Unit) {
    val context = LocalContext.current
    val dao = remember { DbProvider.get(context).userDao()}
    val scope = rememberCoroutineScope ()
    val id = rememberSaveable { mutableStateOf("") }
    val pass = rememberSaveable {mutableStateOf("")}
    val name = rememberSaveable {mutableStateOf("")}
    val error = remember { mutableStateOf("") }
    val saving = remember { mutableStateOf(false)}

    ScreenFrame{
        Text("新規ユーザー登録")
        TextField(id.value, {id.value = it},
            label = {Text("ID")})
        TextField(pass.value, {pass.value = it},
            label = {Text("パスワード")})
        TextField(name.value, {name.value = it},
            label = {Text("名前")})
    }
}