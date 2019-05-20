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

//variables for the front and back sensors
const int TRIGGER_PIN_FRONT_SENSOR = 6; //D6
const int ECHO_PIN_FRONT_SENSOR = 5; //D5
// const int TRIGGER_PIN_REAR_SENSOR = A4;
// const int ECHO_PIN_REAR_SENSOR = A5;
const unsigned int MAX_DISTANCE = 30;

//SR04 back (TRIGGER_PIN_REAR_SENSOR, ECHO_PIN_REAR_SENSOR, MAX_DISTANCE);
SR04 front(TRIGGER_PIN_FRONT_SENSOR, ECHO_PIN_FRONT_SENSOR, MAX_DISTANCE);

//setting variables to 0 so they'll give correct value
int distanceFront = 0;
int distanceBack = 0;

char switchStatementVarMainLoop;
char switchStatementVarManualControl;

//bluetooth configuration ??
SoftwareSerial BTSerial(0,1);

// Setup code runs once
void setup() {
	pinMode(A4, INPUT_PULLUP);
	Serial.begin(9600);

	//Let's try to run the car without the odometerSetUp
	odometerSetUp();

	// Cruise Control controls the car speed in meters/second
	//using default PID values
	// car.enableCruiseControl();
}

// Loop code runs repeatedly
void loop() {
	switchStatementVarMainLoop = Serial.read();
	switch (switchStatementVarMainLoop)
	{
		case 'f':
			alarmFunction();
		break;

		case 'z':
			manualControlFunction();
		break;
	}
}

// --------------------Code for alarmFunction() is bellow --------------------

void alarmFunction(){
	buttonIsPressed = false;
	while (buttonIsPressed == false) {
		automaticObstacleAvoidance();
	}
}

void automaticObstacleAvoidance(){
	measureDistance();
	setCarMoveForward();
	checkIfStopCarButtonIsPressed();

	// we have to add && buttonIsPressed == false to the if statement so the carManualControl
	// so that the button stops the car even when it's turning around
	
	// && distance > 0 because of Arduino sensor bug
	if(distanceFront < MAX_DISTANCE && distanceFront > 0 ){
		stopCar();
		changeRandomDirection();
		checkIfStopCarButtonIsPressed();
	}
}

void checkIfStopCarButtonIsPressed(){
	int sensorVal = digitalRead(A4);
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

// ---------------- Code for manualControlFunction() is bellow ----------------

void manualControlFunction() {
	exitManualControl = false;
	while (exitManualControl == false ){
		carManualControl();
	}
}

void carManualControl(){
	switchStatementVarManualControl = Serial.read();
	Serial.println(switchStatementVarManualControl);
	switch (switchStatementVarManualControl)
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

// ------------ Code common for alarm and manual control modes ------------

long generateRandomNumber() {
	return random(0, 10);
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

// ------------ Code necessary for initialization of Smartcar ------------
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
