package ChatComponents;

import java.util.List;

/**
 * Created by Team 10
 */
public interface ChatService {
    boolean login();

    boolean logout();

    boolean send();

    List<String> getUserList();
}
