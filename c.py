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

while True:
    result = s.recv(1024)
    print result

    data = raw_input('>')
    s.send(result.split('\n')[1] + ':' + data)
    #s.send(data)

s.close
