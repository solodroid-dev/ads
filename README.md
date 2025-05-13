<div align="center">
  <a href="https://solodroid.co.id/">
    <picture>
      <img src="https://raw.githubusercontent.com/solodroidev/content/uploads/images/logo.png" alt="Solodroid Logo" height="70">
    </picture>
  </a>
</div>

## solodroid-ads-sdk
Android library for displaying multiple Ad Networks developed by [solodroid](https://solodroid.co.id/)

### Step 1: Add repositories
Add it in your root `settings.gradle (Project Settings)` at the end of repositories:
```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url 'https://developer.huawei.com/repo/' }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
        maven { url 'https://android-sdk.is.com/' }
        maven { url 'https://unity3ddist.jfrog.io/artifactory/unity-mediation-mvn-prod-local/' }
        maven { url 'https://maven.wortise.com/artifactory/public' }
        maven { url 'https://artifact.bytedance.com/repository/pangle' }
        maven { url 'https://cboost.jfrog.io/artifactory/chartboost-ads/' }
        maven { url 'https://maven.ogury.co' }
        maven { url 'https://developer.huawei.com/repo/' }
    }
}
```

### Step 2: Add the dependency
```gradle
dependencies {
    implementation 'com.github.solodroid-dev.ads:[ad-sdk-name]:[version]'
}
```

## Ad Network Sdk options
### mega-ads-sdk
`mega-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Meta Audience Network, AppLovin Max, AppLovin Discovery, Start.io, Unity Ads, ironSource, Pangle, Huawei Petal Ads, Yandex Ads`

### mega-ads-sdk-no-is
`mega-ads-sdk-no-is:1.+'` with ad network support `AdMob, Ad Manager, Meta Audience Network, AppLovin Max, AppLovin Discovery, Start.io, Unity Ads, Pangle, Huawei Petal Ads, Yandex Ads`

### multi-ads-sdk
`multi-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Meta Audience Network, AppLovin Max, AppLovin Discovery, Start.io, Unity Ads, ironSource`

### multi-ads-sdk-no-is
`multi-ads-sdk-no-is:1.+'` with ad network support `AdMob, Ad Manager, Meta Audience Network, AppLovin Max, AppLovin Discovery, Start.io, Unity Ads`

### admob-ads-sdk
`admob-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager`

### facebook-ads-sdk
`facebook-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Meta Audience Network`

### applovin-ads-sdk
`applovin-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, AppLovin Max, AppLovin Discovery`

### startapp-ads-sdk
`startapp-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Start.io`

### unity-ads-sdk
`unity-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Unity Ads`

### ironsource-ads-sdk
`ironsource-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, ironSource`

### pangle-ads-sdk
`pangle-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Pangle`

### huawei-ads-sdk
`huawei-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Huawei Petal Ads`

### yandex-ads-sdk
`yandex-ads-sdk:1.+'` with ad network support `AdMob, Ad Manager, Yandex Ads`

### no-ads-sdk
`no-ads-sdk:1.+'` with ad network support `No Ads`