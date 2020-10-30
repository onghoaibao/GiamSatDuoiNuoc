#include "Header.h"

void sendCommandHC12(String cmd) {
  pinMode(RX_HC12, INPUT);
  pinMode(TX_HC12, OUTPUT);
  HC12.print(cmd);
  delay(100);
  readDataHC12(200);
}

String readDataHC12(int t) {
  delay(t);
  String s = "";
  while (HC12.available() > 0) {
    char c = HC12.read();
    Serial.print(c);
  }
  return s;
}

String readAddressHC12() {
  String s = "";
  while (HC12.available() > 0) {
    char c = HC12.read();
    s += c;
    Serial.print(c);
  }
  return s;
}

void initHC12(bool a) {
  HC12.begin(9600);
  pinMode(SET, OUTPUT);
  digitalWrite(SET, LOW);
  delay(100);
  if (a) {
    sendCommandHC12("AT");
    sendCommandHC12("AT+A222");
    sendCommandHC12("AT+C111");
  }
  delay(100);
  digitalWrite(SET, HIGH);
  delay(100);
  Serial.println("----- Init HC12 success ----- ");
}
