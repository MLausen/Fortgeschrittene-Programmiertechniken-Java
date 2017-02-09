package ChatComponents;

import java.util.Observable;

/**
 * Created by Team 10
 */
public class ChatContentObservable extends Observable {
    String text;

    public ChatContentObservable(){
        this.text = "Du hast den Chat betreten.";
    }

    public String getText(){
        return this.text;
    }

    public synchronized void setText(String text) {
        this.text = text;
        setChanged();
        notifyObservers(text);
    }
}
