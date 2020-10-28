#include "Header.h"

void setup() {
  // put your setup code here, to run once:
  delay(2000);
  Serial.begin(9600);
  initHC12();
  initHM10();
  delay(1000);
  
}
String *arrS;
void loop() {
  // put your main code here, to run repeatedly:
  //readDataHM10(1000);
//  Serial.println("arrS[0]= " + arrS[0]);
//  Serial.println("arrS[1]= " + arrS[1]);
//  Serial.println("arrS[2]= " + arrS[2]);
    sendCommandHM10("AT+DISI?");
    String s = getDataIBeaconHM10(1000);
    sendDataToServer(s);
//  String s = readDataHM10(5000);
//  Serial.println(s);
//  sendDataToServer(s);
    Serial.println();
    delay(2000);
}

void sendDataToServer(String sData){
  String a1 = "F6FE8B91855C";
  String a2 = "C5F0C9E18BA0";

  int i1 = sData.indexOf(a1);
  int i2 = sData.indexOf(a2);
  
  String sRSSI1 = "";
  String sRSSI2 = "";
  String sDataOut = "A:";
  if(i1 == -1 || (i1+15 > sData.length())){
    sDataOut += "NS1";
  }
  else{
    sRSSI1 = sData.substring(i1 + 13, i1 + 17);
    sDataOut += sRSSI1;
  }

  if(i2 == -1 || (i2+15 > sData.length())){
    sDataOut += ":NS2";
  }
  else{
    sRSSI2 = sData.substring(i2 + 13, i2 + 17); 
    sDataOut += ":" + sRSSI2;
  }
  //HC12.print(sDataOut);
  Serial.println(sData);
  Serial.println("Index1: " + String(i1) + "  sRSSI1: " + sRSSI1);
  Serial.println("Index2: " + String(i2) + "  sRSSI2: " + sRSSI2);
  Serial.println("sDataOut: " + sDataOut);
}
