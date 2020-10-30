#include "Header.h"

String MODULE = "C";
String s1;
String s2;
void setup() {
  // put your setup code here, to run once:
  delay(1000);
  Serial.begin(9600);
  initHM10(false);
  delay(1000);
  initHC12(false);
  delay(1000);
  HC12.println("Hello M" + MODULE+ " !!!");
}
String sHC12 = "";
void loop() {
  HC12.listen();
  sHC12 = readAddressHC12();
  if (sHC12.indexOf("MA") != -1) {
    HM10.listen();
    sendCommandHM10("AT+DISI?");
    String s = getDataIBeaconHM10(200);
    s1 = getRSSIiBeacon("F6FE8B91855C", s);
    s2 = getRSSIiBeacon("C5F0C9E18BA0", s);
    flushBuffer();
    Serial.println("");
    sHC12 = "";
  }
  else if (sHC12.indexOf("MB") != -1) {
    sHC12 = "";
  }
  else if (sHC12.indexOf("MC") != -1) {
    sendDataToRF(s1, s2);
    sHC12 = "";
  }
  else if (sHC12.indexOf("MD") != -1) {
    //sendDataToRF(s1, s2);
    sHC12 = "";
  }
}

void sendDataToRF(String avrRSSI_1, String avrRSSI_2) {
  HC12.print(MODULE + "1:" + String(avrRSSI_1) + ":" + String(avrRSSI_2) + ":" + MODULE + "2");
  Serial.println(MODULE + "1:" + String(avrRSSI_1) + ":" + String(avrRSSI_2) + ":" + MODULE + "2");
}

void sendDataToServer(String sData) {
  String a1 = "F6FE8B91855C";
  String a2 = "C5F0C9E18BA0";

  int i1 = sData.indexOf(a1);
  int i2 = sData.indexOf(a2);

  String sRSSI1 = "";
  String sRSSI2 = "";
  String sDataOut = "A:";
  if (i1 == -1 || (i1 + 15 > sData.length())) {
    sDataOut += "NS1";
  }
  else {
    sRSSI1 = sData.substring(i1 + 13, i1 + 17);
    sDataOut += sRSSI1;
  }
  if (i2 == -1 || (i2 + 15 > sData.length())) {
    sDataOut += ":NS2";
  }
  else {
    sRSSI2 = sData.substring(i2 + 13, i2 + 17);
    sDataOut += ":" + sRSSI2;
  }
  HC12.print(sDataOut);
  Serial.println(sData);
  Serial.println("Index1: " + String(i1) + "  sRSSI1: " + sRSSI1);
  Serial.println("Index2: " + String(i2) + "  sRSSI2: " + sRSSI2);
  Serial.println("sDataOut: " + sDataOut);
}
