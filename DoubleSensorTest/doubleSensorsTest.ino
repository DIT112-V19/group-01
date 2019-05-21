#include <Smartcar.h>
#include <SoftwareSerial.h>

// Configuration of odometers, gyroscope, motors and diferential control,
// all from SmartCar Library examples.
DirectionlessOdometer leftOdometer(50), rightOdometer(60);
const int LEFT_ODOMETER_PIN = 2;
const int RIGHT_ODOMETER_PIN = 3;

//setting variables to 0 so they'll give correct value
int distanceFront = 0;
int distanceBack = 0;

const int GYROSCOPE_OFFSET = 9;
GY50 gyroscope(GYROSCOPE_OFFSET);

BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);
SmartCar car(control, gyroscope, leftOdometer, rightOdometer);

//variables for the front and back sensors
const int TRIGGER_PIN_FRONT_SENSOR = 6; //D6
const int ECHO_PIN_FRONT_SENSOR = 5; //D5
 const int TRIGGER_PIN_REAR_SENSOR = A4;
 const int ECHO_PIN_REAR_SENSOR = A5;
const unsigned int MAX_DISTANCE = 30;

SR04 back (TRIGGER_PIN_REAR_SENSOR, ECHO_PIN_REAR_SENSOR, MAX_DISTANCE);
SR04 front(TRIGGER_PIN_FRONT_SENSOR, ECHO_PIN_FRONT_SENSOR, MAX_DISTANCE);
void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
displayDistances();
}

void measureDistanceBack(){
distanceBack = back.getDistance();
Serial.println(distanceBack);
}

void measureDistanceFront(){
  distanceFront = front.getDistance();
  Serial.print(distanceFront);
}

void displayDistances(){
  Serial.print("\t Front :");
  measureDistanceFront();
  Serial.print("\t Rear : ");
   measureDistanceBack();
}

// Get sensor distance measurement, in centimetre
void measureDistance(){
  distanceFront = front.getDistance();
}
