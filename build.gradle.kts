plugins {
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
}


// It is a good practice to define common repositories for all modules here.
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
