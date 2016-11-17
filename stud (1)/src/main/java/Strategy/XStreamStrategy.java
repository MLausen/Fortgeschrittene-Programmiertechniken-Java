package Strategy;

import com.thoughtworks.xstream.XStream;
import fpt.com.*;
import fpt.com.Product;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sufian Vaio on 16.11.2016.
 */
public class XStreamStrategy implements SerializableStrategy {

    XStream stream = new XStream();

    @Override
    public fpt.com.Product readObject() throws IOException {
        return null;
    }

    @Override
    public void writeObject(Product obj) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {

    }
}
