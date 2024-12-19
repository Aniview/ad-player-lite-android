# Content and Playlist

Both content and playlists are only available for insteam tags.


## Managing Playlist

Playlist contains list of content videos that are played one after the other. We can read current playlist like this:

```kotlin
val controller: AdPlayerController

val playlist = controller.playlist.value
Log.d("TAG", "playlist is $playlist")
```

One important note here is that playlist is loaded by the library *asynchronously* and might not be available right after the controller is created. This might result in the above API returning empty playlist. It is adviced, in most cases, for the application to track when playlist changes:

```kotlin
val controller: AdPlayerController

coroutineScope.launch {
    controller.playlist.collect {
        Log.d("TAG", "new playlist is $it")
    }
}
```


## Currently Played Content

`AdPlayerController` can be used to track which content is currently playing:
```kotlin
val controller: AdPlayerController

// reading current content
Log.d("TAG", "${controller.content.value} is currently playing")

// tracking content changes
coroutineScope.launch {
    controller.content.collect {
        Log.d("TAG", "$it is now playing")
    }
}

// reading playback progress
coroutineScope.launch {
    val position = controller.getContentPosition()
    val duration = controller.getContentDuration()
    Log.d("TAG", "content at $position / $duration")
}
```


## Choosing Content to Play

In order to play different video from the playlist we can use these functions:
```kotlin
val controller: AdPlayerController

// play next video
controller.playNextContent()

// play previous video
controller.playPrevContent()

// play specific video
controller.playContentByIndex(3)
```
