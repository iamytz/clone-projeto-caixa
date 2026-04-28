package com.clonecaixa.dtos;

import java.util.Date;

public record ClientesHabRecordDto(Date dataEntrada,
                                   String proponente,
                                   String cpf,
                                   String cca,
                                   String numContrato,
                                   float valorFinanciado,
                                   String status,
                                   String modalidade,
                                   String reciprocidade,
                                   String intervenienteQuitante,
                                   Date dataDevGarantia,
                                   String obs,
                                   String nomeVendedor,
                                   String venededorCpfCnpj,
                                   char contaCaixa) {
}
