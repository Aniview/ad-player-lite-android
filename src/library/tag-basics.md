# Tag Basics

## What is a Tag?

Tag is a factory used to create controllers.


## Creating a Tag

Tag can be created by using `AdPlayer` global object:
```kotlin
val tag = AdPlayer.getTag(context, pubId = pubId, tagId = tagId) {
    // override default environment
    // * since 1.0.0
    environment = "tg1"

    // override package name used for requesting ADs and analytics
    // * since 3.1.0
    packageName = "com.organization.application"

    // additional macros used for requesting ADs
    // * since 3.4.0
    macros = mapOf(
        "AV_APPNAME" to "My Awesome Application",
        "AV_APPSTOREURL" to "https://play.google.com/store/apps/details?id=com.awesome.app",
    )
}
```
