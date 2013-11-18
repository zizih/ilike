package app;

import live.hz.ilike.server.model.Command;
import live.hz.ilike.server.model.Operates;
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

    private Operates operates;

    public AppHandler() {
        operates = new Operates();
        Option init, one, two, thr;      //有这些步骤
        Command demo, todo, exit, regist, show, wish; //有这些命令

        //第一个option
        init = new Option();
        init.setId("init");
        init.setNextId("one");
        init.setPrompt("Successfully!");
        demo = new Command();
        demo.setId("demo");
        demo.setPrompt("查看演示");
        show = new Command();
        show.setId("show");
        show.setPrompt("去看看这里都有谁在");
        wish = new Command();
        wish.setId("wish");
        wish.setPrompt("查看对对方使用相同喜欢词语的快乐的人");
        exit = new Command();
        exit.setId("exit");
        exit.setPrompt("退出不需要代价哦～");
        init.addCommands(demo, show, wish, exit);     //第一个开关拥有的命令
        operates.add(init);

        //第二个option  //有复用，按需设计
        one = new Option();
        one.setId("one");
        one.setNextId("two");
        one.setPrompt("你知道怎么做的～");
        demo.setPrompt("再看一次演示，会有不同结果哦");
        todo = new Command();
        todo.setId("todo");
        todo.setPrompt("去表白,格式是 todo: you [ignore|like|hate|unlike|love] other");
        one.addCommands(demo, todo, show, wish,  exit);    //第二个开关拥有的命令
        operates.add(one);

        //第三个option  //有复用，按需设计
        two = new Option();
        two.setId("two");
        two.setNextId("thr");
        two.setPrompt("不紧张，除非喜欢你的人刚好你也喜欢，ta才会知道你在这里说的一切");
        regist = new Command();
        regist.setId("regist");
        regist.setPrompt("留下自己的名字,可以等待被表白哦～ 输入格式是 regist:yourname");
        two.addCommands(demo,show, todo, regist, wish, exit);
        operates.add(two);

        //第三个option  //有复用，按需设计
        thr = new Option();
        thr.setId("thr");
        thr.setNextId("one");   //循环复用
        thr.setPrompt("加油啦，有勇气就不会遗憾");
        thr.addCommands(show, todo, regist, wish, exit);
        operates.add(thr);

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
        channel.write(toByteBuffer(operates.option("init").prompt));
        //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
        channel.register(key.selector(), SelectionKey.OP_READ);
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        Socket socket = channel.socket();
        String addr = socket.getInetAddress() + ":" + socket.getPort();

        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String clientMsg = new String(data).trim();

        //把客戶端返回的 optionId:commandId 解析出來
        String[] clientMsgs = clientMsg.split(":");
        int len = clientMsgs.length;
        if (len < 2) return;
        String optionId = clientMsgs[0];
        String commandId = clientMsgs[1];
        System.out.println("收到一个命令: " + commandId);
        String resultStr;
        if (len > 2) {
            resultStr = operates.invoke(optionId, commandId, addr, clientMsgs[2]) + operates.next(optionId).prompt;
        } else {
            resultStr = operates.invoke(optionId, commandId, addr) + operates.next(optionId).prompt;
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
