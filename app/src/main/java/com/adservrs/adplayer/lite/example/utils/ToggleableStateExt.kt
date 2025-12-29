package com.adservrs.adplayer.lite.example.utils

import androidx.compose.ui.state.ToggleableState
import kotlinx.coroutines.flow.MutableStateFlow

fun ToggleableState.next(): ToggleableState {
    val values = ToggleableState.entries
    return values[(ordinal + 1) % values.size]
}

fun Boolean?.toToggleableState() = when (this) {
    null -> ToggleableState.Indeterminate
    true -> ToggleableState.On
    else -> ToggleableState.Off
}

fun ToggleableState.toBooleanOrNull() = when (this) {
    ToggleableState.On -> true
    ToggleableState.Off -> false
    ToggleableState.Indeterminate -> null
}

fun MutableStateFlow<Boolean?>.toggle() {
    value = value.toToggleableState().next().toBooleanOrNull()
}
