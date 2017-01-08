from django.shortcuts import render
from django.http import HttpResponse
from random import random as rand
from influxdb import InfluxDBClient

HOST = "192.168.99.100"
PORT = "8086"
DATABASE = "androidRAM"
USER = "root"
PASSWORD = "root"

client = InfluxDBClient(HOST, PORT, USER, PASSWORD, DATABASE)


def index(request):
	if request.method == 'GET':
		data = request.GET.get('data', '')
		if data == '':
			return HttpResponse("No request or Null")
		try:
			data = int(data)
			json_body = [
				{
					"measurement" : "RAM", #table name (kind of)
					"fields": {
						"RAM": data #Feild name
					}
				}
			]
			client.write_points(json_body)
			return HttpResponse("Data written: "+str(data))
		except:
			return HttpResponse("Data not written, not int: "+str(data))
	return HttpResponse("No request or Null")