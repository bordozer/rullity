package com.bordozer.rullity.data.obj;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"date"})
@ToString
public class Price {
    private final LocalDate date;
    private final Double price;
}
