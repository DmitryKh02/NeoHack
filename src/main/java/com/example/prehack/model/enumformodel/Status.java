package com.example.prehack.model.enumformodel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    BLOCKED,
    OPENED,
    DISCUSSED,
    ESTIMATION_DESTINATION_REPAIRED,
    DISCUSS_BEFORE_PLANNING,
    IN_PROGRESS,
    CODE_REVIEW,
    READY_FOR_TESTING,
    TESTING,
    READY_TO_REALIZE,
    CLOSE
}
