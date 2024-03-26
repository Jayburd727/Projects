import 'dart:convert';
import 'dart:developer' as developer;
import 'package:http/http.dart' as http;
import '../models/item_model.dart';

Future<List<Idea>> fetchIdeas() async {
  //final response = await http
  //.get(Uri.parse('http://2023sp-group17.dokku.cse.lehigh.edu/messages'));
  final response = await http
      .get(Uri.parse('https://2023sp-group17.dokku.cse.lehigh.edu/messages'));

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response, then parse the JSON.
    final List<Idea> returnData;
    var res = jsonDecode(response.body);
    print('json decode: $res');

    StructuredResponse structuredRes = StructuredResponse.fromJson(res);
    var data = structuredRes.mData;
    if (data is List) {
      returnData =
          (data as List<dynamic>).map((x) => Idea.fromJson(x)).toList();
    } else if (data is Map) {
      returnData = <Idea>[Idea.fromJson(data as Map<String, dynamic>)];
    } else {
      developer
          .log('ERROR: Unexpected json response type (was not a List or Map).');
      returnData = List.empty();
    }
    return returnData;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Did not receive success status code from request.');
  }
}
