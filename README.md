# ClearScore Android Application

## Project Overview
ClearScore is an Android application that allows users to view and monitor their credit score information. The app retrieves credit score data from a remote API and presents it in an intuitive user interface built with Jetpack Compose.

## Features
- Display credit score information with visual representation
- Real-time data fetching from ClearScore API
- Basic offline support with HTTP response caching (10MB)
- Secure communication with certificate pinning
- Error handling with user-friendly messages

## Technical Implementation
- **UI**: Jetpack Compose for modern, declarative UI
- **Architecture**: MVVM with Clean Architecture principles
- **Dependency Injection**: Hilt
- **Networking**: Ktor client with OkHttp
- **Caching**: HTTP response caching for basic offline capability
- **Security**: Certificate pinning for secure API communication
- **Testing**: Comprehensive test coverage including unit and UI tests

## Testing Strategy
- **Unit Tests**: Testing individual components in isolation
- **Repository Testing**: 
  - Using Ktor MockEngine to simulate HTTP responses
  - Using test fakes (e.g., FakeClearScoreService) to provide controlled test behaviors
- **UI Tests**: End-to-end testing with WireMock for API response mocking
- **Idling Resources**: Custom implementation for synchronizing UI tests with asynchronous operations
- **Test URL Provider**: Custom mechanism for redirecting network requests during tests

## Assumptions Made
- Users primarily care about their current credit score value and its relation to the maximum possible score
- Network connection may be intermittent, requiring some offline handling
- Security is a priority, hence the implementation of certificate pinning
- App should gracefully handle and communicate errors to users

## Known Issues
- Certificate pinning might cause connectivity issues if the server certificates are rotated
- HTTP caching provides only basic offline capabilities (previously viewed data)
- Current error handling could be enhanced with more specific error messages based on failure types

## Future Improvements
- Implement true offline capability with local database persistence
- Improve accessibility features
- Add localization support for multiple languages

## Setup & Configuration
The application uses Gradle with Kotlin DSL for build configuration. Key environment variables are managed through BuildConfig properties.

### Testing Configuration
The application includes a custom test infrastructure with:
- Ktor MockEngine for simulating HTTP responses in unit tests
- Test fakes for providing controlled test behaviors
- WireMock server for UI test HTTP response mocking
- Custom IdlingResource implementation for Compose UI testing
- Test URL provider mechanism to override API endpoints during tests
