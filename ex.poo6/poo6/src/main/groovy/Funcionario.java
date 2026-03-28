public abstract class Funcionario {
    protected String nome;
    protected String telefone;
    protected String senha;

    public Funcionario(String nome, String telefone, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.senha = senha;
    }

    public boolean acessar(String senhaDigitada) {
        if (this.senha != null && this.senha.equals(senhaDigitada)) {
            System.out.println("Acesso permitido para " + this.nome + ".");
            return true;
        }
        System.out.println("Acesso negado.");
        return false;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setSenha(String senha) { this.senha = senha; }
    public abstract void mostrar();
}