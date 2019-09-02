package com.rubenquadros.yourplaces.di.component

import com.rubenquadros.yourplaces.base.BaseActivity
import com.rubenquadros.yourplaces.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class,DbModule::class,
            GoogleMapsModule::class,RepositoryModule::class,
            RxJavaModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}