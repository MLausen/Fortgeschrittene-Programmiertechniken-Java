package Helper;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import fpt.com.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Team 10
 */
public class SingleValueConverter implements Converter {
    // tutorial http://x-stream.github.io/converter-tutorial.html

    @Override
    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        Product temp = (Product) obj;

        // add id to head
        writer.addAttribute("id", getSixCharId(temp.getId()));

        // create child nodes

        writer.startNode("name");
        writer.setValue(temp.getName());
        writer.endNode();

        writer.startNode("price");
        writer.setValue(parseToPriceFormat(temp.getPrice()));
        writer.endNode();

        writer.startNode("quantity");
        writer.setValue("" + temp.getQuantity());
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
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
