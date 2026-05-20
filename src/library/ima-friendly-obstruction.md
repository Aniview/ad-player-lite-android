# IMA Friendly Obstructions

The Interactive Media Ads (IMA) SDK enforces strict guidelines regarding view obstructions
during ad playback. When external views overlap or obscure the IMA ad container, it may result in
inaccurate impression tracking, reduced interaction metrics, and potential revenue impact.

To ensure compliance, the AdPlayerLite library automatically registers all of its
native controls as IMA-friendly obstructions.

If your application includes custom views that may overlap with the `AdPlayerView`,
you should explicitly register them as friendly obstructions. This can be configured as follows:

```kotlin
val tag: AdPlayerTag
val obstruction: View

tag.newInReadController {
    it.imaFriendlyObstructionsProvider = AdPlayerImaFriendlyObstructionsProvider {
        listOf(
            AdPlayerImaFriendlyObstructionsProvider.Obstruction(
                view = obstruction,
                purpose = FriendlyObstructionPurpose.VIDEO_CONTROLS,
                detailedReason = "custom video controls",
            ),
        )
    }
}