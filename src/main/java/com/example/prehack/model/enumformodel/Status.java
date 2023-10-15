package com.example.prehack.model.enumformodel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    BLOCKED("Blocked"),
    OPENED("Opened"),
    DISCUSSED("Discussed"),
    ESTIMATION_REQUIRED("Estimation required"),
    DISCUSS_BEFORE_PLANNING("Discuss before planning"),
    IN_PROGRESS("In progress"),
    CODE_REVIEW("Code review"),
    READY_FOR_TESTING("Ready for testing"),
    TESTED("Tested"),
    READY_TO_RELEASE("Ready to release"),
    CLOSED("Closed");

    private final String value;
}
