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
    console.log("email: ", UID);

    var FirebaseTokenGenerator = require("firebase-token-generator");
    var tokenGenerator = new FirebaseTokenGenerator("pSFof16OWEXpaCR37Fx11d6C7RQZTyYFRtOaiNPH");
    var token = tokenGenerator.createToken({uid: UID});

    Hekate.authWithCustomToken(token, function(error, authData) {
        if (error) {
            console.log("Login Failed!", error);
            throw new Error(error);
        } else {
            console.log("Login Succeeded!", authData);
            startListening();
        }
    });
};

var startListening = function(){
    var usrDir = Hekate.child("User/"+UID);

    usrDir.on("value", function (DataSnapshot) {
        console.log("key: ", DataSnapshot.key());
        console.log("val: ", DataSnapshot.val());
        console.log("roomVal: ", DataSnapshot.child("room").val());
    }, function (errorObject) {
        console.log("The read failed: " + errorObject.code);
    });
};