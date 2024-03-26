import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart'
    show rootBundle; //rootBundle helps with certifcate
import 'package:postgres/postgres.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'dart:developer' as developer;
import 'models/item_model.dart';
import 'net/get_items_api.dart';
import 'net/send_items_api.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
//import 'package:file_picker/file_picker.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized(); //binds the certicate
  // Load the SSL/TLS certificate from the app's assets folder
  final certData =
      await rootBundle.load('assets/server.cer'); //certifcate issue

  // Create a new security context and add the certificate to its trusted certificates list
  final context = SecurityContext.defaultContext; //certificate issye
  context.setTrustedCertificatesBytes(
      certData.buffer.asUint8List()); //certificate issue

  runApp(MyApp()); //run app
}

/*class MyHttpOverrides extends HttpOverrides { ////possible fix for error
  @override
  HttpClient createHttpClient(SecurityContext? context) {
    return super.createHttpClient(context)
      ..badCertificateCallback =
          (X509Certificate cert, String host, int port) => true;
  }
}*/

//phase 2

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //HttpOverrides.global = MyHttpOverrides(); //possible fix for error
    return MaterialApp(
      title: 'Buzz App',
      home: GoogleSignInExample(),
    );
  }
}

class GoogleSignInExample extends StatefulWidget {
  @override
  _GoogleSignInExampleState createState() => _GoogleSignInExampleState();
}

class _GoogleSignInExampleState extends State<GoogleSignInExample> {
  GoogleSignIn _googleSignIn = GoogleSignIn(scopes: ['email']);
  GoogleSignInAccount? _currentUser;

  @override
  void initState() {
    super.initState();
    _googleSignIn.onCurrentUserChanged.listen((account) {
      setState(() {
        _currentUser = account;
      });
    });
    _googleSignIn.signInSilently();
  }

  Future<void> cache() async {
    //initialize
    SharedPreferences prefs = await SharedPreferences.getInstance();
    //save
    await prefs.setString('my_key', 'my_value');
    //retrieve
    String myValue = prefs.getString('my_key');
    //delete
    prefs.remove('my_key');
  }

