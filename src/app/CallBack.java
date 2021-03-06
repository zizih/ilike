package app;

import live.hz.ilike.model.Client;
import live.hz.ilike.model.Event;
import live.hz.ilike.server.nio.BaseCallback;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 5:56 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class CallBack extends BaseCallback {

    enum From {rain, jack, rose, aaron}

    enum To {dios, lucy, ruby, anson}

    enum Action {like, ignore, hate, love}

    public CallBack() {
        super();
    }

    //默认callbak方法的参数都是第一个为ip:port 第二个之后为客户端传入的第三个参数
    public String demo(String addr) {
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
        //对客户端返回的约定格式数据进行解析
        String[] todos = todo.trim().split(" ");
        String fromNick = todos[0];
        String act = todos[1];
        String toNick = todos[2];

        //先检查action行为是否冲突
        Event.Action action = Event.Action.valueOf(act);
        String result = verify(fromNick, action, toNick);
        if (!result.equals("OK")) {
            return result + "\n";
        }

        //封装对象
        if (todos.length != 3 || !(action instanceof Event.Action)) {
            return "⊙ ω ⊙ 表白句子不对～\n";
        }
        String[] addrs = addr.split(":");
        if (addrs.length != 2) return "☺ ☺ 服务器错误\n";

        //构造一个event对象，即发生了一个事件的数据传输对象
        Client from = new Client();
        from.setNick(fromNick);
        from.setIp(addrs[0].substring(1));
        from.setPort(addrs[1]);
        Client to = new Client(toNick);

        //记录一个event到日志
        Event event = new Event(action, from, to);
        //检查相似event是否已经记录过
        if (contains(event)) return "● ω ●: 你已经说过相同的话了。。\n";
        add(event);//添加到当前运行时的内存中

        if (event.match(getFromEvent(to.getNick()))) {
            log.iwish(toNick + " and " + fromNick + " " + act + " each other!");
            return "＠^^＠\n"
                    + "♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥\n"
                    + "Congratulation! " + toNick + " also "
                    + action.toString() + " you!\n"
                    + "♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ♥";
        }
        return "● ω ●！你们没有任何关系，"
                + toNick
                + "没有对你表示相同的感觉,或者尝试替换like和love，说不定结果就一样了。";
    }

    //反射的时候一起都定位有第一个参数，所以就放这里先，以后日志也用得上的
    public String show(String addr) {
        if (nicksCache.size() == 0) return "^ ^ ~ 还没有替他人，你是第一个到达这里的人\n";
        StringBuilder sb = new StringBuilder("这些人在这里出现过或者故意留下名字暗示你对ta说些什么：\n");
        int contr = 1;
        for (String nick : nicksCache) {
            if (contr % 5 == 0) sb.append("\n");
            sb.append(nick + " ");
            contr++;
        }
        return sb.toString() + "\n";
    }

    public String regist(String addr, String nick) {
        add(nick);
        return "☺ ☺ 名字添加成功\n";
    }

    //和命令一一对应的回调
    public String wish(String addr) {
        if (wishesCache.size() == 0) return "@ @ 還沒有任何值得慶祝的一對哦～\n";
        StringBuilder sb = new StringBuilder("恭喜这些对对方用了相同的喜欢的词，love不向下兼容like，也算筛掉了一半彼此喜欢的人，我只能做到这了。\n");
        int count = -11;
        for (String wish : wishesCache) {
            sb.append("  " + count + ".  " + wish);
            count++;
        }
        return sb.toString() + "\n";
    }

}
