package com.erwin.files;

import com.erwin.module.Contact;
import com.erwin.serviceimpl.OperationsImplementation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Properties;

public class ReadAndWriteFiles {

    List<Contact> list = null;
    Properties p = new Properties();

    public void writeDataToFile() {
        list = OperationsImplementation.getList();
        try {
            FileInputStream fs = new FileInputStream("E:\\Projects\\Java Projects\\Demos\\ErwinContacts\\src\\ConfigurationFiles\\info.properties");
            p.load(fs);
            FileOutputStream fos = new FileOutputStream(p.getProperty("path"));
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(list);
            os.close();
            fos.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    public void readDataFromFile() {

        try {
            FileInputStream fs = new FileInputStream("E:\\Projects\\Java Projects\\Demos\\ErwinContacts\\src\\ConfigurationFiles\\info.properties");
            p.load(fs);
            FileInputStream fis = new FileInputStream(p.getProperty("path"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            OperationsImplementation.allContacts = (List<Contact>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException | ClassNotFoundException io) {
            System.out.println(io);
        }
    }

}
