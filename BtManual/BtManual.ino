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

void loop()
{
switch (Serial.available() > 0)
  {

    t = Serial.read();
    Serial.printLn(t);

  case 1:
    t == 'w';
    car.setSpeed(speedDrive);
    break;

  case 2:
    t == 's';
    car.setSpeed(-speedDrive);
    break;

  case 3:
    t == 'a';
    leftMotor.setSpeed(-speedDrive);
    rightMotor.setSpeed(speedDrive);
    break;

  case 4:
    t == 'd';
    leftMotor.setSpeed(speedDrive);
    rightMotor.setSpeed(-speedDrive);
    break;  


  }
  
}
