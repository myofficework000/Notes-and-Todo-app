# Notes & To-Do

[![Android CI](https://github.com/myofficework000/Notes-and-Todo-app/actions/workflows/main.yml/badge.svg?branch=master)](https://github.com/myofficework000/Notes-and-Todo-app/actions/workflows/main.yml)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Min SDK](https://img.shields.io/badge/minSdk-24-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)

A native Android notes and task manager focused on quick capture, simple organization, and distraction-free daily planning. The app is built with Kotlin, the AndroidX stack, Room persistence, Navigation, and View Binding.

## Highlights

- Create, edit, organize, complete, and delete to-do items.
- Capture rich notes with custom colors, text sizes, links, favorites, and optional locking.
- Keep data on-device with a Room-backed local database.
- Navigate cleanly between the dashboard, notes, and task details.
- Follow an MVVM-oriented structure with `ViewModel`, `LiveData`, coroutines, and DAOs.

## Tech stack

| Area | Technology |
| --- | --- |
| Language | Kotlin 2.4.21 |
| Build | Android Gradle Plugin 9.4.1 · Gradle 9.6 |
| UI | Android Views · View Binding · Material Components · ConstraintLayout |
| Architecture | ViewModel · LiveData · Navigation |
| Data | Room 2.8.5 · Kotlin Symbol Processing (KSP) |
| Async | Kotlin Coroutines 1.11.0 |
| Testing | JUnit · AndroidX Test · Espresso · MockK |

## Requirements

- Android Studio with JDK 17 or later
- Android SDK Platform 37
- A device or emulator running Android 7.0 (API 24) or newer

## Build and test

```bash
./gradlew assembleDebug
./gradlew test
```

The debug APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Screens

<p align="center">
  <img src="app/src/main/assets/1.png" width="200" alt="Dashboard" />
  <img src="app/src/main/assets/2.png" width="200" alt="To-do list" />
  <img src="app/src/main/assets/3.png" width="200" alt="Notes" />
  <img src="app/src/main/assets/4.png" width="200" alt="Note editor" />
</p>

<p align="center">
  <img src="app/src/main/assets/5.png" width="200" alt="Task details" />
  <img src="app/src/main/assets/6.png" width="200" alt="Customization" />
  <img src="app/src/main/assets/7.png" width="200" alt="App flow" />
  <img src="app/src/main/assets/8.png" width="200" alt="Notes and tasks" />
</p>

## Contributing

Issues and pull requests are welcome. Please open an issue before proposing large changes so the approach can be discussed first.

## Author

Built by [Abhishek Pathak](https://github.com/myofficework000). If this project is useful, a star helps others discover it.
