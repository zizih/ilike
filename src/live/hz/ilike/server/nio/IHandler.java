package live.hz.ilike.server.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 4:10 PM
 * 处理器接口
 */
public interface IHandler {
    /**
     * 处理SelectionKey=OP_ACCEPT事件
     *
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key) throws IOException;

    /**
     * 处理SelectionKey=OP_READ事件
     *
     * @param key
     * @throws IOException
     */
    void handleRead(SelectionKey key) throws IOException;

    /**
     * 处理SelectionKey=OP_WRITE事件
     *
     * @param key
     * @throws IOException
     */
    void handleWrite(SelectionKey key) throws IOException;
}
