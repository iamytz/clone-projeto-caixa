package com.clonecaixa.dtos;


import java.time.LocalDate;

public record MomentoVendedorRecord(LocalDate dataEntrada,
                                    String proponente,
                                    String cpf,
                                    String valorFinanciado,
                                    String status,
                                    String contaCaixa,
                                    String numContrato,
                                    LocalDate dataDevGarantia) {
}
