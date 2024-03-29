package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityParam;
import com.sensilabs.projecthub.activity.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentOpenForm implements ActivityForm{

    private String userId;
    private String documentId;

    private enum Fields{
        USER_ID,
        DOCUMENT_ID,
    }
    @Override
    public ActivityType getType() { return ActivityType.DOCUMENT_OPEN ;}

    @Override
    public List<ActivityParam> getParams() {
        List<ActivityParam> params = new ArrayList<>();
        params.add(new ActivityParam(Fields.USER_ID.name(), userId, ActivityType.DOCUMENT_OPEN));
        params.add(new ActivityParam(Fields.DOCUMENT_ID.name(), documentId, ActivityType.DOCUMENT_OPEN));
        return params;
    }
}
