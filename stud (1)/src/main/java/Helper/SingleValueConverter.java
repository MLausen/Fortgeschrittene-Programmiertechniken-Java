package Helper;

import Services.ProductList;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import fpt.com.Product;

import java.text.DecimalFormat;

/**
 * Created by Team 10
 */
public class SingleValueConverter implements Converter {
    // tutorial http://x-stream.github.io/converter-tutorial.html
    private int index = 0;

    @Override
    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        /*if(index == 0){
            writer.startNode("xml");
            writer.addAttribute("version", "1.0");
            writer.addAttribute("encoding", "UTF-8");
        }*/

        Product prod = (Product) obj;

        // add id to head
        writer.addAttribute("id", getSixCharId(prod.getId()));

        // create child nodes

        writer.startNode("name");
        writer.setValue(prod.getName());
        writer.endNode();

        writer.startNode("price");
        writer.setValue(parseToPriceFormat(prod.getPrice()));
        writer.endNode();

        writer.startNode("quantity");
        writer.setValue("" + prod.getQuantity());
        writer.endNode();
        index++;
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Product prod = new Model.Product();

        //go to first position
        reader.moveDown();
        prod.setId(Long.parseLong(reader.getAttribute("id")));

        for (int i = 0; i < 3; i++){
            // go to next position
            reader.moveDown();

            if(reader.getNodeName().equals("name")) {
                prod.setName(reader.getValue());
            }

            if(reader.getNodeName().equals("quantity")) {
                prod.setQuantity(Integer.parseInt(reader.getValue()));
            }

            if(reader.getNodeName().equals("price")) {
                prod.setPrice(Double.parseDouble(reader.getValue()));
            }
            // move back to tag
            reader.moveUp();
        }
        return prod;
    }

    @Override
    public boolean canConvert(Class c) {
        return c.equals(Model.Product.class);
    }

    private String parseToPriceFormat(double price){
        // https://docs.oracle.com/javase/tutorial/i18n/format/decimalFormat.html#numberpattern
        DecimalFormat df = new DecimalFormat();
        df.applyLocalizedPattern("0.00");
        String formattedPrice = df.format(price);
        return formattedPrice;
    }

   /* private long getLongValue(String id){
        return Long.parseLong(id);
    }*/

    private String getSixCharId(long id) {
        if (id < 10) {
            return "00000" + id;
        }
        if (id < 100) {
            return "0000" + id;
        }
        if (id < 1000) {
            return "000" + id;
        }
        if (id < 10000){
            return "00" + id;
        }
        if (id < 100000){
            return "0" + id;
        }
            return "" +  id;
    }
}
