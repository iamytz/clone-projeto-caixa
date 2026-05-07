package com.clonecaixa.repositorys;

import com.clonecaixa.entitys.Cca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CcaRepository extends JpaRepository<Cca, Integer> {
    Cca findByCca(String cca);
    Cca findById(int id);
    List<Cca> findAll();
    void deleteById(int id);

    boolean existsByCca(String cca);
}
