package com.uurobot.yxwlib.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.uurobot.yxwlib.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/16.
 */

public class GreenDaoActivity extends Activity {

        @BindView(R.id.add)
        Button add;

        @BindView(R.id.delete)
        Button delete;

        @BindView(R.id.select)
        Button select;

        @BindView(R.id.modifi)
        Button modifi;

        @BindView(R.id.showResult)
        TextView showResult;

        PeopleDao peopleDao;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_greendao);

                ButterKnife.bind(this);
                DaoSession daoSession = DaoMgr.getInstance().getDaoSession();
                 peopleDao = daoSession.getPeopleDao();

        }

        int id ;
        @OnClick(R.id.add)
        public void add(){

                People people = new People();
                people.setName("yinxiaowei"+id++);
                people.setSex("man");
                peopleDao.insert(people);
        }
        @OnClick(R.id.delete)
        public void delete(){
                List<People> unique = peopleDao.queryBuilder().where(PeopleDao.Properties.Name.eq("yinxiaowei0")).build().list();
                if (unique!=null){
                        for (People p: unique
                             ) {
                                peopleDao.delete(p);
                        }
                }

        }
        @OnClick(R.id.select)
        public void select(){
                List<People> people = peopleDao.loadAll();
                showResult.setText(people.toString());

        }
        @OnClick(R.id.modifi)
        public void modifi(){

        }

}
