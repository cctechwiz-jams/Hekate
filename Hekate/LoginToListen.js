var Firebase = require('firebase');
var Hekate = new Firebase('https://hekate.firebaseio.com');

var email = '';
var password = '';

var readline1 = require('readline');

var rl1 = readline1.createInterface({
  input: process.stdin,
  output: process.stdout
});
rl1.question("Enter your email: ", function(answer) {
    email = answer;
    rl1.close();
});

var readline2 = require('readline');

var rl2 = readline2.createInterface({
  input: process.stdin,
  output: process.stdout
});
rl2.question("Enter your password:(Will be displayed in plain text!) ", function(answer) {
    email = answer;
    rl2.close();
});

console.log("email: ", email);
console.log("password: ", password);