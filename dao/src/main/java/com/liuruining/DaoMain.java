package com.liuruining;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by nielongyu on 15/3/9.
 */
public class DaoMain {
    private static int DB_VERSION = -1;//need to be read from build.gradle


    public static void main(String[] args) throws Exception {
        String relativelyPath = System.getProperty("user.dir");
        DB_VERSION = findDB_VERSION(relativelyPath);
        Schema schema = new Schema(DB_VERSION, "com.liuruining.model.dao");
        schema.enableKeepSectionsByDefault();
        createBook(schema);
        createBook1(schema);
        createBook2(schema);
        createBook3(schema);
        createWord(schema);
        createLastLocation(schema);
        new DaoGenerator().generateAll(schema, relativelyPath + "/model/src/main/java");
    }

    private static void createBook(Schema schema) {
        Entity book = schema.addEntity("Book");
        book.implementsSerializable();
        book.setTableName("BOOKS");
        book.addStringProperty("ID").primaryKey();
        book.addStringProperty("NAME");
        book.addLongProperty("NUMOFLIST");
        book.addLongProperty("NUMOFWORD");
    }

    private static void createBook1(Schema schema) {
        Entity book = schema.addEntity("Book1");
        book.implementsSerializable();
        book.setTableName("book1");
        book.addLongProperty("ID").primaryKey();
        book.addStringProperty("spelling");
        book.addStringProperty("meanning");
        book.addStringProperty("phonetic_alphabet");
        book.addLongProperty("list");
    }

    private static void createBook2(Schema schema) {
        Entity book = schema.addEntity("Book2");
        book.implementsSerializable();
        book.setTableName("book2");
        book.addLongProperty("ID").primaryKey();
        book.addBooleanProperty("isSelect");
        book.addStringProperty("spelling");
        book.addStringProperty("meanning");
        book.addStringProperty("phonetic_alphabet");
        book.addLongProperty("list");
    }

    private static void createBook3(Schema schema) {
        Entity book = schema.addEntity("Book3");
        book.implementsSerializable();
        book.setTableName("book3");
        book.addLongProperty("ID").primaryKey();
        book.addStringProperty("spelling");
        book.addStringProperty("meanning");
        book.addStringProperty("phonetic_alphabet");
        book.addLongProperty("list");
    }

    private static void createWord(Schema schema) {
        Entity book = schema.addEntity("Word");
        book.implementsSerializable();
        book.setTableName("word");
        book.addLongProperty("ID").primaryKey();
        book.addStringProperty("spelling");
        book.addStringProperty("meanning");
        book.addStringProperty("phonetic_alphabet");
        book.addLongProperty("list");
    }

    private static void createLastLocation(Schema schema) {
        Entity book = schema.addEntity("WordLocation");
        book.implementsSerializable();
        book.setTableName("word_location");
        book.addLongProperty("ID").primaryKey();
        book.addIntProperty("book");
        book.addIntProperty("index");
    }

    private static int findDB_VERSION(String relativelyPath) throws IOException {

        File file = new File(relativelyPath + "/app/build.gradle");
        if (!file.exists())
            throw new FileNotFoundException(file.getAbsolutePath());

        FileInputStream fis = null;
        ByteArrayBuffer byteArray = null;
        try {
            fis = new FileInputStream(file);

            byteArray = new ByteArrayBuffer();

            byte[] buffer = new byte[1024];
            int l = -1;
            do {
                l = fis.read(buffer);
                if (l != -1) {
                    byteArray.write(buffer, 0, l);
                }

            } while (l != -1);

            String str = new String(byteArray.getRawData(), 0, byteArray.size());
            int index = str.indexOf("android");
            index = str.indexOf("defaultConfig", index);
            index = str.indexOf("versionCode", index);
            index = str.indexOf(" ", index);
            int returnIndex = str.indexOf("\n", index);
            String version = str.substring(index + 1, returnIndex);

            return Integer.valueOf(version);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("version code is not found.");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (byteArray != null) {
                byteArray.close();
            }
        }
    }
}
