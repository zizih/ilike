package live.hz.ilike.server.nio;

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

    public String demo() {
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

    public String todo() {
        return "todo....";
    }

}
