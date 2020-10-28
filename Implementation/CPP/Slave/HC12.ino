#include "Header.h"

void sendCommandHC12(String cmd) {
  HC12.print(cmd);
//  HC12.write(0x0D);
//  HC12.write(0x0A);
  delay(100);
  readDataHC12();
}

void readDataHC12() {
  delay(100);
  //Serial.println("Len: " + String(HC12.available()));
  while (HC12.available() > 0) {
    char c = HC12.read();
    Serial.print(c);
  }
}

void initHC12(){
  HC12.begin(9600);
  pinMode(SET, OUTPUT);
  digitalWrite(SET, LOW);
  delay(100);
  sendCommandHC12("AT");
  sendCommandHC12("AT+A222");
  sendCommandHC12("AT+C111");
  digitalWrite(SET, HIGH);
  Serial.println("----- Init HC12 success ----- ");
}
