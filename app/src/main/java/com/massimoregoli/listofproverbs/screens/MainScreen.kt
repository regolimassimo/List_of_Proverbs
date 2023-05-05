@file:Suppress("SpellCheckingInspection")

package com.massimoregoli.listofproverbs.screens


import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.massimoregoli.listofproverbs.db.Proverb
import com.massimoregoli.listofproverbs.ui.theme.*
import com.massimoregoli.listofproverbs.viewmodel.ProverbDbViewModelFactory
import com.massimoregoli.listofproverbs.viewmodel.ProverbViewModel

@Composable
fun HomeView() {
    val context = LocalContext.current
    val mTodoViewModel: ProverbViewModel = viewModel(
        factory = ProverbDbViewModelFactory(context.applicationContext as Application)
    )

    var filter by rememberSaveable {
        mutableStateOf("")
    }

    var refresh by rememberSaveable {
        mutableStateOf(false)
    }

    var onlyFavorite by rememberSaveable {
        mutableStateOf(false)
    }
    val items = mTodoViewModel.readByTag("%$filter%", if (onlyFavorite) 1 else 0).observeAsState(listOf()).value
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Proverbi",
            color= MainColor,
            fontFamily=fontFamily(),
            style = MaterialTheme.typography.h3,
        )
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.Blue),
                value = filter,
                onValueChange = {
                    filter = it
                },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(2f),
                label = {
                    Text(text = "Filter")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onlyFavorite = !onlyFavorite
                        }) {
                        Icon(
                            Icons.TwoTone.ThumbUp,
                            contentDescription = null,
                            tint = if (onlyFavorite) FavoriteTextColor else TextColor
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        ProverbsList(list = items, refresh, mTodoViewModel = mTodoViewModel) {
            refresh = ! it
        }
        Spacer(modifier = Modifier.padding(top = 32.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProverbsList(
    list: List<Proverb>,
    refresh: Boolean,
    mTodoViewModel: ProverbViewModel,
    onrefresh: (Boolean) -> Unit
) {
    LazyColumn {
        itemsIndexed(list) { index, proverb ->
            ListItem (
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, FavoriteTextColor, RoundedCornerShape(8.dp))
                    .background(if (index % 2 == 0) FavoriteBackground else Background),
                text = { Text(
                    modifier = Modifier.padding(4.dp),
                    text = proverb.text,
                    fontFamily = fontFamily(),
                    color = if (proverb.favorite  == 0) TextColor else FavoriteTextColor,
                    fontSize = 20.sp) },
                trailing = {
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = {
                            mTodoViewModel.delete(proverb)
                        }) {
                            Icon(
                                Icons.TwoTone.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MainColor
                            )
                        }
                        IconButton(onClick = {
                            proverb.favorite = 1- proverb.favorite
                            mTodoViewModel.update(proverb)
                            onrefresh(refresh)
                        }) {
                            Icon(
                                Icons.TwoTone.ThumbUp,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = if (proverb.favorite == 1) FavoriteTextColor else TextColor
                            )
                        }
                    }
                })
        }
    }
}

@Composable
fun fontFamily(): FontFamily {
    val assets = LocalContext.current.assets
    return FontFamily(
            Font("Jura-Light.ttf", assets))
}





