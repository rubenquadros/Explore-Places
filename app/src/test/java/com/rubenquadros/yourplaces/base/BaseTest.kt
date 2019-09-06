package com.rubenquadros.yourplaces.base

import androidx.fragment.app.FragmentActivity
import com.rubenquadros.yourplaces.data.local.database.PlacesDatabase
import com.rubenquadros.yourplaces.di.*
import com.rubenquadros.yourplaces.di.module.ApplicationModule
import com.rubenquadros.yourplaces.factory.ViewModelFactory
import com.rubenquadros.yourplaces.utils.ApplicationConstants
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.robolectric.Robolectric
import java.io.File
import javax.inject.Inject

@Suppress("DEPRECATION")
abstract class BaseTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var testAppComponent: TestAppComponent

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var placesDatabase: PlacesDatabase

    @Before
    open fun setup() {
        this.configureMockServer()
        this.configureDi()
    }

    @After
    fun tearDown() {
        this.stopMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun configureMockServer() {
        if(isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if(isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    open fun mockResponse(fileName: String, responseCode: Int) = mockServer.enqueue(MockResponse()
        .setResponseCode(responseCode)
        .setBody(getJson(fileName)))

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    open fun configureDi() {
        this.testAppComponent = DaggerTestAppComponent.builder()
            .testApiModule(TestApiModule(if (isMockServerEnabled()) mockServer.url("/").toString() else ApplicationConstants.BASE_URL))
            .applicationModule(ApplicationModule(Robolectric.setupActivity(FragmentActivity::class.java).application))
            .testDbModule(TestDbModule(Robolectric.setupActivity(FragmentActivity::class.java).application))
            .testRxJavaModule(TestRxJavaModule())
            .build()
        this.testAppComponent.inject(this)
    }
}