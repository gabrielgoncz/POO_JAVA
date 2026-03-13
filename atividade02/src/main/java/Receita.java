public class Receita {
    private int codigo;
    private String consulta;
    private String data;
    private String descritivo;

    public Receita() {
    }

    public Receita(int codigo, String consulta, String data, String descritivo) {
        setCodigo(codigo);
        setConsulta(consulta);
        setData(data);
        setDescritivo(descritivo);
    }

    public void prescrever (){
        //TODO
    }
    public void consultar (){
        //TODO
    }

    public void mostrar() {
        String msg = "Receita{" +
                "codigo=" + codigo +
                ", consulta='" + consulta + '\'' +
                ", data='" + data + '\'' +
                ", descritivo='" + descritivo + '\'' +
                '}';
        System.out.println(msg);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescritivo() {
        return descritivo;
    }

    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }
}
