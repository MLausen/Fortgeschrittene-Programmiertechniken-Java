package Strategy;

import Helper.SingleValueConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import fpt.com.Product;
import fpt.com.SerializableStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team 10
 */

public class XStreamStrategy implements SerializableStrategy {
    XStream stream = new XStream(new DomDriver());
    ArrayList<Product> products;

    FileReader reader;
    FileWriter writer;

    private FileOutputStream fos;
    private FileInputStream fis;

    int i = 0;

    @Override
    public fpt.com.Product readObject() throws IOException {
        if (i < products.size()) return products.get(i++);
        return null;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        stream.registerConverter(new SingleValueConverter(), XStream.PRIORITY_LOW);
        stream.alias("ware", Model.Product.class);
        products.add(obj);
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            i= 0;
            reader.close();
        }
        if (writer != null) {
            stream.alias("waren", List.class);
            stream.toXML(products, writer);
            writer.close();
        }
        if(fis != null) fis.close();
        if(fos != null) fos.close();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (input != null) {
            fis = (FileInputStream) input;
            reader = new FileReader(fis.getFD());
            products = null;

            stream.alias("waren", List.class);
            stream.alias("ware", Model.Product.class);
            stream.registerConverter(new SingleValueConverter());

            products = (ArrayList<Product>) stream.fromXML(reader);
        }
        if (output != null) {
            fos = (FileOutputStream) output;
            writer = new FileWriter(fos.getFD());
            products = new ArrayList<>();
        }
    }
}
