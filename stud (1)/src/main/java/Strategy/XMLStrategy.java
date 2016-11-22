package Strategy;

import Helper.ErrorDialog;
import fpt.com.*;
import fpt.com.Product;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Created by Team 10
 */
public class XMLStrategy implements SerializableStrategy {
    private XMLEncoder encoder;
    private XMLDecoder decoder;

    // TODO Exception
    // Array Index out of Bounds?
    // es wird auf index 12 zugegriffen, obwohl 12 elemente und 11 index
    // warum kommt programm in readObj meth, wenn for schleife das abdecken soll?
    @Override
    public fpt.com.Product readObject() throws IOException {
        Product p = null;
        try {
            p = (Product) decoder.readObject();
        } catch (Exception e) {
            ErrorDialog.error("Unfortunately, the requested file was not found.");
            e.printStackTrace();
            return null;
        }
        return p;
    }

    @Override
    public void writeObject(Product obj) throws IOException {
        encoder.writeObject(obj);
        encoder.flush();
        System.out.println(encoder + "naksdmkaslkdmas");
    }

    @Override
    public void close() throws IOException {
        if (encoder != null) {
            encoder.close();
        }
        if (decoder != null) {
            decoder.close();
        }
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {
        if (output != null) {
            this.encoder = new XMLEncoder(output);
        }
        if (input != null) {
            this.decoder = new XMLDecoder(input);
        }
    }
}
