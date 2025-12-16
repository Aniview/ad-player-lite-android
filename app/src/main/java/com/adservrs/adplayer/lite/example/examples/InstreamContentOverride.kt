package com.adservrs.adplayer.lite.example.examples

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.adservrs.adplayer.lite.AdPlayer
import com.adservrs.adplayer.lite.AdPlayerInReadController
import com.adservrs.adplayer.lite.AdPlayerContentOverride
import com.adservrs.adplayer.lite.AdPlayerView

@Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
@Composable
fun InstreamContentOverrideExample(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val override = remember { mutableStateOf(true) }
    val controllerState = remember { mutableStateOf<AdPlayerInReadController?>(null) }

    fun toggle() {
        if (controllerState.value != null) {
            return controllerState::value.set(null)
        }

        val tag = AdPlayer.getTag(
            context = context,
            pubId = "565c56d3181f46bd608b459a",
            tagId = "69009fdf2afa02d6e00f8136"
        )

        controllerState.value = tag.newInReadController {
            if (override.value) {
                it.contentOverride = AdPlayerContentOverride(
                    cmsId = "6915845312387b243304e745"
                )
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        ListItem(
            leadingContent = {
                Checkbox(
                    checked = override.value,
                    onCheckedChange = { override.value = !override.value },
                )
            },
            headlineContent = {
                Text("Override content")
            },
            modifier = Modifier.clickable {
                override.value = !override.value
            },
        )

        Button(onClick = ::toggle) {
            val text = when (controllerState.value) {
                null -> "Show"
                else -> "Hide"
            }
            Text(text)
        }

        val controller = controllerState.value
        if (controller != null) {
            AndroidView(
                factory = {
                    val view = AdPlayerView(it)
                    view.attachController(controllerState.value)
                    view
                },
                onRelease = {
                    it.release()
                    controllerState.value = null
                },
                modifier = Modifier
                    .border(1.dp, Color.Green)
                    .padding(1.dp)
                    .fillMaxSize(),
            )
        }
    }
}
