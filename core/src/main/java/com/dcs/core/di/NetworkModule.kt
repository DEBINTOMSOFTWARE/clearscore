package com.dcs.core.di

import android.content.Context
import com.dcs.core.BuildConfig
import com.dcs.core.data.network.service.ClearScoreService
import com.dcs.core.data.network.service.ClearScoreServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private fun isTestMode(): Boolean {
        return try {
            Class.forName("com.dcs.clearscore.TestUrlProvider")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    private val baseUrl: String
        get() = if (BuildConfig.DEBUG && isTestMode()) {
            try {
                Class.forName("com.dcs.clearscore.TestUrlProvider").getDeclaredField("baseUrl")
                    .get(null) as String
            } catch (e: Exception) {
                BuildConfig.BASE_URL
            }
        } else {
            BuildConfig.BASE_URL
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http_cache"), 10 * 1024 * 1024)) // 10MB cache

        // Apply certificate pinning conditionally based on environment variable/local property
        if (BuildConfig.ENABLE_CERTIFICATE_PINNING) {
            builder.certificatePinner(
                CertificatePinner.Builder()
                    .add(
                        "android-interview.s3.eu-west-2.amazonaws.com",
                        BuildConfig.CERTIFICATE_PIN_HASH
                    )
                    .build()
            )
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideKtorHttpClient(okHttpClient: OkHttpClient): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                preconfigured = okHttpClient
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }

            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideClearScoreService(httpClient: HttpClient): ClearScoreService {
        return ClearScoreServiceImpl(httpClient)
    }
}