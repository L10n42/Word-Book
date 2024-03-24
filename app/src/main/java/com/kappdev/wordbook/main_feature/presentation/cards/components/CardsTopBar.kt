package com.kappdev.wordbook.main_feature.presentation.cards.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.kappdev.wordbook.R
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedBackButton
import com.kappdev.wordbook.main_feature.presentation.common.components.IconButton
import com.kappdev.wordbook.main_feature.presentation.common.components.IconButtonWithoutIndication

@Composable
fun CardsTopBar(
    collectionName: String,
    searchValue: String,
    onSearch: (String) -> Unit,
    navigateBack: () -> Unit,
    openOptions: () -> Unit
) {
    var searchState by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val topBarMotionScene = remember {
        context.resources.openRawResource(R.raw.cards_topbar_scene).readBytes().decodeToString()
    }

    val animationProgress by animateFloatAsState(
        targetValue = if (searchState) 1f else 0f,
        animationSpec = tween(600, easing = LinearEasing),
        label = "TopBar state transition animation"
    )

    MotionLayout(
        motionScene = MotionScene(content = topBarMotionScene),
        progress = animationProgress,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AnimatedBackButton(
            modifier = Modifier.layoutId("back_btn"),
            enabled = !searchState,
            onClick = navigateBack
        )

        Text(
            text = collectionName,
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("title")
        )

        IconButton(
            icon = Icons.Rounded.Tune,
            onClick = openOptions,
            modifier = Modifier.layoutId("options_icon")
        )

        IconButtonWithoutIndication(
            icon = Icons.Rounded.Search,
            enabled = !searchState,
            onClick = { searchState = true },
            modifier = Modifier.layoutId("search_icon")
        )

        IconButton(
            icon = Icons.Rounded.Close,
            enabled = searchState,
            onClick = {
                onSearch("")
                searchState = false
            },
            modifier = Modifier.layoutId("close_icon")
        )

        BasicTextField(
            value = searchValue,
            singleLine = true,
            enabled = searchState,
            onValueChange = onSearch,
            modifier = Modifier.layoutId("search_box"),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box {
                    innerTextField()
                    if (searchValue.isEmpty()) {
                        androidx.compose.material3.Text(
                            text = stringResource(R.string.search),
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            }
        )
    }

//    TopAppBar(
//        elevation = 4.dp,
//        backgroundColor = MaterialTheme.colorScheme.surface,
//        title = { Text(text = collectionName) },
//        navigationIcon = {
//            AnimatedBackButton(onClick = navigateBack)
//        },
//        actions = {
//            IconButton(icon = Icons.Rounded.Search, onClick = openSearch)
//            IconButton(icon = Icons.Rounded.Tune, onClick = openOptions)
//        }
//    )
}

