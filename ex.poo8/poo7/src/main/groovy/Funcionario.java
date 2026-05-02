public abstract class Funcionario {
    private String nome;
    private String telefone;
    private String senha;

    public Funcionario(String nome, String telefone, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.senha = senha;
    }

    public final boolean acessar(String senhaDigitada) {
        if (this.senha != null && this.senha.equals(senhaDigitada)) {
            this.executarAcaoPosLogin();
            return true;
        }
        System.out.println("Acesso negado para " + this.nome + ".");
        return false;
    }

    protected abstract void executarAcaoPosLogin();
    public abstract void mostrar();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}