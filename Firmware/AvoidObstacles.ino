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
  sCmd.readSerial();
  avoid_obstacles();
  delay(10);	//delay between sensor reads for stability.
}

void test(){
  run = 1;
}

void motorControl(int motor, int power){
  if(power == 0){
    motorStop(motor);
  } else if(power > 0){
    motorBackward(motor, power);
  } else {
    motorForward(motor, -power);
  }
}

void completeHalt(){
  motorAllStop();
}

void setup(){
  SDPsetup();
  helloWorld();
}





//SENSOR METHODS 
//#################################################################################################################################################################

//Returns the distance sensed in the given direction (in centimeters).
long sensorRead(char direction)
{
  
    long duration,distance_cm;
    
    switch(direction)
    {
     case 'f':
     
     
      // The sensor is triggered by a HIGH pulse of 10 or more microseconds.
      // Give a short LOW pulse beforehand to ensure a clean HIGH pulse:
      pinMode(9,OUTPUT);
      digitalWrite(9, LOW);
      delayMicroseconds(2);
      digitalWrite(9,HIGH);
      delayMicroseconds(10);
      digitalWrite(9,LOW);
    
    
      pinMode(A1,INPUT);
      duration=pulseIn(A1,HIGH);
    
      break;
     
     case 'b':
      // The sensor is triggered by a HIGH pulse of 10 or more microseconds.
      // Give a short LOW pulse beforehand to ensure a clean HIGH pulse:
      pinMode(5,OUTPUT);
      digitalWrite(5, LOW);
      delayMicroseconds(2);
      digitalWrite(5,HIGH);
      delayMicroseconds(10);
      digitalWrite(5,LOW);
    
    
      pinMode(A2,INPUT);
      duration=pulseIn(A2,HIGH);
     break;
     

     case 'l':
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
     
     
     break;
   
     case 'r':
      // The sensor is triggered by a HIGH pulse of 10 or more microseconds.
      // Give a short LOW pulse beforehand to ensure a clean HIGH pulse:
      pinMode(6,OUTPUT);
      digitalWrite(6, LOW);
      delayMicroseconds(2);
      digitalWrite(6,HIGH);
      delayMicroseconds(10);
      digitalWrite(6,LOW);
    
    
      pinMode(A0,INPUT);
      duration=pulseIn(A0,HIGH);
     break;
    }
   
  
  
     distance_cm = microsecondsToCentimeters(duration);
    
 
    
 
    return distance_cm;  

}

//Moves away from obstacles and informs the computer.
void avoid_obstacles()
{
  long F=sensorRead('f');
  long B=sensorRead('b');
  long L=sensorRead('l');
  long R=sensorRead('r');

if(F<10||B<10)
{
  if(F<10)
  {
    //Move backwards.
    motorControl(FRONT,-50);
    motorControl(BACK,50);
    Serial.println("Obstacle_In_Front");
  }
  else if(B<10)
  {
    //Move forward
    motorControl(FRONT,50);
    motorControl(BACK,-50);
    Serial.println("Obstacle_In_Back");
  }
}
else
{
   motorControl(FRONT,0);
   motorControl(BACK,0);
}

if(L<10||R<10)
{
  if(L<10)
  {
    //Move right
    motorControl(RIGHT,50);
    motorControl(LEFT,-50);
    Serial.println("Obstacle_In_Left");

  }

  else if(R<10)
  {
    //Move left
    motorControl(RIGHT,-50);
    motorControl(LEFT,50);
    Serial.println("Obstacle_In_Right");
  }

}
else
{
   motorControl(RIGHT,0);
   motorControl(LEFT,0);

}


long microsecondsToCentimeters(long microseconds)
{
 return microseconds / 29 / 2;  
}


//###############################################################################################################################################################


