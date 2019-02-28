package com.bordozer.rullity.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.CheckForNull;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public final class ActionError {
    @CheckForNull
    private final String error;
    @CheckForNull
    private final String warning;

    public static ActionError error(final String message) {
        return new ActionError(message, null);
    }

    public static ActionError warning(final String message) {
        return new ActionError(null, message);
    }
}
