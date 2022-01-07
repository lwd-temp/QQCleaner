package me.kyuubiran.qqcleaner.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.R
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerShapes
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTypes

// todo 编辑框抽出为组件
@Composable
fun Edit(
    modifier: Modifier,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    onKeyEvent: (androidx.compose.ui.input.key.KeyEvent) -> Boolean
) {

    Box(
        // 这是居中，但是没什么必要
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = QQCleanerColorTheme.colors.dialogEditColor,
                shape = QQCleanerShapes.dialogEditBackGround
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
    ) {
        TextField(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .onKeyEvent {
                    onKeyEvent(it)
                },
            value = text.value,
            // 这是调用的数字键盘
            keyboardOptions = keyboardOptions,
            // 这是对输入的过滤，文本为纯数字
            onValueChange = onValueChange
        )
        val editHintColor by animateColorAsState(
            if (text.value.isEmpty())
                QQCleanerColorTheme.colors.dialogEditHintColor else QQCleanerColorTheme.colors.transparent,
            tween(100)
        )

        Text(
            text = stringResource(id = R.string.set_auto_clean_interval_desc),
            color = editHintColor,
            style = QQCleanerTypes.DialogEditStyle,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
    }
}