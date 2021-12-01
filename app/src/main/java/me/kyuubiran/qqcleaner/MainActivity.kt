package me.kyuubiran.qqcleaner

import android.content.res.Resources.getSystem
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import me.kyuubiran.qqcleaner.ui.QQCleanerApp
import me.kyuubiran.qqcleaner.ui.activity.BaseActivity
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerColorTheme.colors
import me.kyuubiran.qqcleaner.ui.theme.QQCleanerTheme
import me.kyuubiran.qqcleaner.util.navigationBarMode
import me.kyuubiran.qqcleaner.util.statusBarLightMode

class MainActivity : BaseActivity() {

    private val viewModel: QQCleanerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setContent {
            QQCleanerTheme(viewModel.theme) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            enabled = true,
                            onClickLabel = null,
                            // 防止击穿
                            onClick = {},
                            role = null,
                            // 去掉点击水波纹
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .background(colors.background)
                        .padding(top = getStatusBarHeight())
                ) {
                    val locales = rememberStrings()
                    locales.languageTag = viewModel.locales
                    ProvideStrings(locales) {
                        QQCleanerApp(viewModel)
                    }
                }
            }
        }
        this.statusBarLightMode()

        this.navigationBarMode()
    }

    /**
     * 返回状态栏的高度
     */
    private fun getStatusBarHeight(): Dp {
        var height = 0f
        val resourceId =
            applicationContext.resources
                .getIdentifier(
                    "status_bar_height",
                    "dimen",
                    "android"
                )
        if (resourceId > 0) {
            // 这个是 px 需要转换
            height = applicationContext.resources.getDimension(resourceId)
        }

        return (height / getSystem().displayMetrics.density).dp
    }

}