package com.github.hurrcook.global.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Unit {
    ML,
    G,
    EA;

    @JsonCreator
    public static Unit fromString(String value) {
        return Unit.valueOf(value.toUpperCase());
    }
}
