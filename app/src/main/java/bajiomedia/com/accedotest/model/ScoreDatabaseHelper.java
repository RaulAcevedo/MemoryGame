package bajiomedia.com.accedotest.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import bajiomedia.com.accedotest.R;

/**
 * Created by Raúl on 11/10/2015.
 */
public class ScoreDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "scores";
    private static final int DATABASE_VERSION = 1;
    private Dao<ScoreDetail,Long> dao;


    public ScoreDatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, ScoreDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ScoreDetail.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<ScoreDetail, Long> getDao() throws SQLException {
        if(dao == null) {
            dao = getDao(ScoreDetail.class);
        }
        return dao;
    }
}
