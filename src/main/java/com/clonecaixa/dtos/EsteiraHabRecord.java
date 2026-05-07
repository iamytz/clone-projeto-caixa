package com.clonecaixa.dtos;

import java.time.LocalDate;

public record EsteiraHabRecord(LocalDate dataEntrada,
                               String proponente,
                               String cpf,
                               String cca,
                               String numContrato,
                               String modalidade,
                               String conformidade,
                               String valorFinanciado) {
}
