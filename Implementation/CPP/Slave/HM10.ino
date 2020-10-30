#include "Header.h"
void sendCommandHM10(String cmd) {
  pinMode(RX_HM10, INPUT);
  pinMode(TX_HM10, OUTPUT);
  HM10.print(cmd);
  HM10.write(0x0D);
  HM10.write(0x0A);
  delay(100);
  if (cmd.indexOf("AT+DISI") == -1) {
    readDataHM10(200);
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
  while (tim < 3000) {
    while (HM10.available() > 0) {
      char c = HM10.read();
      s += c;
      tim = 0;
    }
    if (s.indexOf("OK+DISCE") != - 1) {
      break;
    }
    delay(1);
    tim++;
  }
  s.replace("OK+DISC:00000000:00000000000000000000000000000000:0000000000:", "");
  Serial.println("sOut: \n" + String(s));
  return s;
}

String getRSSIiBeacon(String mac, String sData) {
  int i = sData.indexOf(mac);
  String sRSSI = "";
  if (i != -1 && i + 15 < sData.length()) {
    sRSSI = sData.substring(i + 13, i + 17);
  }
  else {
    sRSSI = "0";
  }
  Serial.println("Mac: " + mac + "  RSSI: " + sRSSI);
  return sRSSI;
}

void initHM10(bool a) {
  HM10.begin(9600);
  if (a) {
    sendCommandHM10("AT+BAUD0");
    sendCommandHM10("AT+ROLE1");
    sendCommandHM10("AT+ADTY3");
    sendCommandHM10("AT+IBEA1");
    sendCommandHM10("AT+IMME1"); //AT+SCAN?
    sendCommandHM10("AT+SCAN3");
    delay(3000);
  }
  sendCommandHM10("AT+RESET");
  delay(1000);
  Serial.println("----- Init HM10 success ----- ");
}

void flushBuffer() {
  while (HM10.available() > 0) {
    char c = HM10.read();
  }
}
