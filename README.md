# GrafanaRAM: Display RAM usage of android on grafana using django, influxDB

Requirements(for python3): django, influxdb

Basic setup for Mac OSX: http://bit.ly/2iRa9QR

Then create a database using ```CREATE DATABASE "androidRAM"```
After this get the IP address for the influxDB running on Docker using:

```$ docker-machine ip dev ```

and change ```HOST = "192.168.99.100"``` value in grafanaRAM/grafanaBackend/SaveData/views.py and ```String url = "http://192.168.1.5:8000/polls/?data="+data;``` in 

grafanaRAM/Android/GrafanaTest2/app/src/main/java/kalra/divyanshu/grafanatest/UpdateRAMinfo.java

To add metric to graph: https://youtu.be/sKNZMtoSHN4

In the options that come after clicking the title, change time range settings, set override Time to 1m. To make x-axis finer.

#Output

An android game was started and this GIF shows the hike in RAM and the fall when it was terminated.

![alt tag](https://raw.githubusercontent.com/kalradivyanshu/grafanaRAM/master/output.gif)
