package com.darleyleal.keepmemo.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darleyleal.keepmemo.data.Note
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItemCard(
    isClicked: () -> Unit,
    noteId: (Long) -> Unit,
    note: Note,
    modifier: Modifier = Modifier
) {
    val noteColor = remember {
        mutableStateOf(
            Color(
                Random.nextLong(0xFFFFFFF)
            ).copy(alpha = 0.4f)
        )
    }

    KeepMemoTheme {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ), modifier = modifier
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                .clickable {
                    isClicked()
                    noteId(note.id)
                }
        ) {
            Column(
                modifier = modifier
                    .background(
                        color = noteColor.value
                    )
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = note.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600),
                    modifier = modifier.padding(top = 8.dp, bottom = 16.dp)
                )
                Text(
                    text = note.description,
                    fontSize = 14.sp,
                    modifier = modifier
                        .padding(bottom = 8.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}