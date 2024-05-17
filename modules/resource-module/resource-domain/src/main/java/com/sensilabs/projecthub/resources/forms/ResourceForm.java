package com.sensilabs.projecthub.resources.forms;

import com.sensilabs.projecthub.resources.model.ResourceType;

public interface ResourceForm {
    String getName();
    String getDescription();
    String getValue();
    ResourceType getType();

}
