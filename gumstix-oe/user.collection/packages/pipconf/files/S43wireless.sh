#! /bin/sh

iwconfig wlan0 mode Ad-hoc
iwconfig wlan0 essid "piphoc"
ifconfig wlan0 192.168.28.1 netmask 255.255.255.0
