package com.demo.entity;

import java.util.Set;

public enum Status {
    DONE, IN_PROGRESS, CANCELED;

    public static Set<Status> getAll() {
        return Set.of(DONE, IN_PROGRESS, CANCELED);
    }
}
