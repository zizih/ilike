package live.hz.ilike.util;

import com.google.gson.Gson;
import live.hz.ilike.model.Client;
import live.hz.ilike.model.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        ilikeIni();
        iRegIni();
    }

    //info 为event的Json字符串
    public void ilike(String info) {
        _like_logger.log(Level.INFO, info);
    }

    //info 为一个nick
    public void iregist(String info) {
        _regist_logger.log(Level.INFO, info);
    }

    public List<Event> toEvents() {
        List<Event> events = new ArrayList<Event>();
        try {
            File file = new File("like.log");
            if (!file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.equals("") || line == "") break;
                    int index = line.indexOf("detail:");
                    if (index == -1) continue;
                    String tmp = line.substring(index + "detail:".length());
                    Event event = _gson.fromJson(tmp, Event.class);
                    events.add(event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<String> toNicks() {
        List<String> nicks = new ArrayList<String>();
        try {
            File file = new File("regist.log");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.equals("") || line == "") break;
                    nicks.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nicks;
    }

    private void iRegIni() {
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
    }

    private void ilikeIni() {
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

    class LikeFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            try {
                Event event = _gson.fromJson(record.getMessage(), Event.class);
                //return like such: [1384519003991] rain:like:dad detail:{}
                return String.format("[%s]%s:%s:%s  detail:%s\n",
                        event.getTime(),
                        event.getFrom().getNick(),
                        event.getAction(),
                        event.getTo().getNick(),
                        record.getMessage());
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
                return record.getMessage() + "\n";
            } catch (Exception e) {
                return "";
            }
        }
    }

}
