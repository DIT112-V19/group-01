#include <Smartcar.h>

const int TRIGGER_PIN = 6; //D6
const int ECHO_PIN = 5; //D5

const unsigned int MAX_DISTANCE = 30;
int distance;
int period = 500;
unsigned long time_now = 0;
int speedDrive = 100;
int speedTurn = 38;
BrushedMotor leftMotor(8, 10, 9);
BrushedMotor rightMotor(12, 13, 11);
DifferentialControl control(leftMotor, rightMotor);

SR04 front(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

SimpleCar car(control);


void setup() {
  
  Serial.begin(9600);
  car.setSpeed(speedDrive);
  
}

void loop() {
	distance = front.getDistance();
	if(distance < MAX_DISTANCE && distance > 0 ){
		time_now = millis();
		while (millis() < time_now + period) {
		leftMotor.setSpeed(speedTurn);
		rightMotor.setSpeed(-speedTurn);
		}
	}
	speedTurn = speedTurn * (-1);
	car.setSpeed(speedDrive);
}
