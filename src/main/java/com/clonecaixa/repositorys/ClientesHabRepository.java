package com.clonecaixa.repositorys;


import com.clonecaixa.entitys.ClientesHab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesHabRepository extends JpaRepository <ClientesHab, Long> {
    ClientesHab findById(int id);
    ClientesHab findByCpf(String cpf);
}
