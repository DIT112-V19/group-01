#include <Smartcar.h>
#include <SoftwareSerial.h>

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

//declaring button pin
//int sensorVal = 0;
bool buttonIsPressed = false;
bool exitManualControl = false;


//declaring pins for ultrasonic sensors
//const int TRIGGER_PIN_REAR_SENSOR = A4;
const int ECHO_PIN_REAR_SENSOR = A5;

//variables for the front sensor
const int TRIGGER_PIN_FRONT_SENSOR = 5; //D6
const int ECHO_PIN_FRONT_SENSOR = 6; //D5

const unsigned int MAX_DISTANCE = 30;

//SR04 back (TRIGGER_PIN_REAR_SENSOR, ECHO_PIN_REAR_SENSOR, MAX_DISTANCE);
SR04 front(TRIGGER_PIN_FRONT_SENSOR, ECHO_PIN_FRONT_SENSOR, MAX_DISTANCE);

//setting variables to 0 so they'll give correct value
int distanceFront = 0;
int distanceBack = 0;

//the trigger for car manouver
char trigger;
char option;

//bluetooth configuration ??
SoftwareSerial BTSerial(0,1);

// Setup code runs once
void setup() {
  pinMode(4, INPUT_PULLUP);
  Serial.begin(9600);
  odometerSetUp();

  // Cruise Control controls the car speed in meters/second
  //using default PID values
  // car.enableCruiseControl();
}

// Loop code runs repeatedly
void loop() {

  option = Serial.read();
  switch (option)
  {
    case 'f':
    buttonIsPressed = false;
    while (buttonIsPressed == false) {
      automaticObstacleAvoidance();
      checkIfButtonIsPressed();
    }
    break;

    case 'z':
    exitManualControl = false;
    while (exitManualControl == false ){
      carManualControl();
    }
    break;
  }
}

void automaticObstacleAvoidance(){
  measureDistance();
  setCarMoveForward();

  // && distance > 0 because of Arduino sensor bug
  if(distanceFront < MAX_DISTANCE && distanceFront > 0 ){
    stopCar();
    changeRandomDirection();
  }
}

void checkIfButtonIsPressed(){
  int sensorVal = digitalRead(4);
  Serial.println(sensorVal);

  if (sensorVal == HIGH) {
    measureDistance();
    setCarMoveForward();
  }
  else {
    buttonIsPressed = true;
    stopCar();
  }
}

void carManualControl(){
  trigger = Serial.read();
  Serial.println(trigger);
  switch (trigger)
  {
    case 'w':
    setCarMoveForward();
    break;

    case 's':
    setCarMoveBackwards();
    break;

    case 'a':
    changeDirectionLeft();
    setCarMoveForward();
    break;

    case 'd':
    changeDirectionRight();
    setCarMoveForward();
    break;

    case 'x':
    stopCar();
    break;

    case 'v' :
    exitManualControl = true;
    stopCar();
    break;
  }
}

// Randomly chooses left or right and turn continuously until way is free
void changeRandomDirection() {
  float randomNumber = generateRandomNumber();
  while (distanceFront < MAX_DISTANCE && distanceFront > 0 ) {
    if (randomNumber > 4) {
      changeDirectionRight();
    }
    else {
      changeDirectionLeft();
    };
    measureDistance();
  }
}

long generateRandomNumber() {
  return random(0, 10);
}

void changeDirectionRight() {
  //Arguments(leftMotor speed capacity, rightMotor speed capacity)
  car.overrideMotorSpeed(50, -50);
  //delay so the car has enough time to turn
  delay(150);
  stopCar();
}

void changeDirectionLeft() {
  //Arguments(leftMotor speed capacity, rightMotor speed capacity)
  car.overrideMotorSpeed(-50, 50);
  //delay so the car has enough time to turn
  delay(150);
  stopCar();
}

void setCarMoveForward() {
  car.overrideMotorSpeed(40, 40);
}

void setCarMoveForwardFast(){
  car.overrideMotorSpeed(80, 80);
}

void setCarMoveBackwards(){
  car.overrideMotorSpeed(-40, -40);
}

void stopCar(){
  car.overrideMotorSpeed(0, 0);
}
//void measureDistanceBack(){
//distanceBack = back.getDistance();
//Serial.println(distanceBack);
//}

void measureDistanceFront(){
  distanceFront = front.getDistance();
  Serial.print(distanceFront);
}

void displayDistances(){

  Serial.print("\t Front :");
  measureDistanceFront();
  Serial.print("\t Rear : ");
  // measureDistanceBack();
}

// Get sensor distance measurement, in centimetre
void measureDistance(){
  distanceFront = front.getDistance();
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
