本来是用和iserver一样的SocketServer实现，据说NIO还可以单线程并且非阻塞，就改成NIO了。
NIO的一些知识，一种非阻塞IO：
    1. 一个专门的线程处理所有IO，这个线程维护一个管理通道的对象Selecotor（能检测一个或多个通道上的事件，通道需要和管理器绑定，通道需要注册感兴趣的事件）
    2. 处理线程采用轮询的方式监听selector上是否有事件到达，有的话就触发相应事件，即“事件驱动机制”
    3. （这点不太明白）？？？线程之间通过 wait,notify 等方式通讯


程序进行到第四天的时候，录了视频在[ilike.ogv](http://zizih.github.io/stayreal/#server)

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