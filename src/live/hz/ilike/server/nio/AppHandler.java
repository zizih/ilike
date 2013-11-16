package live.hz.ilike.server.nio;

import live.hz.ilike.server.model.Command;
import live.hz.ilike.server.model.Events;
import live.hz.ilike.server.model.Option;

import java.io.IOException;
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
        //第一个option
        Option option = new Option();
        option.setId("init");
        option.setPrompt("Successfully! you have connected to server!");
        Command comm = new Command();
        comm.setId("demo");
        comm.setPrompt("查看演示");
        Command comm2 = new Command();
        comm2.setId("todo");
        comm2.setPrompt("去表白");
        option.addCommands(comm, comm2);
        events.add(option);

        //第二个option  //有复用，按需设计
        option = new Option();
        option.setId("one");
        option.setPrompt("你已经知道怎么做了～");
        comm.setPrompt("再看一次演示，会有不同结果哦");
        option.addCommands(comm, comm2);
        events.add(option);
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
        // 创建读取的缓冲区

        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.read(buffer);
        byte[] data = buffer.array();
        String clientMsg = new String(data).trim();
        System.out.println("Client Said: " + clientMsg);

        //把客戶端返回的 optionId:commandId 解析出來
        int split = clientMsg.indexOf(":");
        if (split == -1) return;
        String optionId = clientMsg.substring(0, split);
        String commandId = clientMsg.substring(split + 1, clientMsg.length());

        ByteBuffer outBuffer = ByteBuffer.wrap(clientMsg.getBytes());
        channel.write(toByteBuffer(events.invoke(optionId, commandId)
                + events.option("one").prompt
        ));// 将消息回送给客户端
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {

    }


    private ByteBuffer toByteBuffer(String str) {
        return ByteBuffer.wrap(new String(str).getBytes());
    }
}
