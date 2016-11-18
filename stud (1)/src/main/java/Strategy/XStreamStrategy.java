package Strategy;

import Helper.ErrorDialog;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import fpt.com.*;
import fpt.com.Product;

import javax.swing.*;
import java.io.*;

/**
 * Created
 */
public class XStreamStrategy implements SerializableStrategy {
    XStream stream;
    Object output;

    FileReader reader;
    FileWriter writer;

    @Override
    public fpt.com.Product readObject() {
        try (FileReader fileReader = new FileReader("XStreamSer.xml")) {
            output = (fpt.com.Product) stream.fromXML(fileReader);
        } catch (IOException io) {
            ErrorDialog.error("Unfortunately, the requested file was not found.");
        }

        return (fpt.com.Product) output;
    }

    @Override
    public void writeObject(Product obj) {
        stream = new XStream(new DomDriver());
        try (FileWriter fileReader = new FileWriter("XStreamSer.xml")) {
            stream.toXML(obj, fileReader);
        } catch (IOException io) {
            ErrorDialog.error("Unfortunately, the file could not be created.");
            io.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
       /* if (input != null) {
            reader = new FileReader();
        }
        if (output != null) {
            writer = new FileWriter(output);
        }*/
    }

    // default method from interface
    //public XStream createXStream(){}
}
