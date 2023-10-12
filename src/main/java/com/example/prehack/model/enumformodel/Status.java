package com.example.prehack.model.enumformodel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    CREATED ("Created"),
    IN_PROGRESS ("InProgress"),
    COMPLETED ("Completed"),
    EXPIRED ("Expired");

    private final String value;
}
