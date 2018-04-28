package com.rukiasoft.codewars.di.modules

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.rukiasoft.codewars.preferences.CodeWarsPreferences
import com.rukiasoft.codewars.preferences.CodeWarsPreferencesImpl
import com.rukiasoft.codewars.CodeWarsApplicationBase
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.PersistenceManagerImpl
import com.rukiasoft.codewars.persistence.db.CodeWarsDatabase
import com.rukiasoft.codewars.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Raul on 16/11/2017.
 * Module for the app component
 */
@Module(includes = [(ViewModelModule::class)])
@Singleton
class CodeWarsModule {


    @Provides
    fun providesContext(application: CodeWarsApplicationBase): Context {
        return application.applicationContext
    }

    @Provides
    fun providesPersistenceManager(persistenceManager: PersistenceManagerImpl): PersistenceManager {
        return persistenceManager
    }

    @Provides
    fun providesPreferencesManager(codeWarsPreferences: CodeWarsPreferencesImpl): CodeWarsPreferences {
        return codeWarsPreferences
    }

    @Singleton
    @Provides
    fun provideDb(app: CodeWarsApplicationBase, preferences: CodeWarsPreferences): CodeWarsDatabase {


        return Room.databaseBuilder(app,
                CodeWarsDatabase::class.java, Constants.DATABASE_NAME)
                //.addMigrations()    //no migrations, version 1
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        //if the db is updated, remove info from preferences (as we are recreating all the tables)
                        val oldVersion = preferences.getDbVersion()
                        if (oldVersion != db.version) {
                            //store the new version
                            preferences.setDbVersion(db.version)
                        }
                    }
                })
                .build()

    }


}