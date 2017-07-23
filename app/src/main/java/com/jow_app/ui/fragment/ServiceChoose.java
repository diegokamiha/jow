package com.jow_app.ui.fragment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jow_app.R;
import com.jow_app.utils.FormatUtils;
import com.jow_app.webservice.AirTableFetchManager;
import com.jow_app.webservice.AsanaFetchManager;
import com.jow_app.webservice.request.airtable.expenses.CreateRecordFieldsRequest;
import com.jow_app.webservice.request.airtable.expenses.CreateRecordRequest;
import com.jow_app.webservice.request.task.TaskDataRequest;
import com.jow_app.webservice.request.task.TaskRequest;
import com.jow_app.webservice.response.AsanaResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIButton;

/**
 * Created by henrique on 04/07/17.
 */

public class ServiceChoose {

    public static final String TAG = "CreateTask";

    private String project;

    private String account;

    public void asana(final Context context, Result result, Metadata metadata){



        final Map<String, JsonElement> params = result.getContexts().get(0).getParameters();
        String taskName = "";
        String dueDate = "";

        if (params != null && !params.isEmpty()) {
            Log.i(TAG, "Parameters: ");
            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
                if(entry.getKey().equals("nomeTask")) {
                    taskName = entry.getValue().toString().replaceAll("\"","");
                }else if(entry.getKey().equals("dataEntrega")){
                    dueDate = entry.getValue().toString().replaceAll("\"","");
                }else if(entry.getKey().equals("projeto")){
                    project = entry.getValue().toString().replaceAll("\"","");
                }
            }
        }

        if(metadata != null && metadata.getIntentName() != null && metadata.getIntentName().equals("marcarTask")){
            Long projectId = FormatUtils.getProjectId(project);
            long[] projectIds = new long[1];
            if(projectId != null) {
                projectIds[0] = projectId;
            }
            TaskRequest taskRequest = new TaskRequest(new TaskDataRequest(taskName, Long.parseLong(context.getString(R.string.h_workspace_id)), Long.parseLong(context.getString(R.string.h_user_id)), FormatUtils.transformDate(dueDate), projectIds));
            AsanaFetchManager asanaFetchManager = new AsanaFetchManager(context);
            asanaFetchManager.doRequest(taskRequest, new Callback<AsanaResponse>() {
                @Override
                public void onResponse(Call<AsanaResponse> call, Response<AsanaResponse> response) {
                    Toast.makeText(context, "Checkout your asana", Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailure(Call<AsanaResponse> call, Throwable t) {
                    Log.i("exception", t.getMessage());
                }
            });
        }


    }

    public void airtable(final Context context, Result result, Metadata metadata){


        final Map<String, JsonElement> params = result.getContexts().get(0).getParameters();
        String description = "";
        double value = 0;
        String category = "";

        if (params != null && !params.isEmpty()) {
            Log.i(TAG, "Parameters: ");
            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
                if(entry.getKey().equals("description")) {
                    description = entry.getValue().toString().replaceAll("\"","");
                }else if(entry.getKey().equals("value")) {
                    value = Double.parseDouble(entry.getValue().toString().replaceAll("\"",""));
                }else if(entry.getKey().equals("account")){
                    account = entry.getValue().toString().replaceAll("\"","");
                }
            }
        }

        // Data atual
        Calendar dataAtual = Calendar.getInstance(TimeZone.getDefault());
        dataAtual.add(Calendar.HOUR, -3);


        if(metadata != null && metadata.getIntentName() != null && metadata.getIntentName().equals("marcarGasto")){

            CreateRecordRequest createRecordRequest = new CreateRecordRequest(new CreateRecordFieldsRequest(description, -value, dataAtual.getTime(), account));
            AirTableFetchManager airTableFetchManager = new AirTableFetchManager(context);
            airTableFetchManager.doRequest(createRecordRequest, new Callback<AsanaResponse>() {
                @Override
                public void onResponse(Call<AsanaResponse> call, Response<AsanaResponse> response) {
                    Toast.makeText(context, "Checkout your airtable", Toast.LENGTH_SHORT);
                }

                @Override
                public void onFailure(Call<AsanaResponse> call, Throwable t) {
                    Log.i("exception", t.getMessage());
                }
            });
        }


    }

}
