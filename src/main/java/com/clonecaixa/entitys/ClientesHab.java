package com.clonecaixa.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "clientesHab")
public class ClientesHab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataEntrada",nullable = false)
    private Date dataEntrada;

    @Column(name = "proponente",nullable = false)
    private String proponente;

    @Column(name = "cpf",unique = true,nullable = false)
    private String cpf;

    @Column(name = "cca",nullable = false)
    private String cca;

    @Column(name = "numContrato",nullable = false)
    private String numContrato;

    @Column(name = "valorFinanciado",nullable = false)
    private float valorFinanciado;

    @Column(name = "status",nullable = false)
    private String status;

    @Column(name = "modalidade")
    private String modalidade;

    @Column(name = "reciprocidade")
    private String reciprocidade;

    @Column(name = "intervenienteQuitante")
    private String intervenienteQuitante;

    @Column(name = "dataDevGarantia")
    private Date dataDevGarantia;

    @Column(name = "obs")
    private String obs;

    @Column(name = "nomeVendedor")
    private String nomeVendedor;

    @Column(name = "vendedorCpfCnpj")
    private String venededorCpfCnpj;

    @Column(name = "contaCaixa",nullable = false)
    private char contaCaixa;






}
