package com.example.prehack.model.enumformodel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    BLOCKED("Blocked"),
    OPENED("Opened"),
    DISCUSSED("Discussed"),
    ESTIMATION_DESTINATION_REPAIRED("Estimation destination repaired"),
    DISCUSS_BEFORE_PLANNING("Discuss before planning"),
    IN_PROGRESS("In progress"),
    CODE_REVIEW("Code review"),
    READY_FOR_TESTING("Ready for testing"),
    TESTING("Testing"),

    READY_TO_REALIZE("Ready to realize"),
    CLOSE("Close");

    private final String value;
}
