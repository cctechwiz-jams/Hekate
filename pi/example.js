var gpio = require('onoff').Gpio,
    pin_num = 8,
    pin = new gpio(pin_num, 'out');

pin.read(function(err, value){
    if(err){
        console.log("Something went wrong...");
        throw err;
    }

    console.log(value);
});

function exit() {
    pin.unexport();
    process.exit();
}

process.on('SIGINT', exit);
