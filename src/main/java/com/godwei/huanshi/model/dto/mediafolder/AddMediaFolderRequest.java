package com.godwei.huanshi.model.dto.mediafolder;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddMediaFolderRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    private String folder_name;

    private Long parent_id;

}