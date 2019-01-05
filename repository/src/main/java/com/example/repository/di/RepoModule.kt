package com.example.repository.di

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.core.di.scopes.RepoScope
import com.example.core.interfaces.*
import com.example.repository.BuildConfig
import com.example.repository.ImageLoaderImpl
import com.example.repository.datasource.api.*
import com.example.repository.datasource.db.DB_NAME
import com.example.repository.datasource.db.DbDataSource
import com.example.repository.datasource.db.WeatherDao
import com.example.repository.datasource.db.WeatherDb
import com.example.repository.model.db.CITY_ID_COLUMN
import com.example.repository.model.db.CITY_NAME_COLUMN
import com.example.repository.model.db.CITY_TABLE
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val WEATHER_ICON_URL = "http://openweathermap.org/img/w/"
private const val HTTP_LOG_TAG = "soramitsu_http"
private const val NETWORK_TIMEOUT = 6 //seconds
private const val WEATHER_RETROFIT = "weather"
private const val TRANSLATOR_RETROFIT = "translator"

private const val TRANSLATION_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/"

@Module
class RepoModule {

    @Provides
    @RepoScope
    fun weatherDb(appContext: Context): WeatherDb {
        return Room
            .databaseBuilder(appContext, WeatherDb::class.java, DB_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val moscow = ContentValues()
                    moscow.put(CITY_ID_COLUMN, 524901)
                    moscow.put(CITY_NAME_COLUMN, "Moscow")

                    val petersburg = ContentValues()
                    petersburg.put(CITY_ID_COLUMN, 498817)
                    petersburg.put(CITY_NAME_COLUMN, "Saint Petersburg")

                    db.insert(CITY_TABLE, SQLiteDatabase.CONFLICT_REPLACE, moscow)
                    db.insert(CITY_TABLE, SQLiteDatabase.CONFLICT_REPLACE, petersburg)
                }
            })
            .build()
    }

    @Provides
    @RepoScope
    @Named(WEATHER_RETROFIT)
    fun weatherRetrofit(appContext: Context, logger: Logger): Retrofit =
        createRetrofit(appContext, logger, WEATHER_RETROFIT)

    @Provides
    @RepoScope
    @Named(TRANSLATOR_RETROFIT)
    fun translatorRetrofit(appContext: Context, logger: Logger): Retrofit =
        createRetrofit(appContext, logger, TRANSLATOR_RETROFIT)

    @Provides
    @RepoScope
    fun weatherApi(@Named(WEATHER_RETROFIT) retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @RepoScope
    fun translatorApi(@Named(TRANSLATOR_RETROFIT) retrofit: Retrofit): TranslatorApi =
        retrofit.create(TranslatorApi::class.java)

    @Provides
    @RepoScope
    fun apiDataSource(weatherApi: WeatherApi, context: Context) = ApiDataSource(weatherApi, context)

    @Provides
    @RepoScope
    fun weatherDao(weatherDb: WeatherDb) = weatherDb.weatherDao()

    @Provides
    @RepoScope
    fun dbDataSource(weatherDao: WeatherDao) = DbDataSource(weatherDao)

    @Provides
    @RepoScope
    fun weatherRepo(
        apiDataSource: ApiDataSource,
        dbDataSource: DbDataSource,
        logger: Logger
    ): WeatherRepository =
        com.example.repository.WeatherRepository(
            apiDataSource,
            dbDataSource,
            CompositeDisposable(),
            ApiErrors(),
            logger
        )

    private fun createRetrofit(
        appContext: Context,
        logger: Logger,
        type: String
    ): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()

        val connectivityInterceptor = ConnectivityInterceptor(appContext)

        okHttpBuilder.addInterceptor(connectivityInterceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                logger.logDebug(message, HTTP_LOG_TAG)
            }
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(loggingInterceptor)
        }

        okHttpBuilder.connectTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val baseUrl = if (type == WEATHER_RETROFIT) {
            WEATHER_BASE_URL
        } else {
            TRANSLATION_BASE_URL
        }

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @RepoScope
    fun provideImageLoader(): ImageLoader {
        return ImageLoaderImpl()
    }

    @Provides
    @RepoScope
    fun provideTranslator(translatorApi: TranslatorApi): Translator = TranslatorImpl(translatorApi)
}