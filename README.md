# List-To-Do
Demo android application that stores a to-do list, allowing the user to add, mark a task as complete and remove it. The application uses the Room persistence library. 

Application was built using Android Studio using Java + Android SDK

Grade dependencies

    //Recycler View Dependencies
    implementation "androidx.recyclerview:recyclerview:1.1.0"
    implementation "androidx.recyclerview:recyclerview-selection:1.1.0-rc01"
    
    //Material Deisgn Despendencies
    implementation 'com.google.android.material:material:1.1.0'
    
    //Room for storage
    def room_version = "2.2.5"
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    implementation "androidx.room:room-rxjava2:$room_version"
    implementation "androidx.room:room-guava:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"
    
    //Swipe Decoration For RecyclerView
    implementation 'it.xabaras.android:recyclerview-swipedecorator:1.2.2'
    
    



    
    
