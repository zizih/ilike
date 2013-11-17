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

    protected List<Event> events;
    protected Log log;

    public BaseCallback() {
        log = Log.ini();
        events = log.toEvents();
    }

    public void add(Event event) {
        this.events.add(event);
    }

    /**
     * 返回nick作为event的发起者的所有event
     *
     * @param nick
     * @return
     */
    protected List<Event> getFromEvent(String nick) {
        List<Event> froms = new ArrayList<Event>();
        for (Event from : this.events) {
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
        List<Event> tos = new ArrayList<Event>();
        for (Event to : this.events) {
            if (to.getTo().getNick().equals(nick)) {
                tos.add(to);
            }
        }
        return null;
    }

}
