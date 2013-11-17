package live.hz.ilike.model;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/15/13
 * Time: 7:38 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class Event {

    private Client from;

    private Client to;

    private Action action;

    public Event(Action action, Client from, Client to) {
        this.action = action;
        this.from = from;
        this.to = to;
    }

    public Event(Action action, String fromNick, String toNick) {
        this.action = action;
        this.from = from;
        this.to = to;
    }

    public Action getAction() {
        return action;
    }

    public Client getFrom() {
        return from;
    }

    public Client getTo() {
        return to;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setFrom(Client from) {
        this.from = from;
    }

    public void setTo(Client to) {
        this.to = to;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public enum Action {

        hate,

        ublike,

        nothing,

        like,

        love;
    }
}
