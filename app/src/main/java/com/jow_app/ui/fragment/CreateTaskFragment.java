package com.jow_app.ui.fragment;

import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jow_app.R;
import com.jow_app.utils.FormatUtils;
import com.jow_app.webservice.AsanaFetchManager;
import com.jow_app.webservice.request.task.TaskDataRequest;
import com.jow_app.webservice.request.task.TaskRequest;
import com.jow_app.webservice.response.AsanaResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diego on 3/26/17.
 */

@EFragment(R.layout.fragment_create_task)
public class CreateTaskFragment extends Fragment implements AIButton.AIButtonListener{

    public static final String TAG = "CreateTask";

    @ViewById(R.id.start_listen_button)
    AIButton startListenButton;

    TextToSpeech textToSpeech;

    private String project;

    AIConfiguration config;

    public static CreateTaskFragment newInstance(){
        return CreateTaskFragment_.builder().build();
    }

    @AfterViews
    public void afterViews(){
        config =  new AIConfiguration(getString(R.string.ai_config_key),
                AIConfiguration.SupportedLanguages.PortugueseBrazil,
                AIConfiguration.RecognitionEngine.System);

        startListenButton.initialize(config);
        startListenButton.setResultsListener(this);

        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(new Locale("pt"));
                }
            }
        });

        String[] perms = {"android.permission.RECORD_AUDIO"};

        int permsRequestCode = 200;

        requestPermissions(perms, permsRequestCode);
    }


    @Override
    public void onResult(final AIResponse response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "onResult");

                Log.i(TAG, "Received success response");

                // this is example how to get different parts of result object
                final Status status = response.getStatus();
                Log.i(TAG, "Status code: " + status.getCode());
                Log.i(TAG, "Status type: " + status.getErrorType());

                final Result result = response.getResult();

                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());

                Log.i(TAG, "Action: " + result.getAction());
                final String speech = result.getFulfillment().getSpeech();
                Log.i(TAG, "Speech: " + speech);
                textToSpeech.speak(speech, TextToSpeech.QUEUE_FLUSH, null);

                final Metadata metadata = result.getMetadata();
                if (metadata != null) {
                    Log.i(TAG, "Intent id: " + metadata.getIntentId());
                    Log.i(TAG, "Intent name: " + metadata.getIntentName());
                }

                final HashMap<String, JsonElement> params = result.getParameters();
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
                    TaskRequest taskRequest = new TaskRequest(new TaskDataRequest(taskName, Long.parseLong(getString(R.string.h_workspace_id)), Long.parseLong(getString(R.string.h_user_id)), FormatUtils.transformDate(dueDate), projectIds));
                    AsanaFetchManager asanaFetchManager = new AsanaFetchManager(getContext());
                    asanaFetchManager.doRequest(taskRequest, new Callback<AsanaResponse>() {
                        @Override
                        public void onResponse(Call<AsanaResponse> call, Response<AsanaResponse> response) {
                            Toast.makeText(getContext(), "Checkout your asana", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Call<AsanaResponse> call, Throwable t) {
                            Log.i("exception", t.getMessage());
                        }
                    });
                }
            }

        });
    }

    @Override
    public void onError(AIError error) {
        Log.i(TAG, error.getMessage());
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){

            case 200:

                boolean audioAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;

                break;

        }
    }
}
