package bajiomedia.com.accedotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import bajiomedia.com.accedotest.adapter.ScoreAdapter;
import bajiomedia.com.accedotest.model.ScoreDatabaseHelper;
import bajiomedia.com.accedotest.model.ScoreDetail;

/**
 * Created by Raúl on 11/10/2015.
 */
public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        displayScores();
    }

    private void displayScores(){
        ListView scoreList = (ListView)findViewById(R.id.scoresList);
        try {

            ScoreDatabaseHelper dbHelper = OpenHelperManager.getHelper(this, ScoreDatabaseHelper.class);
            Dao<ScoreDetail, Long> scoreDao = dbHelper.getDao();
            QueryBuilder<ScoreDetail,Long> query = scoreDao.queryBuilder();
            query.orderBy("score",false);
            List<ScoreDetail> scores = query.query();
            scoreList.setAdapter(new ScoreAdapter(this,scores));
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
