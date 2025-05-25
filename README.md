# Vibe Music Player 🎵

## 介绍 📖

**Vibe Music Player** 是一款基于 **Android** 平台开发的本地音乐播放器应用。本项目旨在为用户提供简洁、高效的本地音乐播放体验。

## 主要特性 ✨

- **本地音乐管理**
  - 扫描并导入本地音乐文件
  - 支持多种音频格式（MP3、WAV、FLAC等）
  - 自动获取音乐元数据（封面、艺术家、专辑等）

- **播放功能**
  - 基础播放控制（播放/暂停、上一首/下一首）
  - 播放模式切换（列表循环、单曲循环、随机播放、顺序播放）

- **用户界面**
  - 现代化 Material Design 界面
  - 支持深色/浅色主题切换
  - 专辑封面显示

- **其他功能**
  - 音乐文件搜索
  - 收藏夹功能

## 技术栈 🛠️

- **开发语言**: Java
- **最低支持版本**: Android 7.0 (API 24)
- **目标版本**: Android 13 (API 33)
- **构建工具**: Gradle 8.6
- **依赖管理**: Gradle 8.6

## 系统需求 ⚙️

- **Android 版本**: 7.0 及以上
- **开发环境**:
  - Android Studio Arctic Fox 或更高版本
  - JDK 11 或更高版本
  - Gradle 7.0 或更高版本

## 安装与使用 🚀

1. **下载安装**
   - 从 [Releases](https://github.com/Alex-LiSun/vibe-music-player/releases) 页面下载最新版本的 APK 文件
   - 在 Android 设备上安装 APK 文件

2. **首次使用**
   - 启动应用后，授予存储权限以扫描本地音乐文件
   - 等待扫描完成，即可开始使用

## 开发指南 👨‍💻

1. **克隆项目**
   ```bash
   git clone https://github.com/Alex-LiSun/vibe-music-player.git
   ```

2. **导入项目**
   - 使用 Android Studio 打开项目
   - 等待 Gradle 同步完成

3. **构建项目**
   - 在 Android Studio 中选择 Build > Build Bundle(s) / APK(s) > Build APK(s)
   - 或使用命令行：
     ```bash
     ./gradlew assembleDebug
     ```

## 项目结构 📁

```
app/
├── build/          # 构建输出目录
├── src/
│   ├── main/
│   │   ├── java/  # Java 源代码
│   │   ├── res/   # 资源文件
│   │   └── AndroidManifest.xml
│   └── test/      # 测试代码
└── build.gradle   # 构建配置
```

## 项目演示 📺

视频地址：[https://www.bilibili.com/video/******/]

## 项目截图 📷

<table>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/navigation.png" alt="导航栏" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/navigation_black.png" alt="导航栏（深色）" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/library.png" alt="曲库界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/library_black.png" alt="曲库界面（深色）" width="200"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/album.png" alt="专辑界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/album_detail.png" alt="专辑详情界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/artist.png" alt="歌手界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/artist_detail.png" alt="歌手详情界面" width="200"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/favourite.png" alt="收藏界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/play.png" alt="播放界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/search.png" alt="搜索界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/feedback_1.png" alt="反馈界面" width="200"></td>
  </tr>
  <tr>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/feedback_2.png" alt="反馈界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/communicate.png" alt="联系界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/setting.png" alt="设置界面" width="200"></td>
    <td><img src="https://github.com/Alex-LiSun/vibe-music-player/blob/main/img/about.png" alt="关于界面" width="200"></td>
  </tr>
</table>
## 贡献指南 ❤️

欢迎提交 Issue 和 Pull Request 来帮助改进项目！

1. Fork 本仓库
2. 创建你的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交你的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启一个 Pull Request

## 常见问题 (FAQ) ❓

- **应用无法扫描到音乐文件？**
  - 确保已授予存储权限
  - 检查音乐文件是否位于受支持的目录中
  - 确认音乐文件格式是否受支持

- **播放时出现卡顿？**
  - 检查设备存储空间是否充足
  - 确认音乐文件是否完整
  - 尝试清除应用缓存

## 免责声明 ⚠️

本项目仅供学习和研究使用。使用本项目时请遵守相关法律法规，尊重音乐版权。

## 许可证 📄

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情
