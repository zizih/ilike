#first time to use python in a app
#auto: hezi

import socket
s = socket.socket()

#host = raw_input('input server ip: ')
#port = input('input server port: ')
#host = socket.gethostname()
host = '10.50.9.27'
port = 8000

s.connect((host,port))
print 'connected to ',host,port


# function to get option id
def option(str):
    lines = str.split('\n')
    for line in lines:
        if line.startswith('Step:'):
            return line[6:]


while True:
    result = s.recv(1024)
    print result

    data = raw_input('>')
    s.send(option(result) + ':' + data)
    #s.send(data)

s.close
