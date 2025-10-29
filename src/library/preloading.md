# Preloading
`Available since 2.6.0`

Sometimes it is required to preload the content before displaying it,
usually to avoid showing the loading progress to the end user.

This can be achieved by calling `AdPlayerController::preload` method:

```kotlin
val controller: AdPlayerController
controller.preload(Size(300, 250))
```

Preloading requires size of the target placement.
This size is required for the library to know which size of the content to request.
In most cases it should be the size of the placement (for in-read) or the size of the screen (for interstitials).

## Progress Tracking

Calling `preload` method only schedules the content to be preloaded but this is asynchronous operation and might take some time.

Tracking its progress is the same as tracking state of the controller - content is
considered ready when state is different from `AdPlayerState.Preparing`.

Here is an example how to check when preloading has finished:
```kotlin
controller.value.state.first { it !is AdPlayerState.Preparing }
// content is now ready to be shown
```

Or by using non-async code:
```kotlin
controller.addStateListener(object : AdPlayerStateListener {
    override fun onAdPlayerStateChanged(newState: AdPlayerState) {
        if (newState !is AdPlayerState.Preparing) {
            controller.removeStateListener(this)
            // content is now ready to be shown
        }
    }
})
```
