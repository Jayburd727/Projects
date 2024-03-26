import 'dart:convert';
import 'dart:developer' as developer;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../models/item_model.dart';

Future<Idea> addNewIdea(
    String title, String content, String link, dynamic image) async {
  int likes = 0;
  final http.Response response = await http.post(
    Uri.parse('https://2023sp-group17.dokku.cse.lehigh.edu/messages'),
    headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: jsonEncode(<String, dynamic>{
      'mTitle': title,
      'mContent': content,
      'mLikes': likes,
      'mLink': link,
      'mImage': image
    }),
  );
  if (response.statusCode == 200) {
    return Idea.fromJson(json.decode(response.body));
  } else {
    throw Exception('Failed to create new idea.');
  }
}

Future<Idea> updateLike(int id, String title, String content, int likes) async {
  int newlikes = likes + 1;

  String url = 'https://2023sp-group17.dokku.cse.lehigh.edu/messages/' + '$id';

  final http.Response response = await http.put(
    Uri.parse(url),
    headers: <String, String>{
      'Content-Type': 'application/json; charset=UTF-8',
    },
    body: jsonEncode(<String, dynamic>{
      'mTitle': title,
      'mContent': content,
      'mLikes': newlikes
    }),
  );
  if (response.statusCode == 200) {
    return Idea.fromJson(json.decode(response.body));
  } else {
    throw Exception('Failed to add like to an exisiting idea.');
  }
}
