package com.lbins.hmjs.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table HAPPY_HAND_NOTICE.
*/
public class HappyHandNoticeDao extends AbstractDao<HappyHandNotice, String> {

    public static final String TABLENAME = "HAPPY_HAND_NOTICE";

    /**
     * Properties of entity HappyHandNotice.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Noticeid = new Property(0, String.class, "noticeid", true, "NOTICEID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Dateline = new Property(2, String.class, "dateline", false, "DATELINE");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property Is_read = new Property(4, String.class, "is_read", false, "IS_READ");
    };

    private DaoSession daoSession;


    public HappyHandNoticeDao(DaoConfig config) {
        super(config);
    }
    
    public HappyHandNoticeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'HAPPY_HAND_NOTICE' (" + //
                "'NOTICEID' TEXT PRIMARY KEY NOT NULL ," + // 0: noticeid
                "'TITLE' TEXT," + // 1: title
                "'DATELINE' TEXT," + // 2: dateline
                "'CONTENT' TEXT," + // 3: content
                "'IS_READ' TEXT);"); // 4: is_read
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'HAPPY_HAND_NOTICE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, HappyHandNotice entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getNoticeid());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String dateline = entity.getDateline();
        if (dateline != null) {
            stmt.bindString(3, dateline);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
 
        String is_read = entity.getIs_read();
        if (is_read != null) {
            stmt.bindString(5, is_read);
        }
    }

    @Override
    protected void attachEntity(HappyHandNotice entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public HappyHandNotice readEntity(Cursor cursor, int offset) {
        HappyHandNotice entity = new HappyHandNotice( //
            cursor.getString(offset + 0), // noticeid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // dateline
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // is_read
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, HappyHandNotice entity, int offset) {
        entity.setNoticeid(cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDateline(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIs_read(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(HappyHandNotice entity, long rowId) {
        return entity.getNoticeid();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(HappyHandNotice entity) {
        if(entity != null) {
            return entity.getNoticeid();
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
