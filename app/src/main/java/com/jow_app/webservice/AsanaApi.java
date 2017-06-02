package com.jow_app.webservice;

import com.jow_app.webservice.request.task.TaskRequest;
import com.jow_app.webservice.response.AsanaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by diego on 3/26/17.
 */

public interface AsanaApi {
    String endpoint = "https://app.asana.com/api/1.0";

    @POST("tasks")
    Call<AsanaResponse> createTask(@Body TaskRequest request);

    @GET("workspaces")
    Call<AsanaResponse> getWorkspaces();
}
