package com.bordozer.rullity.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RuleActionResult<T, R> {

    private final T object;
    private final Rule<T, R> rule;
    private final R actionResult;
}
