package com.rubenquadros.yourplaces.base

import android.app.Application
import com.rubenquadros.yourplaces.di.component.AppComponent
import com.rubenquadros.yourplaces.di.component.DaggerAppComponent
import com.rubenquadros.yourplaces.di.module.ApiModule
import com.rubenquadros.yourplaces.di.module.DbModule
import com.rubenquadros.yourplaces.di.module.RepositoryModule
import com.rubenquadros.yourplaces.di.module.RxJavaModule
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
            .dbModule(DbModule(this))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .build()
}