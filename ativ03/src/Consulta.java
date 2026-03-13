class Consulta {
    private String data, hora, medico, paciente, motivo, historico;

    public Consulta() {}

    public Consulta(String data, String hora, String medico, String paciente, String motivo, String historico) {
        this.data = data;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente;
        this.motivo = motivo;
        this.historico = historico;
    }

    // Getters e Setters (Exemplo simplificado, crie todos conforme necessário)
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public void mostrar() {
        System.out.println("Consulta: " + data + " às " + hora + " | Paciente: " + paciente + " | Médico: " + medico);
    }
}
