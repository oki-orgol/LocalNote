package jp.co.human.localnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.co.human.localnote.ui.theme.LocalNoteTheme
// import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalNoteTheme {
                Scaffold {innerPadding ->
                    Column (
                        modifier = Modifier.padding(innerPadding)
                    ){
                        App()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(){

    val message = remember {
        mutableStateOf("こんにちは")
    }

    Column {
        Text(message.value)
        Button(
            onClick = {
                // 押したときの処理
                message.value = "押されました"
            }) {
            Text("押す")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    LocalNoteTheme {
        MainScreen()

    }
}