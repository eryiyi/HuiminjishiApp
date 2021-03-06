package com.lbins.hmjs.dao;

import android.database.sqlite.SQLiteDatabase;
import com.lbins.hmjs.module.ShoppingCart;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig empDaoConfig;
    private final DaoConfig happyHandMessageDaoConfig;
    private final DaoConfig happyHandNewsDaoConfig;
    private final DaoConfig happyHandNoticeDaoConfig;
    private final DaoConfig happyHandJwDaoConfig;
    private final DaoConfig friendsDaoConfig;
    private final DaoConfig happyHandGroupDaoConfig;
    private final DaoConfig shoppingCartDaoConfig;

    private final EmpDao empDao;
    private final HappyHandMessageDao happyHandMessageDao;
    private final HappyHandNewsDao happyHandNewsDao;
    private final HappyHandNoticeDao happyHandNoticeDao;
    private final HappyHandJwDao happyHandJwDao;
    private final FriendsDao friendsDao;
    private final HappyHandGroupDao happyHandGroupDao;

    private final ShoppingCartDao shoppingCartDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        shoppingCartDaoConfig = daoConfigMap.get(ShoppingCartDao.class).clone();
        shoppingCartDaoConfig.initIdentityScope(type);

        empDaoConfig = daoConfigMap.get(EmpDao.class).clone();
        empDaoConfig.initIdentityScope(type);

        happyHandMessageDaoConfig = daoConfigMap.get(HappyHandMessageDao.class).clone();
        happyHandMessageDaoConfig.initIdentityScope(type);

        happyHandNewsDaoConfig = daoConfigMap.get(HappyHandNewsDao.class).clone();
        happyHandNewsDaoConfig.initIdentityScope(type);

        happyHandNoticeDaoConfig = daoConfigMap.get(HappyHandNoticeDao.class).clone();
        happyHandNoticeDaoConfig.initIdentityScope(type);

        happyHandJwDaoConfig = daoConfigMap.get(HappyHandJwDao.class).clone();
        happyHandJwDaoConfig.initIdentityScope(type);

        friendsDaoConfig = daoConfigMap.get(FriendsDao.class).clone();
        friendsDaoConfig.initIdentityScope(type);

        happyHandGroupDaoConfig = daoConfigMap.get(HappyHandGroupDao.class).clone();
        happyHandGroupDaoConfig.initIdentityScope(type);

        empDao = new EmpDao(empDaoConfig, this);
        happyHandMessageDao = new HappyHandMessageDao(happyHandMessageDaoConfig, this);
        happyHandNewsDao = new HappyHandNewsDao(happyHandNewsDaoConfig, this);
        happyHandNoticeDao = new HappyHandNoticeDao(happyHandNoticeDaoConfig, this);
        happyHandJwDao = new HappyHandJwDao(happyHandJwDaoConfig, this);
        friendsDao = new FriendsDao(friendsDaoConfig, this);
        happyHandGroupDao = new HappyHandGroupDao(happyHandGroupDaoConfig, this);



        shoppingCartDao = new ShoppingCartDao(shoppingCartDaoConfig, this);




        registerDao(Emp.class, empDao);
        registerDao(HappyHandMessage.class, happyHandMessageDao);
        registerDao(HappyHandNews.class, happyHandNewsDao);
        registerDao(HappyHandNotice.class, happyHandNoticeDao);
        registerDao(HappyHandJw.class, happyHandJwDao);
        registerDao(Friends.class, friendsDao);
        registerDao(HappyHandGroup.class, happyHandGroupDao);
        registerDao(ShoppingCart.class, shoppingCartDao);
    }
    
    public void clear() {
        empDaoConfig.getIdentityScope().clear();
        happyHandMessageDaoConfig.getIdentityScope().clear();
        happyHandNewsDaoConfig.getIdentityScope().clear();
        happyHandNoticeDaoConfig.getIdentityScope().clear();
        happyHandJwDaoConfig.getIdentityScope().clear();
        friendsDaoConfig.getIdentityScope().clear();
        happyHandGroupDaoConfig.getIdentityScope().clear();
    }

    public EmpDao getEmpDao() {
        return empDao;
    }

    public HappyHandMessageDao getHappyHandMessageDao() {
        return happyHandMessageDao;
    }

    public HappyHandNewsDao getHappyHandNewsDao() {
        return happyHandNewsDao;
    }

    public HappyHandNoticeDao getHappyHandNoticeDao() {
        return happyHandNoticeDao;
    }

    public HappyHandJwDao getHappyHandJwDao() {
        return happyHandJwDao;
    }

    public FriendsDao getFriendsDao() {
        return friendsDao;
    }

    public HappyHandGroupDao getHappyHandGroupDao() {
        return happyHandGroupDao;
    }


    public ShoppingCartDao getShoppingCartDao() {
        return shoppingCartDao;
    }


}
