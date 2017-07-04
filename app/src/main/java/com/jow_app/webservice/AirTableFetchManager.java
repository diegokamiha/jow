package com.jow_app.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jow_app.R;
import com.jow_app.webservice.request.airtable.expenses.CreateRecordRequest;
import com.jow_app.webservice.response.AsanaResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diego on 7/4/17.
 */

public class AirTableFetchManager {

    private AirTableApi airTableApi;
    private Gson gson;

    public AirTableFetchManager(final Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", context.getString(R.string.airtable_token))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.connectTimeout(10, TimeUnit.SECONDS);


        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.airtableServiceRootUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        airTableApi = retrofit.create(AirTableApi.class);
    }

    public void doRequest(CreateRecordRequest request, final Callback<AsanaResponse> callback){
        Call call = airTableApi.addRecord(request);
        if(call != null){
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    callback.onResponse(call, response);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    callback.onFailure(call, t);
                }
            });
        }
    }
}
