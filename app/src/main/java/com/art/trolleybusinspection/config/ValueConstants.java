package com.art.trolleybusinspection.config;

import java.time.format.DateTimeFormatter;

public class ValueConstants {
    public static final String TROLLEY_ID = "trolley_id";

    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
}
