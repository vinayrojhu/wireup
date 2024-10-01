package com.example.wireup.Navigation

enum class Screen {
    SPLASH,
    HOME,
    AUTHENTICATION,
    FLIPFLOP,
    PODCAST,
    NODE,
    PROFILE,
    ABOUT,
    ACCOUNT,
    EDIT,
    FRIENDS,
    SAVED,
    SETTINGS,
    SEARCH,


    HELP ,

    READMORE ,
    VIDEOPODCASTOPENED ,
    AUDIOPODCASTOPENED,
    LIKED ,

    PROFILEVIEWMODE,
    FOLLOWNODES,

    TAGS

}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Authentication : NavigationItem(Screen.AUTHENTICATION.name)
    object Home : NavigationItem(Screen.HOME.name)
    object FlipFlop : NavigationItem(Screen.FLIPFLOP.name)
    object Podcast : NavigationItem(Screen.PODCAST.name)
    object Node : NavigationItem(Screen.NODE.name)
    object Profile : NavigationItem(Screen.PROFILE.name)
    object About : NavigationItem(Screen.ABOUT.name)
    object Account : NavigationItem(Screen.ACCOUNT.name)
    object Edit : NavigationItem(Screen.EDIT.name)
    object Friends : NavigationItem(Screen.FRIENDS.name)
    object Saved : NavigationItem(Screen.SAVED.name)
    object Settings : NavigationItem(Screen.SETTINGS.name)
    object Search : NavigationItem(Screen.SEARCH.name)

    object ReadMore : NavigationItem(Screen.READMORE.name)
    object VideoPodcastOpened : NavigationItem(Screen.VIDEOPODCASTOPENED.name)
    object AudioPodcastOpened : NavigationItem(Screen.AUDIOPODCASTOPENED.name)
    object Liked : NavigationItem(Screen.LIKED.name)

    object ProfileViewMode : NavigationItem(Screen.PROFILEVIEWMODE.name)

    object FollowNodes : NavigationItem(Screen.FOLLOWNODES.name)

    object Help : NavigationItem(Screen.HELP.name)

    object Tags : NavigationItem(Screen.TAGS.name)
}