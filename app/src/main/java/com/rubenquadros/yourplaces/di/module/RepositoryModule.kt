package com.rubenquadros.yourplaces.di.module

import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.remote.api.PlacesApiService
import com.rubenquadros.yourplaces.data.repository.PlacesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providePlacesRepository(placesDAO: PlacesDAO, placesApiService: PlacesApiService): PlacesRepository {
        return PlacesRepository(placesDAO, placesApiService)
    }
}