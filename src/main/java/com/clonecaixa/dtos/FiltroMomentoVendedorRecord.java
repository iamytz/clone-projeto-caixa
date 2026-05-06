package com.clonecaixa.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDate;
import java.util.List;

public record FiltroMomentoVendedorRecord(@JsonAlias("start_date") LocalDate startDate,
                                          @JsonAlias("end_date") LocalDate endDate,
                                          List<String> statuses,
                                          List<String> contas,
                                          List<String> dev
                                          ) {
}
