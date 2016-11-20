package Strategy;

import Helper.ErrorDialog;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import fpt.com.*;
import fpt.com.Product;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;

/**
 * Created by Team 10
 */
public class XStreamStrategy implements SerializableStrategy {
    XStream stream;
    Object output;

    FileReader reader;
    FileWriter writer;

    ByteArrayInputStream byteInput;
    ByteArrayOutputStream byteOutput;

    @Override
    public fpt.com.Product readObject() throws IOException {
        output = (fpt.com.Product) stream.fromXML(reader);
        return (fpt.com.Product) output;
    }

    @Override
    public void writeObject(Product obj) throws  IOException{
        // auf rat von uebungsleiter hin
        if(stream == null){
            writer = new FileWriter("XStreamSer.xml");
            stream = new XStream(new DomDriver());
        }
        stream.toXML(obj, writer);
        //TODO to make id 6 char
        //createXStream().aliasAttribute();
        //stream.registerConverter(new Helper.SingleValueConverter());
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
    }

    @Override
    public void open(InputStream input, OutputStream output) throws IOException {  }

    // default method from interface
    // public XStream createXStream(){}
}
