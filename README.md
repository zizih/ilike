在hadoop中，DataNode和NameNode之间的控制信息的交流是通过RPC机制完成的，采用的是动态代理和java NIO，本来是用和iserver一样的SocketServer实现，据说NIO还可以单线程并且非阻塞，就改成NIO了。


程序进行到第二天的结果：

    rain@rain-Lenovo:~/coding/workspace/ilike$ python c.py
    connected to  10.50.9.27 8000
    Successfully! you have connected to server!
    Step: init
    [demo]:查看演示
    [todo]:去表白
    >demo
    ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥
    rian 不喜欢 屌丝
    jack 讨厌 屌丝
    rose 无视 程序员
    aaron 无视 作家
    ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥
    你已经知道怎么做了～
    Step: one
    [demo]:再看一次演示，会有不同结果哦
    [todo]:去表白
    >

