package com.bordozer.rullity.data.obj;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString
public class Item {
    private final String name;
    private final List<Price> prices;
}
