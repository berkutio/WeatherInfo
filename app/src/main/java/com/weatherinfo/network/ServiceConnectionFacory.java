package com.weatherinfo.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.weatherinfo.App;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Deprecated
public class ServiceConnectionFacory {

    private static final String CACHE_CONTROL = "Cache-Control";

    private static final int CONNECTION_TIME_OUT_MS = 20;
    private static final int WRITE_TIME_OUT_MS = 20;
    private static final int READ_TIME_OUT_MS = 30;
    private static final int STALE_MINUTES = 3;
    private static final int CACHE_FILE_SIZE = 2*1024*1024;

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(Context context, String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    private static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateAsLongAdapter());
        return gsonBuilder.create();
    }

    private static OkHttpClient getOkHttpClient(){
            return new OkHttpClient.Builder()
                    .addInterceptor(getCacheInterceptor())
                    .connectTimeout(CONNECTION_TIME_OUT_MS, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT_MS, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT_MS, TimeUnit.SECONDS)
                    .cache(getCache())
                    .build();
    }

    private static Interceptor getCacheInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Response response = chain.proceed( chain.request() );
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(STALE_MINUTES, TimeUnit.MINUTES )
                        .build();
                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static Cache getCache(){
        File cacheFile = new File(App.getApp().getApplicationContext().getCacheDir(), "http");
        return new Cache(cacheFile, CACHE_FILE_SIZE);
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
