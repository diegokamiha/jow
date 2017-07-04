package com.jow_app.webservice;



import com.jow_app.webservice.request.airtable.expenses.CreateRecordRequest;
import com.jow_app.webservice.response.airtable.expenses.CreateRecordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by diego on 7/4/17.
 */

public interface AirTableApi {

    @POST("Julho")
    Call<CreateRecordResponse> addRecord(@Body CreateRecordRequest request);
}
