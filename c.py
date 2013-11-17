#first time to use python in a app
#auto: hezi

import socket
import sys
s = socket.socket()

#host = raw_input('input server ip: ')
#port = input('input server port: ')
#host = socket.gethostname()
host = '10.50.9.27'
port = 9600

s.connect((host,port))
print 'connected to ',host,port


# function to get option id
def option(str):
    lines = str.split('\n')
    for line in lines:
        if line.startswith('<<<<Step: '):
            index = line.find('>>>>')
            return line[10:index]


while True:
    result = s.recv(1024)
    print result

    data = raw_input('>')
    if data.find('exit') != -1:
        sys.exit(0)
    s.send(option(result) + ':' + data)
    #s.send(data)

s.close
