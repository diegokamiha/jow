package com.jow_app.ui.fragment;

import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jow_app.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIButton;

/**
 * Created by diego on 3/26/17.
 */

@EFragment(R.layout.fragment_create_task)
public class CreateTaskFragment extends Fragment implements AIButton.AIButtonListener{

    public static final String TAG = "CreateTask";

    @ViewById(R.id.start_listen_button)
    AIButton startListenButton;

    TextToSpeech textToSpeech;



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

                final ServiceChoose serviceChoose = new ServiceChoose();

                if (metadata != null && metadata.getIntentName().equals("marcarTask")){
                    serviceChoose.asana(getActivity(), result, metadata);
                }else if (metadata!= null && metadata.getIntentName().equals("marcarGasto")){
                    serviceChoose.airtable(getActivity(), result, metadata);
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
