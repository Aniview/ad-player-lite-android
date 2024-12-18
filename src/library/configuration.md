# Project Configuration

Before using AdPlayer Lite library project must be properly configured.

1. Add Maven repository to `settings.gradle.kts`:
```kts
dependencyResolutionManagement {
    repositories {
        maven("https://us-central1-maven.pkg.dev/mobile-sdk-fd2e4/adservr-maven")
    }
}
```

2. Add library dependencies to the app's module `build.gradle.kts`:
```kts
dependencies {
    implementation("com.adservrs:ad-player-lite:1.0.0")
}
```

3. Add GMS configuration to the `AndroidManifest.xml`:
```xml
<application>
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="ca-app-pub-6746653557725812~6678258028" />
    <meta-data
        android:name="com.google.android.gms.ads.flag.OPTIMIZE_INITIALIZATION"
        android:value="true" />
    <meta-data
        android:name="com.google.android.gms.ads.flag.OPTIMIZE_AD_LOADING"
        android:value="true" />
</application>
```

Now your project is configured and you can move to [creating first in-read placement](./in-read-basic.md).
