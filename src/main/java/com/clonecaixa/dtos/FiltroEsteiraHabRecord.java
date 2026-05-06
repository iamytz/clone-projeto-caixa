package com.clonecaixa.dtos;

import java.util.List;

public record FiltroEsteiraHabRecord(String cca,
                                     List<String> statuses,
                                     List<String> conformidade) {
}
