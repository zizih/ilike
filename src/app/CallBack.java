package app;

import live.hz.ilike.model.Client;
import live.hz.ilike.model.Event;
import live.hz.ilike.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 5:56 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class CallBack {

    enum From {rian, jack, rose, aaron}

    enum To {程序员, 文艺青年, 屌丝, 作家}

    enum Action {喜欢, 无视, 不喜欢, 讨厌}

    private Log log;

    public CallBack() {
        this.log = Log.ini();
    }

    //默认callbak方法的参数都是第一个为ip:port 第二个之后为客户端传入的第三个参数
    public String demo(String addr) {
        System.out.println("addr: " + addr);
        StringBuffer sb = new StringBuffer("♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥\n");

        //默认都一样长
        int len = From.values().length;
        int i = 0;
        for (From from : From.values()) {
            int[] indexs = {(int) (Math.random() * len), (int) (Math.random() * len)};
            sb.append(from + " " + Action.values()[indexs[0]] + " " + To.values()[indexs[1]] + "\n");
            i++;
        }
        return sb.append("♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥\n").toString();
    }

    public String todo(String addr, String todo) {
        String[] todos = todo.trim().split(" ");
        Event.Action action = Event.Action.valueOf(todos[1]);
        if (todos.length != 3 || !(action instanceof Event.Action)) {
            return "表白句子不对～\n";
        }
        String[] addrs = addr.split(":");
        if (addrs.length != 2) return "服务器错误\n";

        //构造一个event对象，即发生了一个事件的数据传输对象
        Client from = new Client();
        from.setNick(todos[0]);
        from.setIp(addrs[0].substring(1));
        from.setPort(addrs[1]);
        Client to = new Client(todos[2]);
        Event event = new Event(action, from, to);
        log.ilike(event.toJson());
        return todo;
    }

    public String show() {
        return "show..";
    }

    public String regist() {
        return "register";
    }

    public String exit() {
        return "you haved exited";
    }

}
