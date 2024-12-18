# Launching Interstitials

Unlike in-read mode, interstitials don't require any placement view. Instead the are always displayed in fullscreen. Interstitials, unlike regular fullscreen, will also be automatically closed after the first video finishes.

## Creating Interstitial Controller

An `AdPlayerInterstitialController` must be created before launching interstitial:
```kotlin
val tag = AdPlayer.getTag(context, pubId = "...", tagId = "...")
val controller: AdPlayerInterstitialController = tag.newInterstitialController()
```

Additional configuration can also be provided during creation if needed:
```kotlin
val controller: AdPlayerInterstitialController = tag.newInterstitialController {
    // change background color of the window
    backgroundColor = Color.BLACK

    // disable back buttons/gestures so interstitial cannot be skipped
    dismissOnBack = false

    // this callback will be triggered when interstitial is closed
    onDismissListener = {
        Log.d("TAG", "Interstitial was closed")
    }
}
```

Some of these configurations above can be also configured on the admin portal. Values provided though the code will always override portal configuration.


## Releasing Interstitial Controller

`AdPlayerInterstitialController`, like any other controller, must be always released when no longer needed to release underlying resources:
```kotlin
val controller: AdPlayerInterstitialController = TODO()
controller.release()
```


## Launching or Dismissing Interstitial

Interstitial can be launched after creating `AdPlayerInterstitialController`:
```kotlin
val controller: AdPlayerInterstitialController = TODO()
controller.launchInterstitial()
```

Interstitial can also be forcibly closed if needed:
```kotlin
val controller: AdPlayerInterstitialController = TODO()
controller.dismissInterstitial()
```

It is important to remember that each controller can at most launch one interstitial at the same time.
