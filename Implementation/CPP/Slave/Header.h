#include "Arduino.h"
#include "SoftwareSerial.h"

#ifndef __INIT__MODULE
#define __INIT__MODULE

#define RX_HM10  11
#define TX_HM10  10

#define RX_HC12  3
#define TX_HC12  2
#define SET      4

SoftwareSerial HM10(RX_HM10, TX_HM10);
SoftwareSerial HC12(RX_HC12, TX_HC12);

// Init HM10 function
void sendCommandHM10(String cmd);
void readDataHM10();
String getDataIBeaconHM10(int t);
void initHM10();

// Init HC12 function
void sendCommandHC12(String cmd);
void readDataHC12(int t);
void initHC12();

#endif
