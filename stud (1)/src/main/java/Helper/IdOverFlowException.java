package Helper;

/**
 * Created by Team 1/2Hobbyte
 */
public class IdOverFlowException extends Exception {
    public IdOverFlowException(){
        ErrorDialog.error("Reached maximum number of products.");
    }
}
