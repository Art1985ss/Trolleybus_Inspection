package com.art.trolleybusinspection.database;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.art.trolleybusinspection.IOperations;
import com.art.trolleybusinspection.database.dao.TrolleyDao;
import com.art.trolleybusinspection.entity.Trolley;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrolleyRepository implements IOperations<Trolley> {
    private static TrolleyRepository instance;
    private final TrolleyDao trolleyDao;
    private final LiveData<List<Trolley>> allTrolleys;

    public static synchronized TrolleyRepository getInstance(Application application) {
        if (instance == null) {
            instance = new TrolleyRepository(application);
        }
        return instance;
    }

    public TrolleyRepository(Application application) {
        TrolleyDatabase trolleyDatabase = TrolleyDatabase.getInstance(application);
        trolleyDao = trolleyDatabase.trolleyDao();
        allTrolleys = trolleyDao.getAll();
    }

    @Override
    public void insert(Trolley trolley) {
        Trolley trolley1 = findById(trolley.getId());
        if (trolley1 == null) {
            new InsertAsyncTask(trolleyDao).execute(trolley);
        } else {
            update(trolley);
        }
    }

    @Override
    public void update(Trolley trolley) {
        new UpdateAsyncTask(trolleyDao).execute(trolley);
    }

    @Override
    public void delete(Trolley trolley) {
        new DeleteAsyncTask(trolleyDao).execute(trolley);
    }

    @Override
    public Trolley findById(int id) {
        FindByIdAsyncTask findByIdAsyncTask = new FindByIdAsyncTask(trolleyDao);
        findByIdAsyncTask.execute(id);
        try {
            return findByIdAsyncTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LiveData<List<Trolley>> getAll() {
        return allTrolleys;
    }

    private static class InsertAsyncTask extends AsyncTask<Trolley, Void, Void> {
        private final TrolleyDao trolleyDao;

        public InsertAsyncTask(TrolleyDao trolleyDao) {
            this.trolleyDao = trolleyDao;
        }

        @Override
        protected Void doInBackground(Trolley... trolleys) {
            trolleyDao.insert(trolleys[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Trolley, Void, Void> {
        private final TrolleyDao trolleyDao;

        public UpdateAsyncTask(TrolleyDao trolleyDao) {
            this.trolleyDao = trolleyDao;
        }

        @Override
        protected Void doInBackground(Trolley... trolleys) {
            trolleyDao.update(trolleys[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Trolley, Void, Void> {
        private final TrolleyDao trolleyDao;

        public DeleteAsyncTask(TrolleyDao trolleyDao) {
            this.trolleyDao = trolleyDao;
        }

        @Override
        protected Void doInBackground(Trolley... trolleys) {
            trolleyDao.delete(trolleys[0]);
            return null;
        }
    }

    private static class FindByIdAsyncTask extends AsyncTask<Integer, Void, Trolley> {
        private final TrolleyDao trolleyDao;

        public FindByIdAsyncTask(TrolleyDao trolleyDao) {
            this.trolleyDao = trolleyDao;
        }

        @Override
        protected Trolley doInBackground(Integer... integers) {
            return trolleyDao.findById(integers[0]);
        }
    }

    public int size() {
        return allTrolleys.getValue().size();
    }
}
