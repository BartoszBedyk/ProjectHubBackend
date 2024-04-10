package com.sensilabs.projecthub.activity.forms;

import com.sensilabs.projecthub.activity.model.ActivityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentDownloadForm implements ActivityForm {
    @NotNull(message = "User ID cannot be null.")
    @Size(min = 36, max = 36, message = "User ID must have 36 characters.")
    private String userId;
    @NotNull(message = "Document ID cannot be null.")
    @Size(min = 36, max = 36, message = "Document ID must have 36 characters.")
    private String documentId;


    private enum Fields {
        USER_ID,
        DOCUMENT_ID,
    }

    @Override
    public ActivityType getType() {
        return ActivityType.DOCUMENT_DOWNLOAD;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(Fields.USER_ID.name(), userId);
        params.put(Fields.DOCUMENT_ID.name(), documentId);
        return params;
    }
}
