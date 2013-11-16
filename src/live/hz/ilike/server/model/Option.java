package live.hz.ilike.server.model;

import live.hz.ilike.server.model.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/16/13
 * Time: 6:58 PM
 * email: zizihjk@gmail.com，作者是个好人
 */
public class Option {

    public String id;

    public String prompt;

    public Map<String, Command> commands;

    public void addCommands(Command... commands) {
        if (this.commands == null) {
            this.commands = new HashMap<String, Command>();
        }
        StringBuilder pattern = new StringBuilder();
        for (Command comm : commands) {
            this.commands.put(comm.id, comm);
            pattern.append("\n" + comm.prompt);
        }
        this.prompt += "\n"
                + "Step: " +id
                + pattern.toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String invoke(String commId) {
        return commands.get(commId).invoke();
    }

}
