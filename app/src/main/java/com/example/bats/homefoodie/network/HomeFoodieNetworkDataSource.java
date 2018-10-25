package com.example.bats.homefoodie.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.database.userDatabase.UserEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides an API for doing all operations with the server data
 */
public class HomeFoodieNetworkDataSource {

    private static final String LOG_TAG = HomeFoodieNetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static HomeFoodieNetworkDataSource sInstance;
    private Context mContext;

    // LiveData storing the latest downloaded dishes
    private MutableLiveData<DishEntry[]> mDownloadedDishesList;
    private AppExecutors mExecutors;

    public HomeFoodieNetworkDataSource(Context mContext, AppExecutors appExecutors) {
        this.mContext = mContext;
        mExecutors = appExecutors;
        mDownloadedDishesList = new MutableLiveData<DishEntry[]>();
    }


    /**
     * Get the singleton for this class
     */
    public static HomeFoodieNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = new HomeFoodieNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "made new network data source");
            }
        }
        return sInstance;
    }

    public void fetchData(){
        //need to create from zero. this is temporary code
        UserDao userDao = HomeFoodieDatabase.getInstance(mContext).userDao();


        userDao.insertUser(
                new UserEntry( "batu", "thebatu@gmail.com", "road tofame", true, "Dest Inc"));

        //LiveData<List<UserEntry>> entry = userDao.getAllUsers();
        //Log.d("test", "inserted a user" );


        DishDao dishDao= HomeFoodieDatabase.getInstance(mContext).dishDao();
        dishDao.insertDish(
                new DishEntry(1, "my dish", 7));


        LiveData<List<DishEntry>> dishesList =  dishDao.getAllDishes();



        Toast.makeText(mContext, "inserted a dish" + dishesList.getValue(), Toast.LENGTH_LONG).show();

        mDownloadedDishesList.postValue(dishesList);
    }


    public LiveData<DishEntry[]> getLatestDishes() {
        fetchData();
        return mDownloadedDishesList;

    }





}