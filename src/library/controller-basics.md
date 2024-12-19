# Controlling Playback

## What is Controller?

Controller is the main entity responsible for loading and controlling (pausing, skipping, unmuting, etc.) a content. All controllers inherit `AdPlayerController`, which provides most common functionality.

There are also more specialized controllers available:
- `AdPlayerInReadController` - for working with in-read content
- `AdPlayerInterstitialController` - for displaying interstitials


## Creating Controller

There are few ways to get controller instance:

1. When loading content via `AdPlayerView`:
```kotlin
val view: AdPlayerView
val controller: AdPlayerInReadController = view.load(...)
```

2. Reading already loaded controller from `AdPlayerView`:
```kotlin
val view: AdPlayerView
val controller: AdPlayerInReadController? = view.controller
```

3. Creating controller manually:
```kotlin
val tag = AdPlayer.getTag(context, pubId = "...", tagId = "...")

val controller: AdPlayerInReadController = tag.newInReadController()
// or
val controller: AdPlayerInReadController = tag.newInterstitialController()
```


## Releasing Controller

All controllers must be always released when no longer needed to free underlying resources:
```kotlin
val controller: AdPlayerController
controller.release()
```

In cases when `AdPlayerInReadController` is attached to the `AdPlayerView`, it will be automatically released with the view itself.


## Controlling Video Playback

Most basic functionality, that controllers allow, is to control video playback:
```kotlin
val controller: AdPlayerController

// pause playback
controller.pause()

// resume playback
controller.resume()

// skip current Ad
controller.skipAd()
```

`AdPlayerInReadController` also provides additional functinality:
```kotlin
val controller: AdPlayerInReadController

// toggle fullscreen mode
controller.toggleFullscreen()
```


## Listening for State Changes / Events

One of the things controller provides is ability to listen for state changes and events:
```kotlin
val controller: AdPlayerController

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