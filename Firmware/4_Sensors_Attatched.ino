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
  sensorRead('f');
  sensorRead('b');
  sensorRead('l');
  sensorRead('r');
  delay(100);	//delay between sensor reads for stability.
}


void test(){
  run = 1;
}

void dontMove(){
  motorStop(FRONT);
  motorStop(BACK);
  motorStop(LEFT);
  motorStop(RIGHT);
}

void spinmotor(){
  int motor = atoi(sCmd.next());
  int power = atoi(sCmd.next());
  motorForward(motor, power);
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

void rationalMotors(){
  int front = atoi(sCmd.next());
  int back  = atoi(sCmd.next());
  int left  = atoi(sCmd.next());
  int right = atoi(sCmd.next());
  motorControl(FRONT, front);
  motorControl(BACK, back);
  motorControl(LEFT, left);
  motorControl(RIGHT, right);
}

void pingMethod(){
  Serial.println("pang");
}

void kicker(){
  int type = atoi(sCmd.next());
  if(type == 0){
    motorStop(KICKERS);
  } else if (type == 1){
    motorForward(KICKERS, 100);
  } else {
    motorBackward(KICKERS, 100);
  }
}

void sensorRead(char direction)
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
    
 
    Serial.println(direction);
    Serial.println(distance_cm);
    Serial.println("\n");

    

}

long microsecondsToCentimeters(long microseconds)
{
 return microseconds / 29 / 2;  
}





void completeHalt(){
  motorAllStop();
}

void setup(){
  sCmd.addCommand("f", dontMove); 
  sCmd.addCommand("h", completeHalt); 
  sCmd.addCommand("motor", spinmotor); 
  sCmd.addCommand("r", rationalMotors); 
  sCmd.addCommand("ping", pingMethod); 
  sCmd.addCommand("kick", kicker); 
  SDPsetup();
  helloWorld();
}

