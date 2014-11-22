console.log("Making changes in Firebase...");

var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');

var UID = 'simplelogin:20';
var Path = "User/"+UID+"/";
var URI = "";

var authenticateForFirebase = function() {

    var FirebaseTokenGenerator = require("firebase-token-generator");
    var tokenGenerator = new FirebaseTokenGenerator("pSFof16OWEXpaCR37Fx11d6C7RQZTyYFRtOaiNPH");
    var token = tokenGenerator.createToken({uid: UID});

    Hekate.authWithCustomToken(token, function(error, authData) {
        if (error) {
            console.log("Login Failed!", error);
            throw new Error(error);
        } else {
            console.log("Login Succeeded!", authData);
            makeChanges();
        }
    });
};
authenticateForFirebase();

var makeChanges = function(){

//    URI = Hekate.child(Path+"Rooms");
//    URI.set([{Kitchen: "locked"},{LivingRoom: "unlocked"},{Garage: "locked"}]);

//    URI = Hekate.child(Path+"Pin");
//    URI.set(1234);

//    URI = Hekate.child(Path);
//    URI.update({Pin: 9874});

//    URI = Hekate.child(Path+"Commands");
//    URI.set([{CMD: "ping 8.8.8.8"},{Python: "blinkLED.py"},{CMD: "ifconfig"}]);

//    URI = Hekate.child(Path+"Commands");
//    URI.on('value', function(data){
//        console.log(data.val());
//    });

};
