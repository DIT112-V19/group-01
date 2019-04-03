#include <Smartcar.h>

const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 5; //D5
const unsigned int MAX_DISTANCE = 20;
BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

SimpleCar car(control);


void setup() {
  
  Serial.begin(9600);
  car.setSpeed(30);
  
}

void loop() {
  if(front.getDistance() < MAX_DISTANCE && front.getDistance() > 0 ){
    car.setSpeed(0);
   }
}
