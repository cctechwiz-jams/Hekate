var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');

var UID = '';

var readline = require('readline');

var rl1 = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});
rl1.question("Enter your UID (Found in your Android Hekate settings): ", function(answer) {
    UID = answer;
    rl1.close();
    authenticateForFirebase();
});

var authenticateForFirebase = function() {
    var FirebaseTokenGenerator = require("firebase-token-generator");
    var tokenGenerator = new FirebaseTokenGenerator("pSFof16OWEXpaCR37Fx11d6C7RQZTyYFRtOaiNPH");
    var token = tokenGenerator.createToken({uid: UID});

    Hekate.authWithCustomToken(token, function(error, authData) {
        if (error) {
            console.log("Login Failed!", error);
            console.log("");
            throw new Error(error);
        } else {
            console.log("Login Succeeded!", authData);
            console.log("");
            startListening();
        }
    });
};

var startListening = function(){
    var commandPath = Hekate.child("User/"+UID+"/Commands");
    var roomPath = Hekate.child("User/"+UID+"/Rooms");
    var commands = [];
    var rooms = [];

    commandPath.on("value", function (DataSnapshot) {
        commands = DataSnapshot.val();
        updateCommands(commands);
    }, function (errorObject) {
        console.log("The read failed: " + errorObject.code);
        console.log("");
    });

    roomPath.on("value", function (DataSnapshot) {
        rooms = DataSnapshot.val();
        updateRooms(rooms);
    }, function (errorObject) {
        console.log("The read failed: " + errorObject.code);
        console.log("");
    });
};

var updateCommands = function(commandsArray){
    for(var propertyName in commandsArray) {
        if (propertyName == "CMD") {
            console.log("Executing command " + commandsArray[propertyName]);
        }
        if (propertyName == "Python") {
            console.log("Running Python script " + commandsArray[propertyName]);
        }
    }
    console.log("");

};

var updateRooms = function(RoomsArray){
    for(var propertyName in RoomsArray){
        if(RoomsArray[propertyName] == "unlocked"){
            console.log("unlocking the " + propertyName + " door.");
        }
        if(RoomsArray[propertyName] == "locked"){
            console.log("locking the " + propertyName + " door.");
        }
    }
    console.log("");
};