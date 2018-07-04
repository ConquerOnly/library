package i.android.library.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtils {

    public static final void save(String path,Object object){
        FileOutputStream fos=null;
        ObjectOutputStream oos=null;
        File file=new File(path);
        try {
            fos=new FileOutputStream(file);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(object);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(oos!=null) oos.close();
                if(fos!=null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static final Object restore(String path){

        FileInputStream fis=null;
        ObjectInputStream ois=null;
        File file=new File(path);
        Object object=null;
        if(!file.exists()) return null;
        try {
            fis=new FileInputStream(file);
            ois=new ObjectInputStream(fis);
            object=ois.readObject();
            return object;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ois!=null) ois.close();
                if(fis!=null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }return object;
    }
}
