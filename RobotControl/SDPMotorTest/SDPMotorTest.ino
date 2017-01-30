#include "SDPArduino.h"
#include <Wire.h>
int i = 0;

void setup(){
  SDPsetup();
  helloWorld();
}

void loop(){
  
  Serial.println("Move Forwards 100%");
  motorForward(3, 100);
  motorBackward(4, 100);
  delay(5000);
  Serial.println("Stopping motors 3 and 4");
  motorStop(3);
  motorStop(4);
  delay(2500);
  
  Serial.println("Move backwards 100%");
  motorBackward(3, 100);
  motorForward(4, 100);
  delay(5000);
  Serial.println("Stopping motors 3 and 4");
  motorStop(3);
  motorStop(4);
  delay(2500);
  
  Serial.println("Move Sideways 100%");
  motorForward(2, 100);
  motorBackward(5, 100);
  delay(5000);
  Serial.println("Stopping motors 2 and 5");
  motorStop(2);
  motorStop(5);
  delay(2500);
  
  Serial.println("Move Other Side 100%");
  motorBackward(2, 100);
  motorForward(5, 100);
  delay(5000);
  Serial.println("Stopping motors 2 and 5");
  motorStop(2);
  motorStop(5);
  delay(2500);
  
  Serial.println("Move Diagonally 100%");
  motorForward(3, 100);
  motorBackward(4, 100);
  motorForward(2, 100);
  motorBackward(5, 100);
  delay(5000);
  motorAllStop();
  delay(2500);

  Serial.println("All Motors Stop");
  motorAllStop();
  delay(2500);
}
