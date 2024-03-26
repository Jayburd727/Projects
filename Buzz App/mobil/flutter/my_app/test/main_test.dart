import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_app/main.dart';
import 'package:my_app/models/item_model.dart';

void main() {
  group('IdeasPage', () {
    testWidgets('shows list of ideas', (tester) async {
      final ideas = [
        Idea(title: 'Idea 1', description: 'Description 1', likes: 0),
        Idea(title: 'Idea 2', description: 'Description 2', likes: 0),
      ];

      await tester.pumpWidget(MyApp());

      final ideasPage = find.byType(IdeasPage);
      expect(ideasPage, findsOneWidget);

      for (final idea in ideas) {
        final title = find.text(idea.title);
        final description = find.text(idea.description);
        final likes = find.text('${idea.likes}');

        expect(title, findsOneWidget);
        expect(description, findsOneWidget);
        expect(likes, findsOneWidget);
      }
    });

    testWidgets('adds new idea', (tester) async {
      final idea = Idea(title: 'New Idea', description: 'New Description');

      await tester.pumpWidget(MyApp());

      final ideasPage = find.byType(IdeasPage);
      expect(ideasPage, findsOneWidget);

      final addButton = find.byType(FloatingActionButton);
      expect(addButton, findsOneWidget);

      await tester.tap(addButton);
      await tester.pumpAndSettle();

      final addIdeaPage = find.byType(AddIdeaPage);
      expect(addIdeaPage, findsOneWidget);

      final titleField = find.byKey(ValueKey('title_field'));
      final descriptionField = find.byKey(ValueKey('description_field'));
      final saveButton = find.text('Save');

      expect(titleField, findsOneWidget);
      expect(descriptionField, findsOneWidget);
      expect(saveButton, findsOneWidget);

      await tester.enterText(titleField, idea.title);
      await tester.enterText(descriptionField, idea.description);

      await tester.tap(saveButton);
      await tester.pumpAndSettle();

      final title = find.text(idea.title);
      final description = find.text(idea.description);

      expect(title, findsOneWidget);
      expect(description, findsOneWidget);
    });

    testWidgets('likes idea', (tester) async {
      final idea =
          Idea(title: 'Idea 1', description: 'Description 1', likes: 0);

      await tester.pumpWidget(MyApp());

      final ideasPage = find.byType(IdeasPage);
      expect(ideasPage, findsOneWidget);

      final title = find.text(idea.title);
      final description = find.text(idea.description);
      final likes = find.text('${idea.likes}');

      expect(title, findsOneWidget);
      expect(description, findsOneWidget);
      expect(likes, findsOneWidget);

      final likeButton = find.byIcon(Icons.thumb_up);
      expect(likeButton, findsOneWidget);

      await tester.tap(likeButton);
      await tester.pumpAndSettle();

      final updatedLikes = find.text('${idea.likes + 1}');
      expect(updatedLikes, findsOneWidget);
    });
  });

  //Phase 2 Psuedocode for tests
  /*
  // Test login and logout with Google OAuth
test('Google OAuth login and logout', () {
  // Call login with Google OAuth
  await app.loginWithGoogleOAuth();

  // Check that the user is logged in
  expect(app.isUserLoggedIn(), true);

  // Call logout
  await app.logout();

  // Check that the user is logged out
  expect(app.isUserLoggedIn(), false);
});

// Test adding an editable comment to an idea
test('Add editable comment to idea', () {
  // Create a new idea
  Idea idea = Idea(title: 'New Idea', description: 'Description of new idea');

  // Add a comment to the idea
  Comment comment = Comment(text: 'New comment');
  idea.addComment(comment);

  // Check that the comment was added
  expect(idea.comments.length, 1);
  expect(idea.comments[0].text, 'New comment');

  // Edit the comment
  Comment editedComment = Comment(text: 'Edited comment');
  idea.editComment(comment, editedComment);

  // Check that the comment was edited
  expect(idea.comments.length, 1);
  expect(idea.comments[0].text, 'Edited comment');
});

// Test displaying user profile information
test('Display user profile information', () {
  // Create a new user
  User user = User(username: 'testuser', email: 'testuser@example.com', sexualOrientation: 'straight');

  // Display the user's profile
  app.displayUserProfile(user);

  // Check that the profile information is displayed correctly
  expect(app.usernameField.value, 'testuser');
  expect(app.emailField.value, 'testuser@example.com');
  expect(app.sexualOrientationField.value, 'straight');
});

// Test upvoting and downvoting an idea
  test('Upvote and downvote an idea', () {
  // Create a new idea
  Idea idea = Idea(title: 'New Idea', description: 'Description of new idea');

  // Upvote the idea
  idea.upvote();

  // Check that the number of votes is updated
  expect(idea.numVotes, 1);

  // Downvote the idea
  idea.downvote();

  // Check that the number of votes is updated
  expect(idea.numVotes, 0);
});
*/
}
