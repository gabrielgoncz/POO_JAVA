package br.com.biblioteca.model;

import java.time.LocalDate;

public class Leitor extends Pessoa {
    private String matricula;
    private LocalDate dataCadastro;
    private boolean ativo;

    public Leitor() {}

    public Leitor(Long id, String nome, String cpf, String email, String telefone,
                  String matricula, LocalDate dataCadastro, boolean ativo) {
        super(id, nome, cpf, email, telefone);
        this.matricula = matricula;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
    }

    @Override public String getTipoDescricao() { return "Leitor"; }
    @Override public String getIdentificador() { return matricula; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
