import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:developer' as developer;

class StructuredResponse {
  final String mStatus;
  var mMessage;
  var mData;

  StructuredResponse({
    required this.mStatus,
    this.mMessage,
    this.mData,
  });

  factory StructuredResponse.fromJson(Map<String, dynamic> json) {
    return StructuredResponse(
      mStatus: json['mStatus'],
      mMessage: json['mMessage'],
      mData: json['mData'],
    );
  }

  Map<String, dynamic> toJson() => {
        'mStatus': mStatus,
        'mMessage': mMessage,
        'mData': mData,
      };
}

class Idea {
  var mId;
  var mTitle;
  var mContent;
  var mLikes;

  Idea({
    this.mId,
    required this.mTitle,
    required this.mContent,
    this.mLikes,
  });

  factory Idea.fromJson(Map<String, dynamic> json) {
    return Idea(
      mId: json['mId'],
      mTitle: json['mTitle'],
      mContent: json['mContent'],
      mLikes: json['mLikes'],
    );
  }

  Map<String, dynamic> toJson() => {
        'mId': mId,
        'mTitle': mTitle,
        'mContent': mContent,
        'mLikes': mLikes,
      };
}
