package Helper;

/**
 * Created by Team 10
 */
public class IdOverFlowException extends Exception {
    public IdOverFlowException(){
        ErrorDialog.error("Reached maximum number of products.");
    }
}
