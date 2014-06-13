# Android Studio Bootcamp

This is the example code for a workshop I held about getting started with Android
development using Android Studio.  It contains a few elements that I find to be
important tools when developing Android applications and the code should hopefully
be easy to follow.

## UI

The application will fetch Gists (usually randomly pasted code snippets) from
the GitHub JSON API and present them in a simple UI using components such as
RelativeLayout, ImageView, TextView, ScrollView and LinearLayout. The UI should
support any screen DPI.

It will use a separate layout file to represent an individual Gist and reuse this
for each for every fetched object.

## Fetching from a remote API

Network requests will be run in a separate thread and the code will describe how
to do this and at the same time update the UI from the UI thread.

I have added dependencies in the Android Studio Bootcamp/build.gradle file for
the Jackson JSON processor. While I'm not utilizing their excellent
serialize/deserialize features it is still a very good way to handle JSON
