package com.example.bats.homefoodie.utilities;


import android.content.Context;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.HomefoodieRepository;
import com.example.bats.homefoodie.database.HomeFoodieDatabase;

/**
 * Provides static methods to inject the various classes needed for HomeFoodie
 */
public class InjectorUtils {

    public static HomefoodieRepository provideRepository(Context context) {

        HomeFoodieDatabase database = HomeFoodieDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        HomeFoodieNetworkDataSource networkDataSource =
                HomeFoodieNetworkDataSource.getInstance(context.getApplicationContext(), executors);

        return HomefoodieRepository.getsInstance(database.dishDao(), database.userDao(),
                networkDataSource, executors);
    }

    public static HomeFoodieNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return HomeFoodieNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }




}