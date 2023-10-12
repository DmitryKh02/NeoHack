package com.example.prehack.model.enumformodel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Priority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String value;
}
