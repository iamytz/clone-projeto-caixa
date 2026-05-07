package com.clonecaixa.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cca")
public class Cca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "cca")
    public String cca;

    @Column(name = "correspondente")
    public String correspondente;

    @Column(name = "principalContato")
    public String principalContato;

    @Column(name = "numero")
    public String numero;
}
