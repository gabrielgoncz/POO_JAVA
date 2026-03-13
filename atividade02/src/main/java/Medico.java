public class Medico {
    private int codigo;
    private String nome;
    private String crm;
    private String telefone;
    private String especialidade;
    private String senha;

    public Medico() {
    }

    public Medico(int codigo, String nome, String crm, String telefone, String especialidade, String senha) {
        setCodigo(codigo);
        setNome(nome);
        setCrm(crm);
        setTelefone(telefone);
        setEspecialidade(especialidade);
        setSenha(senha);
    }

    public void acessar(){
        //TODO
    }

    public void mostrar() {
        String msg= "Medico{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", crm='" + crm + '\'' +
                ", telefone='" + telefone + '\'' +
                ", especialidade='" + especialidade + '\'' +
                ", senha='" + senha + '\'' +
                '}';
        System.out.println(msg);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
