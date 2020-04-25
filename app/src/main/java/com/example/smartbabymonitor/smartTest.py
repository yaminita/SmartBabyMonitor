import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import grovepi
import subprocess
import picamera
from grove_i2c_barometic_sensor_BMP180 import BMP085
from grove_rgb_lcd import *
from time import sleep
from math import isnan
import json
import time
import csv
import sys
#csvfile = "/home/pi/temp.csv"
#from android import Android
#import the below libraries to send an email
import smtplib
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.MIMEBase import MIMEBase
from email import encoders

temp_humidity_sensor    = 7


#API KEY - FIREBASE

cred = credentials.Certificate('/home/pi/Desktop/smartBabyMonitorProject/cred.json')
#Initializalize  the app with a service account, granting admin privilegeges

firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://smart-baby-monitor-d19e1.firebaseio.com/'
    })

#droid = Android()

sound_sensor = 0                # Connect the Grove Sound Sensor to analog port A0                 
#dht_sensor_port = 7              #Temperature & Humidity sensor
#dht_sensor_type = 0

blue=0
white=1
therm_version = blue 

temp = db.reference('temperature')
hum = db.reference('humidity')
son = db.reference('sound')

#variables for the minmum and max temperature of the baby room and counter 
min = 24
max = 25
warningTemperature = 0

while True: 
    
    [temperature,humidity] = grovepi.dht(temp_humidity_sensor,therm_version)
    sound = grovepi.analogRead(sound_sensor)

    print("Let's start with getting python to read from the sensor..")
    print("humidity is", humidity)
    print("temperature is", temperature)
    print("sound is", sound)
    
    #variable data to store the reading
    #timeC = time.strftime("%I")+':' +time.strftime("%M")+':'+time.strftime("%S")
    #data = [temperature, timeC]
    #with open(csvfile, "a") as output:
     #   writer = csv.writer(output, delimiter=",", lineterminator = '\n')
      #  writer.writerow(data)    
    
       
    if temperature > max: 
        print "WARNING!"
        print "Baby's room temperature too hot."
        print "........................."
        warningTemperature = warningTemperature + 1 
       
    if temperature < min:
        print "WARNING!"
        print "Baby's room temperature too cold."
        print "........................."
        warningTemperature = warningTemperature + 1
        
        #Wait for 2 loops before notifying the parent about the abnormal temperature
        if warningTemperature == 2:
            #####
            # EMAIL SECTION
            fromaddr = "yaminita7@gmail.com"
            toaddr = "yaminakellysantillan@gmail.com"
            #creating an instance for MIME multipart function/ standart format for email
            msg = MIMEMultipart()
            msg['From'] = fromaddr
            msg['To'] = toaddr
            msg['Subject'] = """TEMPERATURE ALERT. {}""".format(temperature)
            body = "ALERT!! ABNORMAL TEMPERATURE IN YOUR BABY'S ROOM "+str(temperature)
            msg.attach(MIMEText(body, 'plain'))
                
            warningTemperature = 0

            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login(fromaddr, "0916016223")
            text = msg.as_string()
            server.sendmail(fromaddr, toaddr, text)
            server.quit()
            print("CHECK YOUR MAIL TO SEE THE ABNORMAL TEMPERATERAURE IN YOUR BABY'S ROOM " )
    
    

    #####
    #NOw we are uploading the readings to Firebase
            
    print("Now let's upload that to Firebase")
    temp.set({
        "temperature":temperature
        })
    hum.set({
        "humidity": humidity
        })
    
    son.set({
        "sound": sound
        })
    
    print("Give it a moment to upload...")
    sleep(10)
    
'''
    #Script to read data from the CSV & display it in a graph
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
import matplotlib.animation as animation
from datetime import datetime

fig = plt.figure()
rect = fig.patch
rect.set_facecolor('#0079E7')
def animate(i):
    ftemp = 'temp.csv'
    fh = open(ftemp)
    temp = list()
    timeC = list()
    for line in fh:
        pieces = line.split(',')
        degree = pieces[0]
        timeB=  pieces[1]
        timeA= timeB[:8]
        #print timeA
        time_string = datetime.strptime(timeA,'%H:%M:%S')
        #print time_strin
        try:
            temp.append(float(degree))
            timeC.append(time_string)
        except (IOError,TypeError) as e:
            return [-1,-1,-1]
        
                
        ax1 = fig.add_subplot(1,1,1,axisbg='white')
        ax1.xaxis.set_major_formatter(mdates.DateFormatter('%H:%M:%S'))
        ax1.clear()
        ax1.plot(timeC,temp, 'c', linewidth = 3.3)
        plt.title('Temperature')
        plt.xlabel('Time')
ani = animation.FuncAnimation(fig, animate, interval = 6000)
plt.show()

droid.webViewShow('https://smart-baby-monitor-d19e1.firebaseio.com/')
'''

    
    
