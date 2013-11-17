package live.hz.ilike.util;

import com.google.gson.Gson;
import live.hz.ilike.model.Client;
import live.hz.ilike.model.Event;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/6/13
 * Time: 6:24 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class Log {

    //第一次把private命名加上_
    private static Log _instance;
    private Formatter _like_formatter;
    private Formatter _regist_formatter;
    private Handler _like_handler;
    private Handler _regist_handler;
    private Gson _gson;
    private Logger _like_logger;
    private Logger _regist_logger;

    public static Log ini() {
        if (_instance == null) {
            _instance = new Log();
        }
        return _instance;
    }

    private Log() {
        //Gson util
        _gson = new Gson();
    }

    public void ilike(String info) {
        if (_like_logger == null && _like_handler == null && _like_formatter == null) {
            _like_formatter = new LikeFormatter();
            try {
                //设置like logger的参数
                _like_handler = new FileHandler("like.log", true);
                _like_handler.setFormatter(_like_formatter);
                _like_logger = Logger.getLogger(Thread.currentThread().getName());
                _like_logger.addHandler(_like_handler);
                _like_logger.setLevel(Level.INFO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        _like_logger.log(Level.INFO, info);
    }

    public void iregist(String info) {
        if (_regist_logger == null && _regist_handler == null && _regist_formatter == null) {
            _regist_formatter = new RegistFormatter();
            try {
                //设置regist logger的参数
                _regist_handler = new FileHandler("regist.log", true);
                _regist_handler.setFormatter(_regist_formatter);
                _regist_logger = Logger.getLogger(Thread.currentThread().getName());
                _regist_logger.addHandler(_regist_handler);
                _regist_logger.setLevel(Level.INFO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        _regist_logger.log(Level.INFO, info);
    }

    class LikeFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            try {
                Event event = _gson.fromJson(record.getMessage(), Event.class);
                //return like such: [1384519003991] rain:like:dad
                return String.format("[%s]%s:%s:%s\n",
                        record.getMillis(),
                        event.getFrom().getNick(),
                        event.getAction(),
                        event.getTo().getNick());
            } catch (Exception e) {
                return String.format("[%s]<<%s>>\n",
                        record.getMillis(),
                        "err");
            }
        }
    }

    class RegistFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            try {
                Client client = _gson.fromJson(record.getMessage(), Client.class);
                //return like such: [1384519003991] jack:dios:10.50.9.27:9600
                return String.format("[%s]%s:%s:%s:$s\n",
                        record.getMillis(),
                        client.getNick(),
                        client.getDesc(),
                        client.getIp(),
                        client.getPort());
            } catch (Exception e) {
                return String.format("[%s]<<%s>>\n",
                        record.getMillis(),
                        "err");
            }
        }
    }

}
