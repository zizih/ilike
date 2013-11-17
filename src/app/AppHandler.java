package app;

import live.hz.ilike.server.model.Command;
import live.hz.ilike.server.model.Events;
import live.hz.ilike.server.model.Option;
import live.hz.ilike.server.nio.IHandler;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 4:12 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class AppHandler implements IHandler {

    private Events events;

    public AppHandler() {
        events = new Events();
        Option init, one, two, thr;
        Command demo, todo, exit, regist, show;

        //第一个option
        init = new Option();
        init.setId("init");
        init.setNextId("one");
        init.setPrompt("Successfully!");
        demo = new Command();
        demo.setId("demo");
        demo.setPrompt("查看演示");
        todo = new Command();
        todo.setId("todo");
        todo.setPrompt("去表白,格式是 todo: you [like|hate|unlike|love] other");
        exit = new Command();
        exit.setId("exit");
        exit.setPrompt("退出不需要代价哦～");
        init.addCommands(demo, todo, exit);
        events.add(init);

        //第二个option  //有复用，按需设计
        one = new Option();
        one.setId("one");
        one.setNextId("two");
        one.setPrompt("你已经知道怎么做了～");
        demo.setPrompt("再看一次演示，会有不同结果哦");
        one.addCommands(demo, todo, exit);
        events.add(one);

        //第三个option  //有复用，按需设计
        two = new Option();
        two.setId("two");
        two.setNextId("one");
        two.setPrompt("不紧张，还没人知道你来这里了");
        show = new Command();
        show.setId("show");
        show.setPrompt("去看看这里都有谁在");
        regist = new Command();
        regist.setId("regist");
        regist.setPrompt("留下自己的名字,可以等待被表白哦～ 输入格式是 regist:yourname");
        two.addCommands(show, todo, regist, exit);
        events.add(two);

    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        // 获得和客户端连接的通道
        ServerSocketChannel server = (ServerSocketChannel) key
                .channel();
        SocketChannel channel = server.accept();
        // 设置成非阻塞
        channel.configureBlocking(false);

        //给客户端发送信息
        channel.write(toByteBuffer(events.option("init").prompt));
        //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
        channel.register(key.selector(), SelectionKey.OP_READ);
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        Socket socket = channel.socket();
        String addr = socket.getInetAddress() + ":" + socket.getPort();
        System.out.println("hand;ler: " + addr);

        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String clientMsg = new String(data).trim();
        System.out.println("Client Said: " + clientMsg);

        //把客戶端返回的 optionId:commandId 解析出來
        String[] clientMsgs = clientMsg.split(":");
        int len = clientMsgs.length;
        if (len < 2) return;
        String optionId = clientMsgs[0];
        String commandId = clientMsgs[1];
        System.out.println(optionId);
        System.out.println(commandId);
        String resultStr;
        if (len > 2) {
            resultStr = events.invoke(optionId, commandId, addr, clientMsgs[2]) + events.next(optionId).prompt;
        } else {
            resultStr = events.invoke(optionId, commandId, addr) + events.next(optionId).prompt;
        }
        channel.write(toByteBuffer(resultStr));
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {

    }


    private ByteBuffer toByteBuffer(String str) {
        return ByteBuffer.wrap(new String(str).getBytes());
    }
}
