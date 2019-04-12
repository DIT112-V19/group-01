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
  t = Serial.read();
  Serial.println(t);
switch (t)
  {

    

  case 'w':
    
    car.setSpeed(speedDrive);
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


    default:
    car.setSpeed(0);


  }
  
}
