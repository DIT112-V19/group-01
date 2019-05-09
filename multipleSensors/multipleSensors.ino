#include <Smartcar.h>

int echoPin1= 5;
int trigPin1 =6;

int BackSensorTrigPin2 = A4;
int BackSensorEchoPin2 = A5;

long duration, distance,BackSensor,FrontSensor,distance2;



void setup() {
  // put your setup code here, to run once:
Serial.begin(9600);
pinMode(trigPin1,OUTPUT);
pinMode(echoPin1,INPUT);
pinMode(BackSensorTrigPin2,OUTPUT);
pinMode(BackSensorEchoPin2,INPUT);


}

void loop() {
  SonarSensor(trigPin1,echoPin1);

  FrontSensor = distance;

  SonarSensor(BackSensorTrigPin2,BackSensorEchoPin2);

  BackSensor = distance;

 Serial.print(" Back sensor : ");
  Serial.print(BackSensor);
  delay(1000);
 

 Serial.print(" Front sensor :  ");
  Serial.print(FrontSensor);
  delay(1000);
   
 

}

void SonarSensor(int trigPin,int echoPin){
digitalWrite(trigPin,LOW);
delayMicroseconds(2);
digitalWrite(trigPin,HIGH);
delayMicroseconds(10);
digitalWrite(trigPin,LOW);
duration = pulseIn(echoPin, HIGH);
distance = (duration/2) / 29.1;
}
