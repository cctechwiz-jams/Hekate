console.log("hello world");
var usrId = "User1535";
var command = "do something more";

var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');
var User1 = Hekate.child(usrId);
User1.set({command: command});