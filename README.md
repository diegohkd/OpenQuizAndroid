# Open Quiz Android

| dev | master |
|-----|--------|
| ![CI](https://github.com/diegohkd/OpenQuizAndroid/workflows/CI/badge.svg?branch=dev) | ![CI](https://github.com/diegohkd/OpenQuizAndroid/workflows/CI/badge.svg?branch=master) |

Quiz app for Android built using [Open Trivia Database](https://opentdb.com/).
This project is not intended to be published. I have it only for studying purpose and/or as a code sample.

## Details
- Architectural pattern:
    - MVVM + Repository.
    - Data Binding.
    - LiveData for ViewModel communication with View.
    - Coroutines and LiveData for the communication between ViewModel and Repository.
    - Multi-modules structure.
- Automated tests:
    - Unit tests, Integration tests and Instrumentation tests, but mainly Unit tests.
    - Mockk + Espresso frameworks.
- Dependency injection:
    - Koin.
- Navigation:
    - Single Activity with multiple Fragments using Navigation component. 
- User Authentication:
    - Google authentication using Firebase.
- UI
    - A flat custom view for a countdown timer displayed in a quiz.     
- Git
    - pre-commit hook that runs Ktlint. 
- CI/CD
    - Github Action that runs Ktlin and automated tests when a PR is opened.
    - Fastlane + Github Action to distribute app on Firebase App Distribution.

Note: The UI is as simple as possible. A robust UI has never been the goal of this project.

## Compilation Guide
- Add your own keystore inside `app` folder and a `keystore.properties` file in the root folder. The `keystore.properties` should look like this:
```
storePassword=<fill out>
keyPassword=<fill out>
keyAlias=<fill out>
storeFile=<fill out>
```
- Add `google.services.json` inside `data/src/` folder.
- Build/Run using Android Studio or gradlew.

## What's next
I don't have a very detailed plan for the next features, but here are some ideas:
https://github.com/diegohkd/OpenQuizAndroid/projects/1
