package com.adservrs.adplayer.lite.example.examples

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adservrs.adplayer.lite.AdPlayer
import com.adservrs.adplayer.lite.AdPlayerInReadController
import com.adservrs.adplayer.lite.AdPlayerView
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun FloatingRecyclerViewExample(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val localView = LocalView.current

    val controller = remember {
        AdPlayer.getTag(context, pubId = PUB_ID, tagId = TAG_ID).newInReadController()
    }

    DisposableEffect(controller) {
        onDispose {
            controller.release()
        }
    }

    Column(modifier) {
        val floatingScopeViewMut = remember { MutableStateFlow<View?>(null) }

        AndroidView(
            factory = {
                val recycler = RecyclerView(context)
                recycler.layoutManager = LinearLayoutManager(it)
                recycler.adapter = AppAdapter(controller, floatingScopeViewMut)
                recycler
            },
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .clipToBounds(),
        )
        HorizontalDivider()

        ListItem(
            headlineContent = { Text("Use root view as floating scope") },
            trailingContent = {
                val floatingScopeView = floatingScopeViewMut.collectAsState()
                Checkbox(
                    checked = floatingScopeView.value != null,
                    onCheckedChange = {
                        floatingScopeViewMut.value = when (floatingScopeViewMut.value) {
                            null -> localView.rootView
                            else -> null
                        }
                    }
                )
            },
        )
    }
}

private class AppAdapter(
    controller: AdPlayerInReadController,
    val floatingScopeView: StateFlow<View?>,
) : RecyclerView.Adapter<AppViewHolder>() {
    private val items = (20 downTo 1).toList() + listOf(controller) + (1..20).toList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position] is AdPlayerInReadController) {
            true -> 1
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return when (viewType == 0) {
            true -> AppNumViewHolder(parent.context)
            else -> AppAdsViewHolder(parent.context, floatingScopeView)
        }
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val item = items[position]
        return if (item is AdPlayerInReadController) {
            (holder as AppAdsViewHolder).attach(item)
        } else {
            (holder as AppNumViewHolder).attach(position, "$item")
        }
    }
}

private sealed class AppViewHolder(view: View) : RecyclerView.ViewHolder(view)

private class AppNumViewHolder(private val view: TextView) : AppViewHolder(view) {
    constructor(context: Context) : this(TextView(context).also {
        it.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT,
        )
        it.gravity = Gravity.CENTER
        it.textSize = 20f
        it.setTextColor(Color.WHITE)
        it.setPadding((24 * context.resources.displayMetrics.density).roundToInt())
    })

    fun attach(position: Int, text: String) {
        val color = when (position % 2 == 0) {
            true -> Color.GRAY
            else -> Color.DKGRAY
        }
        view.text = text
        view.setBackgroundColor(color)
    }
}

private class AppAdsViewHolder(
    private val view: AdPlayerView,
    private val floatingScopeView: StateFlow<View?>,
) : AppViewHolder(view) {
    constructor(context: Context, floatingScopeView: StateFlow<View?>) : this(AdPlayerView(context), floatingScopeView)

    init {
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            var job: Job? = null

            override fun onViewAttachedToWindow(p0: View) {
                job = MainScope().launch {
                    floatingScopeView.collect {
                        view.floatingScope = it
                    }
                }
            }

            override fun onViewDetachedFromWindow(p0: View) {
                job?.cancel()
            }
        })
    }

    fun attach(controller: AdPlayerInReadController) {
        view.attachController(controller)
    }
}
