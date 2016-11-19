package Helper;

import Services.ProductList;
import fpt.com.Product;

import java.util.List;

/**
 * Created by Team 1/2Hobbyte
 */
public class IdGenerator {
    // ML Why is id static and method is not?
    // do we need object if idgenerator?
    // why we generate for all products one (ControllerShop)
    // why not generate onNewProduct
    /*static long id ;
    public long generate(ProductList p) throws  IdOverFlowException {
        for (id = 1; id <= 999999; id++) {
            if(p.findProductById(id) == null)
                return id;
        }
        throw new IdOverFlowException();
    }*/

    private static long id = 0;
    public static long generateID() throws IdOverFlowException {
        for (id = 1; id <= 999999; id++) {
            if(ProductList.getInstance().findProductById(id) == null)
                return id;
        }
        throw new IdOverFlowException();
    }
}
