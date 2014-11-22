console.log("Listening for Firebase to update...")

var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');

var user = '';
var dataKey = '';
var dataProperty = '';

Hekate.on("value", function (DataSnapshot) {
    console.log("key: ", DataSnapshot.key());
    console.log("val: ", DataSnapshot.val());

    console.log("hasChildren: ", DataSnapshot.hasChildren());

    if(DataSnapshot.hasChildren()) {
        DataSnapshot.forEach(function (childSnapshot) {
            user = childSnapshot.key();

            if(childSnapshot.hasChildren()) {
                childSnapshot.forEach(function (childchildSnapshot) {
                    dataKey = childchildSnapshot.key();
                    dataProperty = childchildSnapshot.val();
                });
            }

        });
    }

    unlock(user, dataKey, dataProperty);

}, function (errorObject) {
    console.log("The read failed: " + errorObject.code);
});

var unlock = function (u, dk, dp) {
    console.log("User: ", u)
    console.log("DataKey: ", dk)
    console.log("DataProperty: ", dp)
};
