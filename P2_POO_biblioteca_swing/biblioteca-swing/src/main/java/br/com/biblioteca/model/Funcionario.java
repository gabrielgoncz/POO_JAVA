package br.com.biblioteca.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa {
    private String matricula;
    private String cargo;
    private BigDecimal salario;
    private LocalDate dataAdmissao;

    public Funcionario() {}

    public Funcionario(Long id, String nome, String cpf, String email, String telefone,
                       String matricula, String cargo, BigDecimal salario, LocalDate dataAdmissao) {
        super(id, nome, cpf, email, telefone);
        this.matricula = matricula;
        this.cargo = cargo;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
    }

    @Override public String getTipoDescricao() { return "Funcionário"; }
    @Override public String getIdentificador() { return matricula; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
}
