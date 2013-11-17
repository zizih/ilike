package app;


import live.hz.ilike.server.nio.Server;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/15/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Start {

    /**
     * 启动服务端测试
     *
     * @throws java.io.IOException
     */
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.init(9600);
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
            server.stop();
        }
    }

}

