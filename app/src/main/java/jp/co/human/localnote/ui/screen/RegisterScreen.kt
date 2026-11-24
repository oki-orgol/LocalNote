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
    val idError = remember { mutableStateOf(false) }
    val passError = remember { mutableStateOf(false) }
    val nameError = remember { mutableStateOf(false) }
    val saving = remember { mutableStateOf(false)}

    ScreenFrame{
        Text("新規ユーザー登録")
        TextField(id.value,
            {id.value = it},
            label = {Text("ID")},
            isError = idError.value,
            singleLine = true
        )
        TextField(pass.value,
            {pass.value = it},
            label = {Text("パスワード")},
            isError = passError.value,
            singleLine = true
        )
        TextField(name.value,
            {name.value = it},
            label = {Text("名前")},
            isError = nameError.value,
            singleLine = true
        )

        Button(enabled = !saving.value, onClick = {
            idError.value = false
            passError.value = false
            nameError.value = false
            error.value = ""

            if (id.value.isBlank()) idError.value = true
            if (pass.value.isBlank()) passError.value = true
            if (name.value.isBlank()) nameError.value = true

            if (idError.value && passError.value && nameError.value) {
                error.value = "すべて入力してください"
                return@Button
            }
            else if (idError.value){
                error.value = "IDを入力してください"
                return@Button
            } else if (passError.value){
                error.value = "パスワードを入力してください"
                return@Button
            }else if (nameError.value){
                error.value = "名前を入力してください"
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