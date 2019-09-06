package com.rubenquadros.yourplaces.base

import android.app.Application
import com.rubenquadros.yourplaces.di.component.AppComponent
import com.rubenquadros.yourplaces.di.component.DaggerAppComponent
import com.rubenquadros.yourplaces.di.module.*
import com.rubenquadros.yourplaces.utils.ApplicationConstants

open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = initDagger()
    }

    protected open fun initDagger(): AppComponent =
        DaggerAppComponent.builder()
            .apiModule(ApiModule(ApplicationConstants.BASE_URL, this))
            .applicationModule(ApplicationModule(this))
            .dbModule(DbModule(this))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .build()
}