package com.jow_app.webservice.response.workspace;

import com.jow_app.model.asana.Workspace;
import com.jow_app.webservice.response.AsanaResponse;

import java.util.List;

/**
 * Created by diego on 4/9/17.
 */

public class GetWorkspacesResponse extends AsanaResponse{

    private List<Workspace> data;

    public GetWorkspacesResponse(List<Workspace> data) {
        this.data = data;
    }

    public List<Workspace> getData() {
        return data;
    }

    public void setData(List<Workspace> data) {
        this.data = data;
    }
}
