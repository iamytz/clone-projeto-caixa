package com.clonecaixa.repositorys;

import com.clonecaixa.entitys.Cca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CcaRepository extends JpaRepository<Cca, Integer> {
    Cca findByCca(String cca);
    boolean existsByCca(String cca);
}
