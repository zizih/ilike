package live.hz.ilike.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rain
 * Date: 11/15/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Group {

    private List<Client> clients;

    public void add(Client client) {
        this.clients.add(client);
    }

    public int remove(Client... cs) {
        for (Client client : cs) {
            this.clients.remove(client);
        }
        return cs.length;
    }
}
