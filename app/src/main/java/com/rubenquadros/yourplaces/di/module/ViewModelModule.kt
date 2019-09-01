package com.rubenquadros.yourplaces.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rubenquadros.yourplaces.factory.ViewModelFactory
import com.rubenquadros.yourplaces.viewmodel.PlacesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    internal abstract fun providePlacesViewModel(viewModel: PlacesViewModel): ViewModel
}