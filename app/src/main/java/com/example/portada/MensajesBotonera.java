package com.example.portada;

import java.util.Map;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MensajesBotonera {
    public static final Map<String, Integer> mapaBotones = Stream.of(
            new SimpleEntry<>("A", 1),
            new SimpleEntry<>("C", 2),
            new SimpleEntry<>("E", 3),
            new SimpleEntry<>("G", 4)).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
}
