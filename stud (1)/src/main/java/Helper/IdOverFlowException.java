package Helper;

/**
 * Created by Team 10
 */

//id overflow exception when all 999999 ids are used
public class IdOverFlowException extends Exception {
    public IdOverFlowException(){
        ErrorDialog.error("Reached maximum number of products.");
    }
}
