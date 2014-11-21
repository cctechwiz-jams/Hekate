console.log("Listening for Firebase to update...")

var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');

var user = '';

Hekate.on("value", function (DataSnapshot) {
    console.log("key: ", DataSnapshot.key());
    console.log("val: ", DataSnapshot.val());

    console.log("hasChildren: ", DataSnapshot.hasChildren());

    DataSnapshot.forEach(function (childSnapshot) {
        user = childSnapshot.key();
        console.log("childkey:", childSnapshot.key());
        console.log("Childval:", childSnapshot.val());

        console.log("childhasChildren: ", childSnapshot.hasChildren());
        childSnapshot.forEach(function (childchildSnapshot) {
            console.log("childchildkey:", childchildSnapshot.key());
            console.log("childchildval:", childchildSnapshot.val());
        });

    });

    var data = DataSnapshot.exportVal();
    printChange(data);


}, function (errorObject) {
    console.log("The read failed: " + errorObject.code);
});

var printChange = function (data) {
    console.log("Command to execute: ", data[user]["command"]);
};
