package me.kyuubiran.qqcleaner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme
import kotlin.properties.Delegates

object QQCleanerData {
    // 当前主题
    var theme by mutableStateOf(QQCleanerColorTheme.getCurrentTheme())

    var statusBarHeight by mutableStateOf(0.dp)

    var navigationBarHeight by mutableStateOf(0.dp)

    var isDark by Delegates.notNull<Boolean>()

    var isFirst by mutableStateOf(true)
}