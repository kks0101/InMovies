# InMovies
Browse through your favorite Movies.

![Demo](Screenshots/main.gif) ![Demo](Screenshots/search.gif) ![Demo](Screenshots/deepLinkShare.gif)


A simple Android Application that uses [The Movie Db](https://www.themoviedb.org/documentation/api) to show - now playing, popular movies and search for specific movie.

## Tech Stack

* JAVA
* [JetPack](https://developer.android.com/jetpack)

  > Jetpack is a suite of libraries to help developers follow best practices, 
  > reduce boilerplate code, and write code that works consistently across Android 
  > versions and devices so that developers can focus on the code they care about. 
  
  * LiveData
    * Life cycle aware observable, ensures that we don't need to update the UI every time app data changes.
  * ViewModel
    * To outlive the persistance of data over short lived life cycle of fragments or activity
  * Room
    * Object Relational Mapping to facilitate database handling - maps database objects to JAVA objects
    * abstraction layer over SQLite
* Architecture
  * MVVM architecture (Model View, ViewModel)
    * removes tight coupling between each component
    * separates the Business logic from the UI components
  * Repository Pattern
    * creating data layer agnostic apps: UI components does not know where the data comes from
    * facilitates multiple data source (both remote and local)
* [Retrofit2](https://square.github.io/retrofit/)
  * turns HTTP API to JAVA interface
  * uses OkHttp to handle API calls
  * GSON JSON adapter
* [Glide](https://github.com/bumptech/glide)
  * for loading images
* RxJAVA
  * to make API calls/ database query
  * facilitates asynchronous and event based programming

## Features

* Bookmark specific movie
* Using deeplink to get details of specific movie
* Share movies
* Search for specific movie - updating the result as the user types
* Display bookmarks
* Repository Pattern
* Uses ViewModels to store UI state
* Uses RxJAVA for making calls to db and network

