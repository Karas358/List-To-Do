# List-To-Do
Android application that uses Room to store a to-do list.


Application was built using Android Studio using Java + SDK

The application uses Room to store the data, which is why we added the dependency on the gradle.
The application uses a recycler view to display the data, which is why we added the dependency on the gradle.

Grade dependencies

    implementation "androidx.recyclerview:recyclerview:1.1.0"
    implementation "androidx.recyclerview:recyclerview-selection:1.1.0-rc01"
    implementation 'com.google.android.material:material:1.1.0'
    def room_version = "2.2.5"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"
    implementation "androidx.room:room-guava:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"
    implementation 'it.xabaras.android:recyclerview-swipedecorator:1.2.2'
