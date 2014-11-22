console.log("Making changes in Firebase...");

var usrId = "User1535";
var command = "Hello Chaz.";

var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');
var User1 = Hekate.child(usrId);
User1.set({command: command});

