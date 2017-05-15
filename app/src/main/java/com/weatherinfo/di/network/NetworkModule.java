package com.weatherinfo.di.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by user on 09.05.17.
 */

@Module(includes = CacheModule.class)
public class NetworkModule {

    private static final String CACHE_CONTROL = "Cache-Control";

    private static final int CONNECTION_TIME_OUT_MS = 20;
    private static final int WRITE_TIME_OUT_MS = 20;
    private static final int READ_TIME_OUT_MS = 30;
    private static final int STALE_MINUTES = 3;

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @ScopeNetwork
    public Retrofit getRetrofitInstance(OkHttpClient client, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Provides
    @ScopeNetwork
    public OkHttpClient getOkHttpClient(Interceptor cacheInterceptor, Cache cache){
        return new OkHttpClient.Builder()
                .addInterceptor(cacheInterceptor)
                .connectTimeout(CONNECTION_TIME_OUT_MS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT_MS, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT_MS, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }


    @Provides
    @ScopeNetwork
    public Interceptor getCacheInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(STALE_MINUTES, TimeUnit.MINUTES )
                        .build();
                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }


    @Provides
    @ScopeNetwork
    public Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateAsLongAdapter());
        return gsonBuilder.create();
    }

    private static class DateAsLongAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(Long.valueOf(json.getAsString()));
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

}
