#include <Smartcar.h>

// Configuration of left and right odometers, from SmartCar Library example.
DirectionlessOdometer leftOdometer(50), rightOdometer(60);
const int LEFT_ODOMETER_PIN = 2;
const int RIGHT_ODOMETER_PIN = 3;

// Config of gyroscopePrintAngle
//GYROSCOPE_OFFSET is set to 9 because of calibration purposes
const int GYROSCOPE_OFFSET = 9;
GY50 gyroscope(GYROSCOPE_OFFSET);

// Control configuration
BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);
SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  // Still have to find what this does
  delay(1500);

  odometerSetUp();

  delay(2000);
}

void loop() {
  // put your main code here, to run repeatedly:
  car.setAngle(90);
  delay(2000);


  // odometerPrintDistance();
  // gyroscopePrintAngle();
}

// Set up of left and right odometers. Extracted from SmartCar example
void odometerSetUp() {
  leftOdometer.attach(LEFT_ODOMETER_PIN, []() {
    leftOdometer.update();
  });
  rightOdometer.attach(RIGHT_ODOMETER_PIN, []() {
    rightOdometer.update();
  });
}

void odometerPrintDistance() {
  // Serial.print(leftOdometer.getDistance());
  Serial.print("\t\t");
  Serial.println(rightOdometer.getDistance());
}

void gyroscopePrintAngle() {
  // Update the readings of the gyroscope
  gyroscope.update();
  Serial.println(gyroscope.getHeading());
}
