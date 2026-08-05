

# Vibe Music Player 🎵

## Introduction 📖

**Vibe Music Player** is a local music player application developed for the **Android** platform. This project aims to provide users with a simple and efficient local music playback experience.

## Key Features ✨

- **Local Music Management**
  - Scan and import local music files
  - Supports multiple audio formats (MP3, WAV, FLAC, etc.)
  - Automatically retrieves music metadata (cover art, artist, album, etc.)

- **Playback Features**
  - Basic playback controls (play/pause, previous/next)
  - Playback mode switching (loop list, repeat single, shuffle, sequential)

- **User Interface**
  - Modern Material Design interface
  - Supports dark/light theme switching
  - Album cover display

- **Additional Features**
  - Music file search
  - Favorites functionality

## Tech Stack 🛠️

- **Development Language**: Java
- **Minimum Supported Version**: Android 7.0 (API 24)
- **Target Version**: Android 13 (API 33)
- **Build Tool**: Gradle 8.6
- **Dependency Management**: Gradle 8.6

## System Requirements ⚙️

- **Android Version**: 7.0 and above
- **Development Environment**:
  - Android Studio Arctic Fox or later
  - JDK 17 or later
  - Gradle 8.0 or later

## Installation & Usage 🚀

1. **Download & Install**
   - Download the latest APK file from the [Releases](https://github.com/Alex-LiSun/vibe-music-player/releases) page
   - Install the APK file on your Android device

2. **First-Time Use**
   - After launching the app, grant storage permissions to scan local music files
   - Wait for the scan to complete, then you can start using it

## Development Guide 👨‍💻

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Alex-LiSun/vibe-music-player.git
   ```

2. **Import the Project**
   - Open the project in Android Studio
   - Wait for Gradle synchronization to complete

3. **Build the Project**
   - In Android Studio, select Build > Build Bundle(s) / APK(s) > Build APK(s)
   - Or use the command line:
     ```bash
     ./gradlew assembleDebug
     ```

## Project Structure 📁

```
app/
├── build/          # Build output directory
├── src/
│   ├── main/
│   │   ├── java/  # Java source code
│   │   ├── res/   # Resource files
│   │   └── AndroidManifest.xml
│   └── test/      # Test code
└── build.gradle   # Build configuration
```

## Project Demo 📺

Video link: [https://www.bilibili.com/video/BV1tXjVzGEvG/]

## Project Screenshots 📷

<table>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/navigation.png" alt="Navigation bar" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/navigation_black.png" alt="Navigation bar (Dark)" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/library.png" alt="Library screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/library_black.png" alt="Library screen (Dark)" width="200"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/album.png" alt="Album screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/album_detail.png" alt="Album detail screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/artist.png" alt="Artist screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/artist_detail.png" alt="Artist detail screen" width="200"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/favourite.png" alt="Favorites screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/play.png" alt="Player screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/search.png" alt="Search screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/feedback_1.png" alt="Feedback screen" width="200"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/feedback_2.png" alt="Feedback screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/communicate.png" alt="Contact screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/setting.png" alt="Settings screen" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/about.png" alt="About screen" width="200"></td>
  </tr>
</table>

## Contribution Guide ❤️

Feel free to submit Issues and Pull Requests to help improve the project!

1. Fork this repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Frequently Asked Questions (FAQ) ❓

- **App cannot scan music files?**
  - Ensure storage permissions have been granted
  - Check if music files are in supported directories
  - Confirm if the music file format is supported

- **Playback lagging/stuttering?**
  - Check if the device has sufficient storage space
  - Confirm the music files are complete/not corrupted
  - Try clearing the app cache

## Disclaimer ⚠️

This project is for learning and research purposes only. Please comply with relevant laws and regulations and respect music copyrights when using this project.

## License 📄

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
