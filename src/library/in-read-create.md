# Creating In-Read Placement

## Creating AdPlayerView

`AdPlayerView` is the main `View` that is responsible for displaying in-read ads.

This view can be added via code:
```kotlin
val view = AdPlayerView(context)
view.load(pubId = "PUBLISHER_ID", tagId = "TAG_ID")
addView(view)
```

Or though the layout xml files:
```xml
<com.adservrs.adplayer.lite.AdPlayerView
    android:id="@+id/ad_player"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

It is not possible to load content from the xml file so it is still required to call `AdPlayerView.load` from the code.


## Releasing AdPlayerView

`AdPlayerView` will try its best to release all used resources when GC-ed but it is highly recommended to explicitly release it to avoid unwanted side-effects:
```kotlin
val view: AdPlayerView = TODO()
view.release()
```

Releasing `AdPlayerView` will immediately free all used resources (including attached `AdPlayerInReadController`).


## Layout Contract

`AdPlayerView` will strictly follow any measuring specs provided. View will try to fit 16/9 video and any required decorations (like labels, close buttons, etc.) into given constraints.

In case both `width` and `height` constraint are unbounded it will fallback to the predefined hard-coded size.
