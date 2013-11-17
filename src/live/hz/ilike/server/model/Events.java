package live.hz.ilike.server.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 7:36 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class Events {

    public Map<String, Option> events;

    public void add(Option option) {
        if (events == null) {
            events = new HashMap<String, Option>();
        }
        events.put(option.id, option);
    }

    public Option option(String optionId) {
        return events.get(optionId);
    }

    public Option next(String optionId) {
        return option(option(optionId).nextId);
    }

    public String invoke(String optionId, String commandId) {
        return events.get(optionId).invoke(commandId);
    }

}
