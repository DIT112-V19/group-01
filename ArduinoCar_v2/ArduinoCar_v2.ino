#include <Smartcar.h>
#include <SoftwareSerial.h>

const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 5; //D5

const unsigned int MAX_DISTANCE = 30;
int distance;
int period = 700;
unsigned long time_now = 0;
int speedDrive = 50;
int speedTurn = 38;
char t ;
BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
SoftwareSerial BTSerial(0,1);
SimpleCar car(control);

void setup() {
  
  Serial.begin(9600);
  //car.setSpeed(speedDrive);
}

void loop() {
  // distance = front.getDistance();
  // if(distance < MAX_DISTANCE && distance > 0 ){
  //  time_now = millis();
  //  while (millis() < time_now + period) {
  //    leftMotor.setSpeed(speedTurn);
  //    rightMotor.setSpeed(-speedTurn);
  //    if (distance > MAX_DISTANCE) {
  //      break;
  //    }
  //  }
  //  speedTurn = speedTurn * (-1);
    
  // }
  // car.setSpeed(speedDrive);

  while (Serial.available()>0){

    t=Serial.read();
    Serial.println(t);
  }
  if (t=='f'){
    car.setSpeed(speedDrive);
  }
  if (t=='b'){
    car.setSpeed(-speedDrive);
  }
  if (t=='l'){
    leftMotor.setSpeed(-speedDrive);
    rightMotor.setSpeed(speedDrive);
  }
  if (t=='r'){
    leftMotor.setSpeed(speedDrive);
    rightMotor.setSpeed(-speedDrive);
  }
  if (t=='s'){
    car.setSpeed(0);
  }
}
