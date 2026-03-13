public class Agenda {
    private int codigo;
    private String data;
    private String hora;
    private String medico;
    private String paciente;

    public Agenda() {
    }

    public Agenda(int codigo, String data, String hora, String medico, String paciente) {
        setCodigo(codigo);
        setData(data);
        setHora(hora);
        setMedico(medico);
        setPaciente(paciente);
    }

    public void consultar(){
        //TODO
    }

    public void mostrar() {
        String msg = "Agenda{" +
                "codigo=" + codigo +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", medico='" + medico + '\'' +
                ", paciente='" + paciente + '\'' +
                '}';
        System.out.println(msg);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }
}
