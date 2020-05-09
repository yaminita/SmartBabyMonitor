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
#import the below libraries to send an email
import smtplib
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.MIMEBase import MIMEBase
from email import encoders



#API KEY - FIREBASE
cred = credentials.Certificate('/home/pi/Desktop/smartBabyMonitorProject/cred.json')
#Initializalize  the app with a service account, granting admin privilegeges

firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://smart-baby-monitor-d19e1.firebaseio.com/'
    })


# sensors used in this project
sound_sensor = 0                # Connect the Grove Sound Sensor to analog port A0
temp_humidity_sensor    = 7     # Temperature & Humidity sensor


blue=0
white=1
therm_version = blue 

temp = db.reference('temperature')
hum = db.reference('humidity')
son = db.reference('sound')

#variables for the minmum and max temperature of the baby room, noise allows and counters before emailing the parent 
min = 24
max = 25
warning = 0
noiseallow = 260 # 260db for demostration but the noise should be considered 400db for a baby cry
warningcry = 0

while True: 
    
    [temperature,humidity] = grovepi.dht(temp_humidity_sensor,therm_version)
    sound = grovepi.analogRead(sound_sensor)

    print("Let's start with getting python to read from the sensor..")
    print("humidity is", humidity)
    print("temperature is", temperature)
    print("sound is", sound)

    # Condition which control the temperature min & max of the baby room   
    if temperature > max: 
        print "WARNING!"
        print "Baby's room temperature too hot."
        print "........................."
        warning = warning + 1 
       
    if temperature < min:
        print "WARNING!"
        print "Baby's room temperature too cold."
        print "........................."
        warning = warning + 1
        
        #Wait for 2 loops (abnormal readings) before notifying the parent about the abnormal temperature
        if warning == 2:
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
                
            warning = 0

            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login(fromaddr, "0916016223")
            text = msg.as_string()
            server.sendmail(fromaddr, toaddr, text)
            server.quit()
            print("CHECK YOUR MAIL TO SEE THE ABNORMAL TEMPERATERAURE IN YOUR BABY'S ROOM " )
        
    #Condition which control if the baby is crying         
    if sound > noiseallow: 
        print "WARNING!"
        print "Baby's is craying."
        print "........................."
        warningcry = warningcry + 1
        
        #Wait for 2 loops before notifying the parent about the abnormal sound
        if warningcry == 2:
            #####
            # EMAIL SECTION
            fromaddr = "yaminita7@gmail.com"
            toaddr = "yaminakellysantillan@gmail.com"
            #creating an instance for MIME multipart function/ standart format for email
            msg = MIMEMultipart()
            msg['From'] = fromaddr
            msg['To'] = toaddr
            msg['Subject'] = """BABY CRYING ALERT. {}""".format(sound)
            body = "ALERT!! ABNORMAL NOIESES IN YOUR BABY'S ROOM "+str(sound) 
            msg.attach(MIMEText(body, 'plain'))
                
            warningcry = 0

            server = smtplib.SMTP('smtp.gmail.com', 587)
            server.starttls()
            server.login(fromaddr, "0916016223")
            text = msg.as_string()
            server.sendmail(fromaddr, toaddr, text)
            server.quit()
            print("CHECK YOUR MAIL TO SEE THE ABNORMAL NOISES IN YOUR BABY'S ROOM " )
    
    

    #####
    #NOw we are uploading the readings to Firebase
            
    print("Now let's upload that to Firebase")
    temp.set({
        "Temperature": temperature
        })
    hum.set({
        "Humidity": humidity
        })
    
    son.set({
        "Sound": sound
        })
    
    print("Give it a moment to upload...")
    sleep(10)
 