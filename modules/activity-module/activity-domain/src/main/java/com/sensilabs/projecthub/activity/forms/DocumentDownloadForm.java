package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;

import java.util.ArrayList;
import java.util.List;

public class DocumentDownloadForm implements ActivityForm{

    private String userId;
    private String documentId;

    private enum Fields{
        USER_ID,
        DOCUMENT_ID,
    }
    @Override
    public ActivityType getType() { return ActivityType.DOCUMENT_DOWNLOAD ;}

    @Override
    public List<ActivityParam> getParams() {
        List<ActivityParam> params = new ArrayList<>();
        params.add(new ActivityParam(Fields.USER_ID.name(), userId, ActivityType.DOCUMENT_DOWNLOAD));
        params.add(new ActivityParam(Fields.DOCUMENT_ID.name(), documentId, ActivityType.DOCUMENT_DOWNLOAD));
        return params;
    }
}