  Future<void> handleSignIn() async {
    try {
      await _googleSignIn.signIn();
      setState(() {
        _currentUser = _googleSignIn.currentUser;
      });

      // Navigate to the IdeasPage if the user is signed in
      if (_currentUser != null) {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => IdeasPage()),
        );
      }
    } catch (error) {
      print(error);
    }
  }

  Future<void> handleSignOut() async {
    try {
      await _googleSignIn.signOut();
      setState(() {
        _currentUser = null;
      });
    } catch (error) {
      print(error);
    }
  }

  Widget buildUserInfo() {
    if (_currentUser != null) {
      String sexualIdentity =
          "Straight"; // Replace with the actual sexual identity of the user
      String genderOrientation = "Male"; //Replace with actual sexual identity
      String Note = "I Love Buzz!";
      return Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                margin: EdgeInsets.only(top: 16),
                child: CircleAvatar(
                  backgroundImage: _currentUser?.photoUrl != null
                      ? NetworkImage(_currentUser!.photoUrl!)
                      : null,
                  radius: 30,
                ),
              ),
              SizedBox(width: 16),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    margin: EdgeInsets.only(top: 25),
                    child: Text(
                      _currentUser!.displayName ?? '',
                      style: TextStyle(
                        fontSize: 12,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  SizedBox(height: 8),
                  Container(
                    margin: EdgeInsets.only(top: 1),
                    child: Text(
                      _currentUser!.email ?? '',
                      style: TextStyle(fontSize: 12),
                    ),
                  ),
                  SizedBox(height: 8),
                  Container(
                    margin: EdgeInsets.only(top: 1),
                    child: GestureDetector(
                      onTap: () {
                        showDialog(
                          context: context,
                          builder: (context) => AlertDialog(
                            title: Text("Edit Sexual Identity"),
                            content: TextField(
                              decoration: InputDecoration(
                                hintText: "Enter your sexual identity",
                              ),
                              onChanged: (value) {
                                sexualIdentity = value;
                              },
                            ),
                            actions: [
                              ElevatedButton(
                                onPressed: () {
                                  Navigator.pop(context);
                                },
                                child: Text("Cancel"),
                              ),
                              ElevatedButton(
                                onPressed: () {
                                  setState(() {
                                    // Save the updated sexual identity
                                  });
                                  Navigator.pop(context);
                                },
                                child: Text("Save"),
                              ),
                            ],
                          ),
                        );
                      },
                      child: Row(
                        children: [
                          Text(
                            "Sexual Identity: ",
                            style: TextStyle(fontSize: 12),
                          ),
                          Text(
                            sexualIdentity,
                            style: TextStyle(
                              fontSize: 12,
                              color: Colors.blue,
                              decoration: TextDecoration.underline,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  SizedBox(height: 8),
                  Container(
                    margin: EdgeInsets.only(top: 1),
                    child: GestureDetector(
                      onTap: () {
                        showDialog(
                          context: context,
                          builder: (context) => AlertDialog(
                            title: Text("Edit Gender Orientation"),
                            content: TextField(
                              decoration: InputDecoration(
                                hintText: "Enter your gender orientation",
                              ),
                              onChanged: (value) {
                                genderOrientation = value;
                              },
                            ),
                            actions: [
                              ElevatedButton(
                                onPressed: () {
                                  Navigator.pop(context);
                                },
                                child: Text("Cancel"),
                              ),
                              ElevatedButton(
                                onPressed: () {
                                  setState(() {
                                    // Save the updated sexual identity
                                  });
                                  Navigator.pop(context);
                                },
                                child: Text("Save"),
                              ),
                            ],
                          ),
                        );
                      },
                      child: Row(
                        children: [
                          Text(
                            "Gender Orientation: ",
                            style: TextStyle(fontSize: 12),
                          ),
                          Text(
                            genderOrientation,
                            style: TextStyle(
                              fontSize: 12,
                              color: Colors.blue,
                              decoration: TextDecoration.underline,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  SizedBox(height: 8),
                  Container(
                    margin: EdgeInsets.only(top: 1),
                    child: GestureDetector(
                      onTap: () {
                        showDialog(
                          context: context,
                          builder: (context) => AlertDialog(
                            title: Text("Edit Note"),
                            content: TextField(
                              decoration: InputDecoration(
                                hintText: "Enter your Note",
                              ),
                              onChanged: (value) {
                                Note = value;
                              },
                            ),
                            actions: [
                              ElevatedButton(
                                onPressed: () {
                                  Navigator.pop(context);
                                },
                                child: Text("Cancel"),
                              ),
                              ElevatedButton(
                                onPressed: () {
                                  setState(() {
                                    // Save the updated sexual identity
                                  });
                                  Navigator.pop(context);
                                },
                                child: Text("Save"),
                              ),
                            ],
                          ),
                        );
                      },
                      child: Row(
                        children: [
                          Text(
                            "Note: ",
                            style: TextStyle(fontSize: 12),
                          ),
                          Text(
                            Note,
                            style: TextStyle(
                              fontSize: 12,
                              color: Colors.blue,
                              decoration: TextDecoration.underline,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  SizedBox(height: 32.0),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => IdeasPage()),
                      );
                    },
                    child: Text('Go to Ideas Page'),
                  ),
                ],
              ),
            ],
          ),
          SizedBox(height: 16),
          Expanded(
            child: Align(
              alignment: Alignment.bottomCenter,
              child: Container(
                margin: EdgeInsets.only(bottom: 32),
                child: ElevatedButton(
                  onPressed: handleSignOut,
                  child: Text('Sign Out'),
                ),
              ),
            ),
          ),
        ],
      );
    } else {
      return ElevatedButton(
        onPressed: handleSignIn,
        child: Text('Sign in with Google'),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile Page'),
      ),
      body: Center(
        child: buildUserInfo(),
      ),
    );
  }

  List<Idea> _ideas = [];
  Future<void> _refresh() async {
    setState(() {});
  }
}

class IdeasPage extends StatefulWidget {
  const IdeasPage({Key? key}) : super(key: key);

  @override
  _IdeasPageState createState() => _IdeasPageState();
}
/*
class _IdeasPageState extends State<IdeasPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Ideas Page'),
      ),
      body: Center(
        child: Text('This is the ideas page'),
      ),
    );
  }
}
*/

/*
class MyApp extends StatelessWidget {
  //const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ideas App',
      home: IdeasPage(),
    );
  }
}*/

/*class Idea {
  final String title;
  final String description;
  int likes;

  Idea({required this.title, required this.description, this.likes = 0});
}*/
/*
class IdeasPage extends StatefulWidget {
  //const IdeasPage({super.key, required this.title});
  @override
  _IdeasPageState createState() => _IdeasPageState();
}*/

class _IdeasPageState extends State<IdeasPage> {
  List<Idea> _ideas = [];
  Future<void> _refresh() async {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Ideas Page'),
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ReadIdeas(),
            SizedBox(height: 32),
            Text(
              'Comments',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 16),
            CommentSection(),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _addIdea,
        child: Icon(Icons.add),
      ),
    );
  }

  void _addIdea() async {
    final result = await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => AddIdeaPage()),
    );
    if (result != null) {
      setState(() {
        _ideas.add(result);
        //_updateIdeasTable(result);
      });
    }
  }
}

class CommentSection extends StatefulWidget {
  const CommentSection({Key? key}) : super(key: key);

  @override
  _CommentSectionState createState() => _CommentSectionState();
}

class _CommentSectionState extends State<CommentSection> {
  final TextEditingController _commentController = TextEditingController();
  List<String> _comments = [];

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          padding: EdgeInsets.all(8.0),
          child: Row(
            children: [
              Expanded(
                child: TextField(
                  controller: _commentController,
                  decoration: InputDecoration(hintText: 'Add a comment'),
                ),
              ),
              SizedBox(width: 8.0),
              ElevatedButton(
                onPressed: () {
                  setState(() {
                    _comments.add(_commentController.text);
                    _commentController.clear();
                  });
                },
                child: Text('Post'),
              ),
            ],
          ),
        ),
        Divider(),
        ListView.builder(
          shrinkWrap: true,
          itemCount: _comments.length,
          itemBuilder: (context, index) {
            return ListTile(
              title: Text(_comments[index]),
            );
          },
        ),
      ],
    );
  }
}

