#include "SerialCommand.h"
#include "SDPArduino.h"
#include <Wire.h>
#include <Arduino.h>

#define FRONT 2
#define RIGHT 3
#define BACK 4
#define LEFT 5
#define KICKERS 1


int run = 0;

SerialCommand sCmd;



void loop(){
  respond();
  delay(10);	//delay between sensor reads for stability.
}


//SENSOR METHODS 
//#################################################################################################################################################################

//Returns the distance sensed in the given direction (in centimeters).
long sensorRead()
{
  
    long duration,distance_cm;
    
      // The sensor is triggered by a HIGH pulse of 10 or more microseconds.
      // Give a short LOW pulse beforehand to ensure a clean HIGH pulse:
      pinMode(3,OUTPUT);
      digitalWrite(3, LOW);
      delayMicroseconds(2);
      digitalWrite(3,HIGH);
      delayMicroseconds(10);
      digitalWrite(3,LOW);
    
    
      pinMode(A3,INPUT);
      duration=pulseIn(A3,HIGH);
     
     
  
     distance_cm = microsecondsToCentimeters(duration);
    
 
    
 
    return distance_cm;  

}

//Moves away from obstacles and informs the computer.
void respond()
{
  long L=sensorRead();

  if(L<10)
  {
      Serial.println("Obstacle_In_Left");
  }

}


long microsecondsToCentimeters(long microseconds)
{
 return microseconds / 29 / 2;  
}

void setup()
{
 SDPsetup();
  helloWorld();
}

//###############################################################################################################################################################


