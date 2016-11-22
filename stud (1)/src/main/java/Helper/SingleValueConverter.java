package Helper;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import fpt.com.Product;

import java.util.Locale;

/**
 * Created by Team 10
 */
public class SingleValueConverter implements Converter {

    @Override
    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        Product prod = (Product) obj;

        // add id as attribute and convert the id with 6 digits id
        writer.addAttribute("id", String.format("%06d", prod.getId()));

        /*
        creating xml nodes by opening tag, writing tag and closung tag
         */

        writer.startNode("name");
        writer.setValue(prod.getName());
        writer.endNode();

        writer.startNode("price");
        writer.setValue(String.format(Locale.US, "%.2f", prod.getPrice())); //make the double number as 2 digits after comma
        writer.endNode();

        writer.startNode("quantity");
        writer.setValue("" + prod.getQuantity());
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Product prod = new Model.Product();

        // read the attribute id and set it to product
        prod.setId(Long.parseLong(reader.getAttribute("id")));

        // complete reading until there is no tags
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            if ("name".equalsIgnoreCase(nodeName)) {
                prod.setName(reader.getValue());
            } else if ("price".equalsIgnoreCase(nodeName)) {
                prod.setPrice(Double.parseDouble(reader.getValue()));
            } else if ("quantity".equalsIgnoreCase(nodeName)) {
                prod.setQuantity(Integer.parseInt(reader.getValue()));
            }
            reader.moveUp();
        }
        return prod;
    }

    @Override
    public boolean canConvert(Class c) {
        return c.equals(Model.Product.class);
    }
}
