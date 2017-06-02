package com.jow_app.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jow_app.R;
import com.jow_app.webservice.request.task.TaskRequest;
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
 * Created by diego on 3/26/17.
 */

public class AsanaFetchManager {

    private AsanaApi asanaApi;
    private Gson gson;
    private Context mContext;


    public AsanaFetchManager(final Context context) {
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
                        .header("Authorization", context.getString(R.string.personal_token_h))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.connectTimeout(10, TimeUnit.SECONDS);

        mContext = context;


        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.asanaServiceRootUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        asanaApi = retrofit.create(AsanaApi.class);
    }

    public void doRequest(TaskRequest request, final Callback<AsanaResponse> callback){
        Call call = asanaApi.createTask(request);
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
