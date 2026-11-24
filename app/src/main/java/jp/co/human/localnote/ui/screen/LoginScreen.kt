package jp.co.human.localnote.ui.screen

import android.util.Log.e
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import jp.co.human.localnote.database.provider.DbProvider
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLogin: (String) -> Unit, onRegister: () -> Unit){

    val loginId = rememberSaveable{ mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("")}
    val message = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val dao = remember { DbProvider.get(context).userDao() }
    val scope = rememberCoroutineScope()
    val busy = remember { mutableStateOf(false) }

    ScreenFrame {
        Text("ログイン")

        TextField(
            value = loginId.value,
            onValueChange = {loginId.value = it},
            label = {Text("ID")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password.value,
            onValueChange = {password.value = it},
            label = {Text("パスワード")},
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(enabled = !busy.value, onClick = {
            if (loginId.value.isBlank() || password.value.isBlank()){
                message.value = "IDとパスワードを入力してください"
                return@Button
            }
            busy.value = true
            scope.launch {
                try {
                    val user = dao.findById(loginId.value.trim())
                    if (user != null && user.password == password.value) {
                        password.value = ""
                        message.value = ""
                        onLogin(user.loginId)
                    } else {
                        message.value = "IDまたはパスワードが違います"
                    }
                } catch (e: androidx.sqlite.SQLiteException){
                    message.value = "DBの読み込みに失敗しました"
                } finally {
                    busy.value = false
                }
            }
        }) {
            Text("ログイン")
        }
        Text(message.value)

        Button(onClick = {
            loginId.value = ""
            password.value = ""
            message.value = ""
        }) {
            Text("クリア")
        }

        TextButton(onClick = onRegister){
            Text("新規ユーザー登録")
        }
    }
}

@Composable
fun ScreenFrame(content: @Composable ColumnScope.() -> Unit){
    Column(
        Modifier.fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        content()
    }
}