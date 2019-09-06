package com.rubenquadros.yourplaces.di

import com.rubenquadros.yourplaces.base.BaseTest
import com.rubenquadros.yourplaces.di.module.ApplicationModule
import com.rubenquadros.yourplaces.di.module.RepositoryModule
import com.rubenquadros.yourplaces.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestApiModule::class, TestDbModule::class,
            RepositoryModule::class, TestRxJavaModule::class,
            ViewModelModule::class, ApplicationModule::class])
interface TestAppComponent {
    fun inject(baseTest: BaseTest)
}