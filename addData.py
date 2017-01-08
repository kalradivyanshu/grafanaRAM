from random import random as rand
from influxdb import InfluxDBClient
from time import sleep

HOST = "192.168.99.100"
PORT = "8086"
DATABASE = "wadus"
USER = "root"
PASSWORD = "root"

client = InfluxDBClient(HOST, PORT, USER, PASSWORD, DATABASE)
for i in range(60):
	randomNum = int(rand()*200)
	print(randomNum)
	json_body = [
		{
			"measurement" : "scores",
			"fields": {
				"value": randomNum
			}
		}
	]
	client.write_points(json_body)
	sleep(1)

