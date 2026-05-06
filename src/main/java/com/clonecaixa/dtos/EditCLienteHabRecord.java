package com.clonecaixa.dtos;

import java.time.LocalDate;

public record EditCLienteHabRecord(LocalDate dataEntrada,
                                   String proponente,
                                   String cca,
                                   String numContrato,
                                   String valorFinanciado,
                                   String status,
                                   String modalidade,
                                   String reciprocidade,
                                   String intervenienteQuitante,
                                   LocalDate dataDevGarantia,
                                   String obs,
                                   String nomeVendedor,
                                   String vendedorCpfCnpj,
                                   String contaCaixa,
                                   String conformidade) {
}
