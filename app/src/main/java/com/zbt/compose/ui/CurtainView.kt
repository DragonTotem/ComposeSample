package com.zbt.compose.ui

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.zbt.compose.R
import com.zbt.compose.ui.widgets.CurtainAngleAnimView
import com.zbt.compose.ui.widgets.CurtainBubbleSeekBar

/**
 * Author: quantao.zhu
 * Date: 2024/4/26 下午5:58
 * Version: 1.0
 * Desc: CurtainView
 */

@Composable
fun CurtainView() {
    var angle by remember {
        mutableFloatStateOf(0f)
    }
    var process by remember {
        mutableFloatStateOf(0f)
    }

    Column {
        AndroidView(factory = {
            CurtainAngleAnimView(it).apply {
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        600
                    )
                setTestProgress(angle)
            }
        }, update = {
            it.updateCurtainProgress(process, angle)
        })
        Row {
            Button(onClick = {
                angle = 0f
                process = 0f
            }) {
                Text("90度")
            }
            Button(onClick = {
                angle = 90f
                process = 0f
            }) {
                Text("0度")
            }
            Button(onClick = {
                angle = 180f
                process = 0f
            }) {
                Text("-90度")
            }
        }

        Row {
            Button(onClick = {
                angle = 0f
                process = 0f
            }) {
                Text("关")
            }
            Button(onClick = {
                angle = 90f
                process = 50f
            }) {
                Text("半开")
            }
            Button(onClick = {
                angle = 180f
                process = 100f
            }) {
                Text("全开")
            }
        }

        Spacer(modifier = Modifier.size(60.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = {
                CurtainBubbleSeekBar(it).apply {
                    layoutParams =
                        ViewGroup.LayoutParams(600, 84)
                    setPadding(30, 45, 30, 0)
                    isDuplicateParentStateEnabled = true
                    splitTrack = false
                    thumb = resources.getDrawable(R.drawable.ic_light_slider_value_scale)
                    progressDrawable =
                        resources.getDrawable(R.drawable.bubble_seekbar_horizontal_bg_drawable_debug)
                    setBackgroundColor(resources.getColor(R.color.black))
                    setMaxAndMin(-90, 90)
                    progress = 0
                }
            })
        }
    }
}