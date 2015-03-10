package com.liuruining.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.liuruining.model.dao.Book2;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table book2.
*/
public class Book2Dao extends AbstractDao<Book2, Long> {

    public static final String TABLENAME = "book2";

    /**
     * Properties of entity Book2.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "ID");
        public final static Property IsSelect = new Property(1, Boolean.class, "isSelect", false, "IS_SELECT");
        public final static Property Spelling = new Property(2, String.class, "spelling", false, "SPELLING");
        public final static Property Meanning = new Property(3, String.class, "meanning", false, "MEANNING");
        public final static Property Phonetic_alphabet = new Property(4, String.class, "phonetic_alphabet", false, "PHONETIC_ALPHABET");
        public final static Property List = new Property(5, Long.class, "list", false, "LIST");
    };


    public Book2Dao(DaoConfig config) {
        super(config);
    }
    
    public Book2Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'book2' (" + //
                "'ID' INTEGER PRIMARY KEY ," + // 0: ID
                "'IS_SELECT' INTEGER," + // 1: isSelect
                "'SPELLING' TEXT," + // 2: spelling
                "'MEANNING' TEXT," + // 3: meanning
                "'PHONETIC_ALPHABET' TEXT," + // 4: phonetic_alphabet
                "'LIST' INTEGER);"); // 5: list
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'book2'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Book2 entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        Boolean isSelect = entity.getIsSelect();
        if (isSelect != null) {
            stmt.bindLong(2, isSelect ? 1l: 0l);
        }
 
        String spelling = entity.getSpelling();
        if (spelling != null) {
            stmt.bindString(3, spelling);
        }
 
        String meanning = entity.getMeanning();
        if (meanning != null) {
            stmt.bindString(4, meanning);
        }
 
        String phonetic_alphabet = entity.getPhonetic_alphabet();
        if (phonetic_alphabet != null) {
            stmt.bindString(5, phonetic_alphabet);
        }
 
        Long list = entity.getList();
        if (list != null) {
            stmt.bindLong(6, list);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Book2 readEntity(Cursor cursor, int offset) {
        Book2 entity = new Book2( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // isSelect
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // spelling
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // meanning
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phonetic_alphabet
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // list
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Book2 entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIsSelect(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setSpelling(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMeanning(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhonetic_alphabet(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setList(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Book2 entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Book2 entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
