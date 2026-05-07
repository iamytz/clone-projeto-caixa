package com.clonecaixa.repositorys;


import com.clonecaixa.dtos.FiltroEsteiraHabRecord;
import com.clonecaixa.entitys.ClientesHab;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClientesHabRepository extends JpaRepository <ClientesHab, Long> {
    ClientesHab findById(int id);
    @Transactional
    ClientesHab findByCpf(String cpf);
    boolean existsByCca(String cca);



    //filtro momentovendedor
    @Query("""
    SELECT m 
    FROM ClientesHab m
    WHERE (:startDate IS NULL OR m.dataEntrada >= :startDate)
      AND (:endDate IS NULL OR m.dataEntrada <= :endDate)
      AND (:statusesEmpty = true OR UPPER(m.status) IN :statuses)
      AND (:contasEmpty = true OR UPPER(m.contaCaixa) IN :contas)
      AND (
            (:devCom = true AND :devSem = true)
            OR (:devCom = true AND m.dataDevGarantia IS NOT NULL)
            OR (:devSem = true AND m.dataDevGarantia IS NULL)   
          )
    ORDER BY m.proponente
""")
    List<ClientesHab> buscarPorFiltro(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("statuses") List<String> statuses,
            @Param("statusesEmpty") boolean statusesEmpty,
            @Param("contas") List<String> contas,
            @Param("contasEmpty") boolean contasEmpty,
            @Param("devCom") boolean devCom,
            @Param("devSem") boolean devSem
    );

    @Query("""
SELECT m 
FROM ClientesHab m 
WHERE m.cca LIKE CONCAT('%',:cca,'%')
AND LOWER(m.modalidade) LIKE LOWER(CONCAT('%',:modalidade,'%'))
AND m.conformidade IN (:conformidades)
AND m.status IN ('negociado','em_negociacao')
""")
    List<ClientesHab> buscaFiltroEsteira(@Param("cca")String cca,@Param("modalidade")String modalidade,@Param("conformidades")List<String>conformidades);


}

