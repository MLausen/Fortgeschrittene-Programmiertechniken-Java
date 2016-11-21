package Strategy;

import Helper.ErrorDialog;
import Helper.SingleValueConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import fpt.com.*;
import fpt.com.Product;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;

/**
 * Created by Team 10
 */
//http://stackoverflow.com/questions/24894108/add-a-root-element-to-a-xml
//not allowed to iterate with for loop
public class XStreamStrategy implements SerializableStrategy {
    XStream stream;
    Object output;

    FileReader reader;
    FileWriter writer;

    ByteArrayInputStream byteInput;
    ByteArrayOutputStream byteOutput;

    @Override
    public fpt.com.Product readObject() throws IOException {
        if (stream == null) {
            stream = new XStream(new DomDriver());
            reader = new FileReader("xproducts.xml");
        }

        stream.registerConverter(new SingleValueConverter(), XStream.PRIORITY_LOW);
        output = stream.fromXML(reader);
        return (fpt.com.Product) output;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        if (stream == null) {
            writer = new FileWriter("xproducts.xml");
            stream = new XStream(new DomDriver());
        }

        stream.registerConverter(new SingleValueConverter(), XStream.PRIORITY_LOW);
        //stream.alias("waren", Model.Product.class);

        stream.toXML(obj, writer);
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
    }

    // default method from interface
    // public XStream createXStream(){}
}
