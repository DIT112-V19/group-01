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

// DifferentialControl: controling a vehicle by
// applying different speed on each side, like a tank.
DifferentialControl control(leftMotor, rightMotor);
SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

//variables for the obstacle avoidance
const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 5; //D5
const unsigned int MAX_DISTANCE = 30;
SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
int distance;

// Setup code runs once
void setup() {
  Serial.begin(9600);
  odometerSetUp();

  // Cruise Control controls the car speed in meters/second
  //using default PID values
  // car.enableCruiseControl();
}

// Loop code runs repeatedly
void loop() {
  measureDistance();
  setCarMoveForward();
  if(distance < MAX_DISTANCE && distance > 0 ){
    changeRandomDirection();
  }
}

// Get sensor distance measurement, in centimetre
void measureDistance(){
  distance = front.getDistance();
}

void setCarMoveForward() {
  car.setAngle(0);
  car.setSpeed(50);
}

float decideRandomNumber() {
  return random(0, 1);
}

// if way is bloked, randomly choose left or right
//and turn continuously until way is free
void changeRandomDirection() {
  if (decideRandomNumber() > 0.5) {
    while (distance < MAX_DISTANCE && distance > 0 ) {
      changeDirectionRight();
      measureDistance();
    };
  }
  else {
    while (distance < MAX_DISTANCE && distance > 0 ) {
      changeDirectionLeft();
    };
  }
}

void changeDirectionRight() {
  //Arguments(leftMotor speed capacity, rightMotor speed capacity)
  car.overrideMotorSpeed(75, -75);
  //delay so the car has enough time to turn
  delay(500);
}

void changeDirectionLeft() {
  //Arguments(leftMotor speed capacity, rightMotor speed capacity)
  car.overrideMotorSpeed(-75, 75);
  //delay so the car has enough time to turn
  delay(500);
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
