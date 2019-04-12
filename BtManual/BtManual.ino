#include <Smartcar.h>
#include <SoftwareSerial.h>

int speedDrive = 50;
char t ;
BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);
SoftwareSerial BTSerial(0,1);
SimpleCar car(control);
unsigned long time_now = 0;

void setup() {

  Serial.begin(9600);

}

void loop()
{
  t = Serial.read();
  Serial.println(t);
  switch (t)
  {

    case 'w':

    time_now = millis();
    while (millis() < time_now + 500) {
    
    car.setSpeed(speedDrive);
    }
    break;

    case 's':

    car.setSpeed(-speedDrive);
    break;

    case 'a':

    leftMotor.setSpeed(-speedDrive);
    rightMotor.setSpeed(speedDrive);
    break;

    case 'd':

    leftMotor.setSpeed(speedDrive);
    rightMotor.setSpeed(-speedDrive);
    break;


    case 't':
    car.setSpeed(0);
    break;
  }

}
