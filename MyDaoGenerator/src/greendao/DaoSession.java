package greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.People;
import greendao.Animal;

import greendao.PeopleDao;
import greendao.AnimalDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig peopleDaoConfig;
    private final DaoConfig animalDaoConfig;

    private final PeopleDao peopleDao;
    private final AnimalDao animalDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        peopleDaoConfig = daoConfigMap.get(PeopleDao.class).clone();
        peopleDaoConfig.initIdentityScope(type);

        animalDaoConfig = daoConfigMap.get(AnimalDao.class).clone();
        animalDaoConfig.initIdentityScope(type);

        peopleDao = new PeopleDao(peopleDaoConfig, this);
        animalDao = new AnimalDao(animalDaoConfig, this);

        registerDao(People.class, peopleDao);
        registerDao(Animal.class, animalDao);
    }
    
    public void clear() {
        peopleDaoConfig.getIdentityScope().clear();
        animalDaoConfig.getIdentityScope().clear();
    }

    public PeopleDao getPeopleDao() {
        return peopleDao;
    }

    public AnimalDao getAnimalDao() {
        return animalDao;
    }

}
