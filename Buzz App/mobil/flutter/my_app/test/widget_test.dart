// This is a basic Flutter widget test.
//
// To perform an interaction with a widget in your test, use the WidgetTester
// utility in the flutter_test package. For example, you can send tap and scroll
// gestures. You can also use WidgetTester to find child widgets in the widget
// tree, read text, and verify that the values of widget properties are correct.

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:my_app/main.dart';

void main() {
  testWidgets('Counter increments smoke test', (WidgetTester tester) async {
    // Build our app and trigger a frame.
    await tester.pumpWidget(MyApp());

    // Verify that our counter starts at 0.
    expect(find.text('0'), findsOneWidget);
    expect(find.text('1'), findsNothing);

    // Tap the '+' icon and trigger a frame.
    await tester.tap(find.byIcon(Icons.add));
    await tester.pump();

    // Verify that our counter has incremented.
    expect(find.text('0'), findsNothing);
    expect(find.text('1'), findsOneWidget);

    //Test that the camera icon is displayed on the UI, and that tapping it opens the Camera API.
//Test that the gallery icon is displayed on the UI, and that tapping it opens the Gallery API.
//Test that the Camera API successfully captures a picture, and that it is displayed on the UI.
//Test that the Gallery API successfully selects a picture, and that it is displayed on the UI.
//Test that the app can post a picture to The Buzz using the appropriate API, and that it is displayed on the UI.
//Test that the local cache is correctly implemented using SharedPreferences, and that content is not re-downloaded unnecessarily.
//Test that content that is already saved to the device can be posted using the appropriate API, and that it is displayed on the UI.
// Test that the camera icon is displayed on the UI,
// and that tapping it opens the Camera API.

    group('buzz', () {
      testWidgets('camera icon should open Camera API',
          (WidgetTester tester) async {
        // Set up the app state.
        MyApp app = new MyApp();

        // Mock the Camera API.
        MockCameraAPI mockCameraAPI = new MockCameraAPI();
        when(mockCameraAPI.isAvailable()).thenReturn(true);

        // Inject the mocked Camera API.
        app.cameraAPI = mockCameraAPI;

        // Render the app.
        await tester.pumpWidget(app);

        // Find the camera icon.
        Finder cameraIconFinder = find.byIcon(Icons.camera);

        // Tap the camera icon.
        await tester.tap(cameraIconFinder);

        // Wait for the widget tree to rebuild.
        await tester.pump();

        // Find the Camera API widget.
        Finder cameraAPIFinder = find.byType(CameraAPI);

        // Check that the Camera API widget is displayed.
        expect(cameraAPIFinder, findsOneWidget);

        // Check that the Camera API was called.
        verify(mockCameraAPI.open()).called(1);
      });
    });
  });
}
