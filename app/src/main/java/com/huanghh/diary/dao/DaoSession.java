package com.huanghh.diary.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.huanghh.diary.mvp.model.DiaryItem;

import com.huanghh.diary.dao.DiaryItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig diaryItemDaoConfig;

    private final DiaryItemDao diaryItemDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        diaryItemDaoConfig = daoConfigMap.get(DiaryItemDao.class).clone();
        diaryItemDaoConfig.initIdentityScope(type);

        diaryItemDao = new DiaryItemDao(diaryItemDaoConfig, this);

        registerDao(DiaryItem.class, diaryItemDao);
    }
    
    public void clear() {
        diaryItemDaoConfig.clearIdentityScope();
    }

    public DiaryItemDao getDiaryItemDao() {
        return diaryItemDao;
    }

}
