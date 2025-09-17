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

`AdPlayerInReadController` also provides additional functionality:
```kotlin
val controller: AdPlayerInReadController

// toggle fullscreen mode
controller.toggleFullscreen()
```


## Controlling Audio Volume
`Available since 2.3.0`

Audio volume level can be changed as following:
```kotlin
val controller: AdPlayerController

// set max volume
controller.setVolumeLevel(1f)

// set min volume (mute)
controller.setVolumeLevel(0f)

// set average volume level
controller.setVolumeLevel(0.5f)
```

Volume changes can be observed through the `AdPlayerEvent.AdVolumeChange` and `AdPlayerEvent.ContentVolumeChange` events.


## Listening for State Changes / Events

One of the things controller provides is ability to listen for state changes and events:
```kotlin
val controller: AdPlayerController

// reading current state
Log.d("TAG", "Current state is ${controller.state.value}")

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


## Listening for Placement Type Changes
`Available since 2.4.0`

AtPlayer Tag can be attached to different types of placements (like in-read, fullscreen, interstitial, etc.).
These placement type changes can be observed:
```kotlin
val controller: AdPlayerController

// reading current placement type
Log.d("TAG", "Current placement type is ${controller.placement.value}")

// listening for placement type changes
coroutineScope.launch {
    controller.placement.collect {
        Log.d("TAG", "Placement type changed to $it")
    }
}

// listening for placement type changes with java-style listener
controller.addPlacementTypeListener {
    Log.d("TAG", "Placement type changed to $it")
}
```
