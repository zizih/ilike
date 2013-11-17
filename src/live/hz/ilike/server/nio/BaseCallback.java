package live.hz.ilike.server.nio;

import live.hz.ilike.model.Event;
import live.hz.ilike.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/17/13
 * Time: 6:30 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class BaseCallback {

    protected List<Event> eventsCache;
    protected List<String> nicksCache;
    protected Log log;

    public BaseCallback() {
        log = Log.ini();
        eventsCache = log.toEvents();
        nicksCache = log.toNicks();
    }

    public void add(Event event) {
        this.eventsCache.add(event);
        addNick(event.getFrom().getNick());
        log.ilike(event.toJson());
    }

    /**
     * 判断条件是fromNick、action、toNick相同
     *
     * @param event
     * @return
     */
    public boolean contains(Event event) {
        if (this.eventsCache == null) return false;
        for (Event e : this.eventsCache) {
            if (e.getFrom().getNick().equals(event.getFrom().getNick())
                    && e.getTo().getNick().equals(event.getTo().getNick())
                    && e.getAction().equals(e.getAction())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回nick作为event的发起者的所有event
     *
     * @param nick
     * @return
     */
    protected List<Event> getFromEvent(String nick) {
        if (this.eventsCache == null) return null;
        List<Event> froms = new ArrayList<Event>();
        for (Event from : this.eventsCache) {
            if (from.getFrom().getNick().equals(nick)) {
                froms.add(from);
            }
        }
        return froms;
    }

    /**
     * 返回nikc作为event接受者的所有event
     *
     * @param nick
     * @return
     */
    protected List<Event> getToEvent(String nick) {
        if (this.eventsCache == null) return null;
        List<Event> tos = new ArrayList<Event>();
        for (Event to : this.eventsCache) {
            if (to.getTo().getNick().equals(nick)) {
                tos.add(to);
            }
        }
        return tos;
    }

    /**
     * 返回不重复的event发起者的名字列表
     *
     * @return
     */
    protected List<String> getFromNicks() {
        if (this.eventsCache == null) return null;
        List<String> nicks = new ArrayList<String>();
        for (Event e : this.eventsCache) {
            if (!nicks.contains(e.getFrom().getNick())) {
                nicks.add(e.getFrom().getNick());
            }
        }
        return nicks;
    }

    protected List<String> getNicks() {
        return this.nicksCache;
    }

    //更新内存和log
    protected void addNick(String nick) {
        if (!nicksCache.contains(nick)) {
            nicksCache.add(nick.trim());
            log.iregist(nick);
        }
    }

    /**
     * 返回fromNick和toNick相同的event列表
     *
     * @param fromNick
     * @param toNick
     * @return
     */
    protected List<Event> getCommonEvents(String fromNick, String toNick) {
        if (this.eventsCache == null) return null;
        List<Event> es = new ArrayList<Event>();
        for (Event to : this.eventsCache) {
            if (to.getFrom().getNick().equals(fromNick)
                    && to.getTo().getNick().equals(toNick)) {
                es.add(to);
            }
        }
        return es;
    }

    /**
     * return OK 表示通过
     * 否则表示action冲突
     *
     * @param fromNick
     * @param toNick
     * @return
     */
    protected String verify(String fromNick, Event.Action action, String toNick) {
        if (this.eventsCache == null) return "OK";
        List<Event.Action> as = new ArrayList<Event.Action>();
        as.add(action);
        for (Event e : this.eventsCache) {
            if (e.getFrom().getNick().equals(fromNick)
                    && e.getTo().getNick().equals(toNick)) {
                if (!as.contains(e.getAction())) {
                    as.add(e.getAction());
                }
            }
        }
        if ((as.contains(Event.Action.like) || as.contains(Event.Action.love))
                && as.contains(Event.Action.hate)) {
            return "Warn: 你不能"
                    + "对"
                    + toNick
                    + "既"
                    + (as.contains(Event.Action.like) ? Event.Action.like.toString() : Event.Action.love.toString())
                    + "又"
                    + Event.Action.hate.toString();
        }
        return "OK";
    }

}
