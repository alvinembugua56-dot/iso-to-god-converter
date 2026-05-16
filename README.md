# ISO to GOD Converter

A comprehensive Android application for converting ISO (disc image) files to GOD (Game on Demand) format, compatible with Android devices and Xbox 360 console.

## Features

- 📁 **ISO File Selection** - Browse and select ISO files from device storage
- ⚙️ **Conversion Engine** - Parses ISO structure and repackages into GOD format
- 📊 **Progress Tracking** - Real-time conversion progress with percentage and speed
- 🎮 **Xbox 360 Compatible** - Output GOD files compatible with Xbox 360 console
- 🔄 **Batch Processing** - Convert multiple ISO files sequentially
- 📱 **Android Native** - Built with Kotlin for optimal performance
- 💾 **File Management** - View, organize, and manage converted GOD files
- ⚡ **Optimized Performance** - Efficient ISO parsing and GOD packaging

## Technology Stack

- **Language**: Kotlin
- **Android SDK**: API 24+ (Android 7.0)
- **Build System**: Gradle (Kotlin DSL)
- **Architecture**: MVVM + Repository Pattern
- **Async**: Coroutines & Flow
- **Database**: Room Database
- **UI**: Material Design 3
- **Logging**: Timber

## Requirements

- Android 7.0 (API 24) or higher
- Minimum 100MB free storage
- Read/Write permissions for file access

## Installation & Setup

```bash
# Clone repository
git clone https://github.com/alvinembugua56-dot/iso-to-god-converter.git
cd iso-to-god-converter

# Build
./gradlew build

# Install
./gradlew installDebug
```

## Usage

1. Launch the app
2. Tap "Select ISO File"
3. Browse and select your ISO file
4. Enter game information
5. Tap "Convert to GOD"
6. Monitor progress in real-time

## Project Structure

```
iso-to-god-converter/
├── app/
│   ├── src/main/
│   │   ├── java/com/alvinembugua56/isoconverter/
│   │   │   ├── activities/
│   │   │   ├── services/
│   │   │   ├── utils/
│   │   │   ├── models/
│   │   │   └── viewmodel/
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
└── build.gradle.kts
```

## Key Components

### Activities
- **MainActivity** - File selection and app entry point
- **ConversionActivity** - Real-time conversion progress
- **SettingsActivity** - App settings and configuration

### Services
- **ConversionService** - Background conversion with notifications

### Utilities
- **ISOParser** - ISO 9660 format validation and metadata extraction
- **GODPackager** - GOD package creation and validation
- **ConversionEngine** - Main conversion orchestration

### Models
- **ConversionProgress** - Progress tracking data
- **ConversionTask** - Task persistence model
- **GameInfo** - Game metadata

## Permissions

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
```

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## License

MIT License - see LICENSE file

## Support & Troubleshooting

### Conversion Fails
- Ensure ISO file is not corrupted
- Verify sufficient storage space
- Check file permissions

### Slow Conversion
- Close other applications
- Use faster storage device
- Monitor device temperature

## Roadmap

- [ ] Pause/Resume functionality
- [ ] Batch conversion queue
- [ ] Advanced metadata editor
- [ ] Cloud upload
- [ ] Xbox Live integration
- [ ] Desktop companion app

---

**Developed with ❤️ for gaming enthusiasts**
