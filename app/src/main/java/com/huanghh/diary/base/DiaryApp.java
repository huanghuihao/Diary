package com.huanghh.diary.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.huanghh.diary.R;
import com.huanghh.diary.dao.DaoMaster;
import com.huanghh.diary.dao.DaoSession;
import com.huanghh.diary.utils.SharedPreUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import org.greenrobot.greendao.database.Database;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;

public class DiaryApp extends Application {
    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    //静态单例
    public static DiaryApp instances;
    public static SharedPreUtils mSharedPre;


    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPre = SharedPreUtils.getInstance("diary", this);
        instances = this;
        setDatabase();
        initXunFei();
        initHf();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 设置全局smartRefreshLayout
     * static 代码段可以防止内存泄露
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                layout.setReboundDuration(500);
                return new PhoenixHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setReboundDuration(500);
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setTextSizeTitle(14);
                return footer;
            }
        });
    }


    public static DiaryApp getInstances() {
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "diary-db", null);
        //db = mHelper.getWritableDatabase();
        Database database = mHelper.getEncryptedWritableDb("huanghuihao");

        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        //mDaoMaster = new DaoMaster(db);
        //mDaoSession = mDaoMaster.newSession();
        mDaoSession = new DaoMaster(database).newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private void initXunFei() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b6171de");
    }

    private void initHf() {
        HeConfig.init("HE1808091127431013", "573178b285d54596b622b860c9867647");
    }
}
