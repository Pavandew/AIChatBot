package com.example.aistudent.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudent.model.ModelItem
import com.example.aistudent.R
import com.example.aistudent.model.MessageModel
import com.example.aistudent.ui.theme.BackGroundColor
import com.example.aistudent.ui.theme.HeaderColor
import com.example.aistudent.viewmodel.ChatViewModel

@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel
) {
    Column (
        modifier = modifier
            .background(BackGroundColor)
    ){
        // Applilcation Header
        AppHeader()

        // List of message
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = chatViewModel.messageList
        )

        // This is the field where we s
        MessageInput(
            onMessageSend = {
                chatViewModel.sendMessage(it)
//                chatViewModel.addVoiceMessage(audioUri = )
            }
        )
    }
}

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    messageList: List<MessageModel>
) {
    if(messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.chat_lo),
                contentDescription = "Question",
                tint = Color.Unspecified
            )
             Text(text = "Welcome Buddy! ", fontSize = 17.sp, modifier = Modifier.padding(bottom = 6.dp, top = 10.dp))
             Text(text = "What can I help you?", fontSize = 22.sp)
        }
    } else  {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true  // this will start chat from bottom
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }

}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box (
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 3.dp,
                        bottom = 3.dp

                    )
                    .clip(RoundedCornerShape(20.dp))
//                    .background(if (isModel) ColorModelColor else ColorUserColor)
                    .padding(16.dp)
            ) {
                // SelectionContainer for Copy the text from the app
                SelectionContainer {
                    Text( text = messageModel.message.toString(), fontWeight = FontWeight.W500 )
                }
            }
        }
    }

}
@Composable
fun MessageInput(onMessageSend: (String)-> Unit) {

//    val model = listOf("Gemini", "OpenAI GPT-3.5", "Claude (OpenRouter)", "LLaMA (OpenRouter)")
    val model = listOf(
        ModelItem("Gemini", R.drawable.gemini),
        ModelItem("OpenAI GPT-3.5", R.drawable.chatgpt),
        ModelItem("Claude (OpenRouter)", R.drawable.claude),
        ModelItem("LLaMA (OpenRouter)", R.drawable.llma),
    )

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedModel by remember {
        mutableStateOf(model.first())
    }

    // enter the message value for send
    var message by remember {
        mutableStateOf("")
    }

    Row(
        modifier =  Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
//            placeholder = { Text(text = "Ask me anything.. ") },
            modifier = Modifier.weight(1f),   // if I type long message then it will go second line
            value = message,
            onValueChange = {
                message = it
            },

            // leadingIcon use for show the icon inside the outLinedTextField
            leadingIcon = {
                IconButton(onClick = {
                    expanded = true // open dropdown
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
//                        painter = painterResource(id = selectedModel.imageRes),
                        contentDescription = "Model Selector",
                        tint = Color.Unspecified
                    )
                }
            },
            label = { Text(selectedModel.name)}
        )
        ModelDropMenu(
            expanded = expanded,
            models = model,
            onSelect = {
                selectedModel = it
                expanded = false
            },
            onDismiss = {expanded = false}
        )


        IconButton(onClick = {
            if(message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send"
            )
        }
    }
}

@Composable
fun ModelDropMenu(
    expanded: Boolean,
    models: List<ModelItem>,
    onSelect: (ModelItem) -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        models.forEach { model ->
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = model.imageRes),
                            contentDescription = model.name,
                            modifier = Modifier.size(55.dp)
                            .padding(8.dp),
                            tint = Color.Unspecified
                        )
//                        Text(text = model.name)

                    }
                },
                onClick = { onSelect(model) }
            )
        }
    }
}
@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderColor)
    ) {
        Text(
            modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp),
            text = "AI Student",
            color = Color.White,
            fontSize = 25.sp,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

