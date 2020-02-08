# app3
My Server Java Application - Rain Notification.

API used:
  developer.accuweather - provides weather data;
  pushover.net - provides a service for sending PUSH messages to your device

Application deployed to Amazon Web Service Lambda
Once a day, it automatically starts, requests data from the weather server for my city, and if it rains, it sends me a message in the phone.

