package com.lbins.hmjs.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EMP.
*/
public class EmpDao extends AbstractDao<Emp, String> {

    public static final String TABLENAME = "EMP";

    /**
     * Properties of entity Emp.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Empid = new Property(0, String.class, "empid", true, "EMPID");
        public final static Property Mobile = new Property(1, String.class, "mobile", false, "MOBILE");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property Nickname = new Property(3, String.class, "nickname", false, "NICKNAME");
        public final static Property Cover = new Property(4, String.class, "cover", false, "COVER");
        public final static Property Provinceid = new Property(5, String.class, "provinceid", false, "PROVINCEID");
        public final static Property Cityid = new Property(6, String.class, "cityid", false, "CITYID");
        public final static Property Areaid = new Property(7, String.class, "areaid", false, "AREAID");
        public final static Property Rzstate2 = new Property(8, String.class, "rzstate2", false, "RZSTATE2");
        public final static Property Is_use = new Property(9, String.class, "is_use", false, "IS_USE");
        public final static Property Dateline = new Property(10, String.class, "dateline", false, "DATELINE");
        public final static Property UserId = new Property(11, String.class, "userId", false, "USER_ID");
        public final static Property ChannelId = new Property(12, String.class, "channelId", false, "CHANNEL_ID");
        public final static Property DeviceType = new Property(13, String.class, "deviceType", false, "DEVICE_TYPE");
        public final static Property Pname = new Property(14, String.class, "pname", false, "PNAME");
        public final static Property CityName = new Property(15, String.class, "cityName", false, "CITY_NAME");
        public final static Property Is_push = new Property(16, String.class, "is_push", false, "IS_PUSH");
        public final static Property Rolestate = new Property(17, String.class, "rolestate", false, "ROLESTATE");
        public final static Property Lng = new Property(18, String.class, "lng", false, "LNG");
        public final static Property Lat = new Property(19, String.class, "lat", false, "LAT");
        public final static Property Yqnum = new Property(20, String.class, "yqnum", false, "YQNUM");
        public final static Property Topnum = new Property(21, String.class, "topnum", false, "TOPNUM");
        public final static Property Tjempid = new Property(22, String.class, "tjempid", false, "TJEMPID");
        public final static Property Sign = new Property(23, String.class, "sign", false, "SIGN");
    };

    private DaoSession daoSession;


    public EmpDao(DaoConfig config) {
        super(config);
    }
    
    public EmpDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EMP' (" + //
                "'EMPID' TEXT PRIMARY KEY NOT NULL ," + // 0: empid
                "'MOBILE' TEXT," + // 1: mobile
                "'PASSWORD' TEXT," + // 2: password
                "'NICKNAME' TEXT," + // 3: nickname
                "'COVER' TEXT," + // 4: cover
                "'PROVINCEID' TEXT," + // 5: provinceid
                "'CITYID' TEXT," + // 6: cityid
                "'AREAID' TEXT," + // 7: areaid
                "'RZSTATE2' TEXT," + // 8: rzstate2
                "'IS_USE' TEXT," + // 9: is_use
                "'DATELINE' TEXT," + // 10: dateline
                "'USER_ID' TEXT," + // 11: userId
                "'CHANNEL_ID' TEXT," + // 12: channelId
                "'DEVICE_TYPE' TEXT," + // 13: deviceType
                "'PNAME' TEXT," + // 14: pname
                "'CITY_NAME' TEXT," + // 15: cityName
                "'IS_PUSH' TEXT," + // 16: is_push
                "'ROLESTATE' TEXT," + // 17: rolestate
                "'LNG' TEXT," + // 18: lng
                "'LAT' TEXT," + // 19: lat
                "'YQNUM' TEXT," + // 20: yqnum
                "'TOPNUM' TEXT," + // 21: topnum
                "'TJEMPID' TEXT," + // 22: tjempid
                "'SIGN' TEXT);"); // 23: sign
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EMP'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Emp entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getEmpid());
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(2, mobile);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(4, nickname);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(5, cover);
        }
 
        String provinceid = entity.getProvinceid();
        if (provinceid != null) {
            stmt.bindString(6, provinceid);
        }
 
        String cityid = entity.getCityid();
        if (cityid != null) {
            stmt.bindString(7, cityid);
        }
 
        String areaid = entity.getAreaid();
        if (areaid != null) {
            stmt.bindString(8, areaid);
        }
 
        String rzstate2 = entity.getRzstate2();
        if (rzstate2 != null) {
            stmt.bindString(9, rzstate2);
        }
 
        String is_use = entity.getIs_use();
        if (is_use != null) {
            stmt.bindString(10, is_use);
        }
 
        String dateline = entity.getDateline();
        if (dateline != null) {
            stmt.bindString(11, dateline);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(12, userId);
        }
 
        String channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindString(13, channelId);
        }
 
        String deviceType = entity.getDeviceType();
        if (deviceType != null) {
            stmt.bindString(14, deviceType);
        }
 
        String pname = entity.getPname();
        if (pname != null) {
            stmt.bindString(15, pname);
        }
 
        String cityName = entity.getCityName();
        if (cityName != null) {
            stmt.bindString(16, cityName);
        }
 
        String is_push = entity.getIs_push();
        if (is_push != null) {
            stmt.bindString(17, is_push);
        }
 
        String rolestate = entity.getRolestate();
        if (rolestate != null) {
            stmt.bindString(18, rolestate);
        }
 
        String lng = entity.getLng();
        if (lng != null) {
            stmt.bindString(19, lng);
        }
 
        String lat = entity.getLat();
        if (lat != null) {
            stmt.bindString(20, lat);
        }
 
        String yqnum = entity.getYqnum();
        if (yqnum != null) {
            stmt.bindString(21, yqnum);
        }
 
        String topnum = entity.getTopnum();
        if (topnum != null) {
            stmt.bindString(22, topnum);
        }
 
        String tjempid = entity.getTjempid();
        if (tjempid != null) {
            stmt.bindString(23, tjempid);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(24, sign);
        }
    }

    @Override
    protected void attachEntity(Emp entity) {
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
    public Emp readEntity(Cursor cursor, int offset) {
        Emp entity = new Emp( //
            cursor.getString(offset + 0), // empid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mobile
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // cover
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // provinceid
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // cityid
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // areaid
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // rzstate2
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // is_use
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // dateline
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // userId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // channelId
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // deviceType
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // pname
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // cityName
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // is_push
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // rolestate
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // lng
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // lat
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // yqnum
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // topnum
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // tjempid
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23) // sign
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Emp entity, int offset) {
        entity.setEmpid(cursor.getString(offset + 0));
        entity.setMobile(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNickname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCover(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setProvinceid(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCityid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAreaid(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRzstate2(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIs_use(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDateline(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUserId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setChannelId(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDeviceType(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setPname(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCityName(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setIs_push(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setRolestate(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setLng(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setLat(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setYqnum(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setTopnum(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setTjempid(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setSign(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(Emp entity, long rowId) {
        return entity.getEmpid();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(Emp entity) {
        if(entity != null) {
            return entity.getEmpid();
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
