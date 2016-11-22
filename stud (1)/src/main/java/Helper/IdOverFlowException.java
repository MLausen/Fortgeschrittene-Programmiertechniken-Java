package Helper;

/**
 * Created by Team 10
 */

// exception thrown in case of unavailable id index
public class IdOverFlowException extends Exception {
    public IdOverFlowException(){
        ErrorDialog.error("Reached maximum number of products.");
    }
}
