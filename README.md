# Hekate
====
The DIY home automation project.

## Raspberry Pi Setup
```sh
(as root and assuming fresh install of Rasbian)
$ apt-get update
$ apt-get install curl git
$ curl --silent --location https://deb.nodesource.com/setup_0.12 | bash -
$ apt-get install --yes nodejs
$ apt-get install --yes build-essential
$ git clone https://github.com/chaz2x4/Hekate.git
$ cd Hekate/pi
$ npm install
```
Then see if it works:
```sh
$ node -v
$ npm -v
$ node example.js
```

## Android App Setup
// TODO
