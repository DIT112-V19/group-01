#include <Smartcar.h>
#include <SoftwareSerial.h>

int speedDrive = 50;
char t ;
BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);
SoftwareSerial BTSerial(0,1);
SimpleCar car(control);

void setup() {
  
  Serial.begin(9600);
  
}

void loop() {
 
  while (Serial.available()>0){

    t=Serial.read();
    Serial.println(t);
  }
  if (t=='w'){
    car.setSpeed(speedDrive);
  }
  if (t=='s'){
    car.setSpeed(-speedDrive);
  }
  if (t=='a'){
    leftMotor.setSpeed(-speedDrive);
    rightMotor.setSpeed(speedDrive);
  }
  if (t=='d'){
    leftMotor.setSpeed(speedDrive);
    rightMotor.setSpeed(-speedDrive);
  }
  else {
    car.setSpeed(0);
  }
}
