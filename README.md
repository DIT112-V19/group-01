# group-01

## README

## Contents of the readme
1. Project description
2. Installation
3. User manual

### 1. Project description of Speedy Carzales Arduino Alarm Car
Speedy Carzales is a smartcar, that plays a tone on a certain time, while it drives away from the user and tries to avoid obstacles as well as being turned off.
Another functionality is the ability to manually operate it.
The project consists of the Android App and the Arduino Code.
The App is optimized for Android 9.0. 

The hardware, used in this project is:
* Arduino Mega
* 4 Motors
* Smartcar Shield
* 2 Ultrasonic Sensors
* Active Buzzor
* Custom made PCB
* a button (Pullup Configuration)
* a Gyroscope (no code for its implementation used)
* 2 Odometers
* On/Off Switch
* 8 Batteries and a Batterie 
* 1 Battery Snap Connector
* 1 Battery Clip (8 * AA Battery)
* 1 Resistor
* a variety of jumpers, and cables fitting to the hardware
* 4 tires
* acrylic board as platform for the car
* Bluetooth Module
* 1 Mini-Breadboard

Tools needed to assemble the car
* Hot Glue Gun
* Screwdriver
* Soldering Iron
* surge protector*

*Please keep in mind to connect the surge protector with your computer when you plug your arduino in your USB Port. We take no resonsiblity for damage to your hardware, body or any other involved subjects.

## 2. Installation
1. Download arduino IDE, make sure that the arduino board is connected through a cable
2. Download android studio IDE
3. Import the code onto your computer
4. Upload the code into the arduino using the arduino IDE
5. Upload the android application on an android device using the android studio IDE

## 3. User manual 
1. Turn on the car with the On/Off switch located on the side 
2. Boot the phone application
3. Press connect on the welcome page
4. Use the on/off button to turn on your bluetooth 
5. Press discover to find bluetooth devices
6. Choose your device on the list by tapping on it
7. Press "start connection"
8. After getting the message "successfully connected" popped up, press "continue"
9. Choose between setting the alarm or play with the car 
10. "play" : Control the car by pressing the buttons (if "reconnect bluetooth" appears, please reconnect)
11. "Alarm" : Set the alarm by choosing the time on the clock provided. Then click "set alarm" 
12. "Alarm" : Press activate to immediately set off the alarm
13. To return at any point in the app, use the return button in the upper left corner
14. ##### Enjoy ! 

## 4. The PCB
The PCB was designed in EAGLE CAD. It is used to connect the Ultrasonic Sensors to the Arduino, as well as the button. The cutouts are fitted so it will be able to sit on the smartcarshield, without blocking the Gyroscope or the connections. 
