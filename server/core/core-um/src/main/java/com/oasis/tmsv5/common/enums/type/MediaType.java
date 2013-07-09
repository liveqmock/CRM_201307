package com.oasis.tmsv5.common.enums.type;

import java.util.Arrays;
import java.util.List;

public enum MediaType {
    // DOC, XLS, JPEG;
    IMAGE_TYPE(new String[] { "jpeg", "jpg", "gif", "png", "bmp" }), TEXT_TYPE(new String[] { "txt", "htm", "html" }), DOC_TYPE(
            new String[] { "doc", "xls", "pdf" }), ZIP_TYPE(new String[] { "rar", "zip" });

    List<String> types;

    MediaType(String[] typeArray) {
        types = Arrays.asList(typeArray);
    }

    private boolean contains(String fileType) {
        return this.types.contains(fileType);
    }

    public static MediaType getFileCategory(String fileType) {
        if (fileType == null || fileType.equals("")) {
            return null;
        }

        for (MediaType type : MediaType.values()) {
            if (type.contains(fileType)) {
                return type;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(MediaType.getFileCategory("zip"));
    }

}
