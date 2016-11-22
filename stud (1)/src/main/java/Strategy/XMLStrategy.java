package Strategy;

import fpt.com.Product;
import fpt.com.SerializableStrategy;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Created by Team 10
 */
public class XMLStrategy implements SerializableStrategy {
    private XMLEncoder encoder;
    private XMLDecoder decoder;

    private FileOutputStream fos;
    private FileInputStream fis;

    @Override
    public fpt.com.Product readObject() throws IOException {
        Product p = null;
        try {
            p = (Product) decoder.readObject();
        } catch (Exception e) {
            return null;
        }
        return p;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        encoder.writeObject(obj);
        encoder.flush();
    }

    @Override
    public void close() throws IOException {
        if(encoder!=null) encoder.close();
        if(decoder!=null) decoder.close();
        if(fis!=null) fis.close();
        if(fos!=null)  fos.close();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (output != null) {
            this.encoder = new XMLEncoder(output);
            this.fos = (FileOutputStream) output;
        }
        if (input != null) {
            this.decoder = new XMLDecoder(input);
            this.fis = (FileInputStream) input;
        }
    }
}
