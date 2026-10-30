package jp.co.human.localnote

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

// 20261023課題
@Composable
fun Counter(){
    // var num = 0
    val count = remember{mutableStateOf(0)}
//    val message = remember {
//        mutableStateOf("こんにちは")
//    }

    Column {
//        Text(message.value)
        Text(count.value.toString())

        Button(
            onClick = {
                // 押したときの処理
                count.value = count.value + 1
            }) {
            Text("増やす")
        }
        Button(
            onClick = {
                // 押したときの処理
                count.value = 0
            }) {
            Text("リセット")
        }
    }
}
