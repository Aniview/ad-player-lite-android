# Controlling In-Read Placement

## What is Controller?

`AdPlayerView` will only load and display Ad content but can't control it (pausing, skipping, unmuting, etc.). These tasks are done by using controllers.

For In-Read placement there is a corresponding `AdPlayerInReadController`.


## Creating In-Read Controller

There are few ways to get controller:

1. when loading content via `AdPlayerView`:
```kotlin
val view = AdPlayerView(context)
val controller: AdPlayerInReadController = view.load(...)
```

2. reading already loaded controller from `AdPlayerView`:
```kotlin
val view = AdPlayerView(context)
val controller: AdPlayerInReadController? = view.controller
```

3. creating controller manually:
```kotlin
val tag = AdPlayer.getTag(context, pubId = "...", tagId = "...")
val controller: AdPlayerInReadController = tag.newInReadController()

val view = AdPlayerView(context)
view.attachController(controller)
```


## Releasing In-Read Controller

`AdPlayerInReadController`, like any other controller, must be always released when no longer needed to release underlying resources:
```kotlin
val controller: AdPlayerInReadController = TODO()
controller.release()
```

In case `AdPlayerInReadController` is attached to the `AdPlayerView`, it will be automatically released with the view itself.


## Controlling Video Playback

Most basic functionality, that controllers allow, is to control video playback:
```kotlin
val controller: AdPlayerInReadController = TODO()

// pause playback
controller.pause()

// resume playback
controller.resume()

// skip current Ad
controller.skipAd()

// toggle fullscreen mode
controller.toggleFullscreen()
```


## Listening for State Changes / Events

One of the things controller provides is ability to listen for state changes and events:
```kotlin
val controller: AdPlayerController = TODO()

// reading current state
Log.d("TAG", "Currect state is ${controller.state.value}")

// listening for state changes
coroutineScope.launch {
    controller.state.collect {
        if (it is AdPlayerState.Playing) {
            Log.d("TAG", "AdPlayer started a playback")
        }
    }
}

// listening for events
coroutineScope.launch {
    controller.events.collect {
        if (it is AdPlayerEvent.AdImpression) {
            Log.d("TAG", "Ad impressions was triggered")
        }
    }
}
```