class AddIdeaPage extends StatefulWidget {
  @override
  _AddIdeaPageState createState() => _AddIdeaPageState();
}

class _AddIdeaPageState extends State<AddIdeaPage> {
  final _titleController = TextEditingController();
  final _descriptionController = TextEditingController();

  void _refresh() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Add Idea'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(
              controller: _titleController,
              decoration: InputDecoration(
                labelText: 'Title',
              ),
            ),
            SizedBox(height: 16.0),
            TextField(
              controller: _descriptionController,
              decoration: InputDecoration(
                labelText: 'Description',
              ),
            ),
            SizedBox(height: 16.0),
            ElevatedButton(
              child: Text('Add'),
              onPressed: () {
                /*final idea = Idea(
                  title: _titleController.text,
                  description: _descriptionController.text,
                  likes: 0,
                );*/
                var send = addNewIdea(
                    _titleController.text, _descriptionController.text);
                Navigator.pop(context);
                _refresh;
              },
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    _titleController.dispose();
    _descriptionController.dispose();
    super.dispose();
  }
}

class ReadIdeas extends StatefulWidget {
  const ReadIdeas({super.key});
  @override
  State<ReadIdeas> createState() => _ReadIdeasState();
}

class _ReadIdeasState extends State<ReadIdeas> {
  late Future<List<Idea>> _future_ideas;

  Future<void> _refresh() async {
    Future<List<Idea>> _new_ideas = fetchIdeas();
    setState(() {
      _future_ideas = _new_ideas; //update the state with new data
    });
  }

  @override
  void initState() {
    super.initState();
    _future_ideas = fetchIdeas(); //from the data model file
  }

  @override
  Widget build(BuildContext context) {
    var fb = FutureBuilder<List<Idea>>(
      future: _future_ideas,
      builder: (BuildContext context, AsyncSnapshot<List<Idea>> snapshot) {
        Widget child;
        if (snapshot.hasData) {
          developer.log('`using` ${snapshot.data}', name: 'ideas');
          // create  listview to show one row per array element of json response
          child = //Expanded(
              /*child:*/ ListView.builder(
                  scrollDirection: Axis.vertical,
                  shrinkWrap: true,
                  padding: const EdgeInsets.all(16.0),
                  itemCount: snapshot.data!.length,
                  itemBuilder: (context, i) {
                    return Column(
                      children: <Widget>[
                        ListTile(
                          title: Text(
                              "${snapshot.data![i].mId} ${snapshot.data![i].mTitle}"),
                          subtitle: Text("${snapshot.data![i].mContent}"),
                          trailing: Row(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              LikeIcon(
                                  //creates a LikeIcon for each Message
                                  id: snapshot.data![i].mId,
                                  title: snapshot.data![i].mTitle,
                                  description: snapshot.data![i].mContent,
                                  likes: snapshot.data![i].mLikes),
                              //Text("${snapshot.data![i].mLikes}"),
                            ],
                          ),
                        ),
                        Divider(height: 1.0),
                      ],
                    );
                  }) /*)*/;
        } else if (snapshot.hasError) {
          // newly added
          child = Text('${snapshot.error}');
        } else {
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },
    );
    return fb;
  }
}

class LikeIcon extends StatefulWidget {
  final int id;
  final String title;
  final String description;
  final int likes;

  const LikeIcon(
      {super.key,
      required this.id,
      required this.title,
      required this.description,
      required this.likes});

  @override
  _LikeIconState createState() => _LikeIconState();
}

class _LikeIconState extends State<LikeIcon> {
  bool _iconClicked = false;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        var res = updateLike(
            widget.id, widget.title, widget.description, widget.likes);
        setState(() {
          _iconClicked = !_iconClicked;
        });
      },
      child: Icon(
        Icons.favorite,
        color: _iconClicked ? Color.fromARGB(255, 244, 54, 54) : Colors.grey,
        size: 30.0,
      ),
    );
  }
}

Future<void> uploadImage() async {
  final result = await FilePicker.platform.pickFiles(type: FileType.image);
  if (result == null || result.files.isEmpty) {
    // User canceled the file selection
    return;
  }

  final file = File(result.files.single.path!);
  final fileName = result.files.single.name;
}

//class FilePicker {}
