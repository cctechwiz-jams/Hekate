import RPi.GPIO as GPIO ## Import GPIO library
import time ## Import 'time' library. Allows us to use 'sleep'

pin = 8  

GPIO.setmode(GPIO.BOARD) ## Use board pin numbering
GPIO.setup(pin , GPIO.OUT) ## Setup GPIO Pin 14  to OUT

GPIO.output(pin, GPIO.LOW )## Switch on pin 14 

print "Done" ## When loop is complete, print "Done"


