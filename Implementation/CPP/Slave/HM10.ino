#include "Header.h"
void sendCommandHM10(String cmd) {
  HM10.print(cmd);
  HM10.write(0x0D);
  HM10.write(0x0A);
  delay(100);
  if (cmd.indexOf("AT+DISI") == -1) {
    readDataHM10(1000);
  }
}

String readDataHM10(int t) {
  int i = 0;
  delay(t);
  String s = "";
  while (HM10.available() > 0) {
    char c = HM10.read();
    s += c;
  }
  Serial.println("-> " + s + "  LEN: " + String(i));
  return s;
}

String getDataIBeaconHM10(int t) {
  delay(t);
  String s = "";
  long tim = 0;
  while (tim < 5000) {
    while (HM10.available() > 0) {
      char c = HM10.read();
      s += c;
      tim=0;
    }
    if(s.indexOf("OK+DISCE") != - 1){
      break;  
    }
    delay(1);
    tim++;
  }
  //Serial.println("sOut:\n" + String(s));
  s.replace("OK+DISC:00000000:00000000000000000000000000000000:0000000000:", "");
  return s;
}

void initHM10() {
  HM10.begin(9600);
  sendCommandHM10("AT+BAUD0");
  sendCommandHM10("AT+ROLE1");
  sendCommandHM10("AT+ADTY3");
  sendCommandHM10("AT+IBEA1");
  sendCommandHM10("AT+IMME1"); //AT+SCAN?
  sendCommandHM10("AT+SCAN3");
  delay(3000);
  sendCommandHM10("AT+RESET");
  delay(1000);
  Serial.println("----- Init HM10 success ----- ");
}

void flushBuffer() {
  while (HM10.available() > 0) {
    char c = HM10.read();
  }
}
