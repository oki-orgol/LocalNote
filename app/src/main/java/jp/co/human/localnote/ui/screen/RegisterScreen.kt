package jp.co.human.localnote.ui.screen

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import jp.co.human.localnote.database.entity.UserEntity
import jp.co.human.localnote.database.provider.DbProvider
import kotlinx.coroutines.launch

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

        Button(enabled = !saving.value, onClick = {
            if (id.value.isBlank() || pass.value.isBlank() || name.value.isBlank()) {
                error.value = "すべて入力してください"
                return@Button
            }
            saving.value = true

            scope.launch {
                try {
                    val rowId = dao.insert(UserEntity(
                        id.value.trim(), pass.value,
                        name.value.trim()
                    ))
                    if (rowId == -1L) {
                        error.value = "別のIDを使ってください"
                    }else{
                        onDone()
                    }
                } catch(
                    e: androidx.sqlite.SQLiteException
                ){
                    error.value = "保存に失敗しました"
                } finally{
                    saving.value = false
                }
            }
        }){ Text("登録") }
        Text(error.value)
        TextButton(onClick = onDone) {Text("戻る")}
    }
}