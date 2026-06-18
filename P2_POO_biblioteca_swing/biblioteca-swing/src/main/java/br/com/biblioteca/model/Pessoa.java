package br.com.biblioteca.model;

/**
 * Classe-base abstrata da hierarquia de pessoas (HERANÇA).
 * Define comportamento polimórfico via {@link #getTipoDescricao()}
 * e {@link #getIdentificador()}, sobrescritos pelas subclasses.
 */
public abstract class Pessoa {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    protected Pessoa() {}

    protected Pessoa(Long id, String nome, String cpf, String email, String telefone) {
        this.id = id; this.nome = nome; this.cpf = cpf;
        this.email = email; this.telefone = telefone;
    }

    public abstract String getTipoDescricao();

    public abstract String getIdentificador();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return getTipoDescricao() + " - " + nome + " (" + getIdentificador() + ")";
    }
}
