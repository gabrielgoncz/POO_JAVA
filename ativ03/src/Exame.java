class Exame {
    private String consulta, data, descritivo;

    public Exame() {}

    public Exame(String consulta, String data, String descritivo) {
        this.consulta = consulta;
        this.data = data;
        this.descritivo = descritivo;
    }

    public String getDescritivo() { return descritivo; }
    public void setDescritivo(String descritivo) { this.descritivo = descritivo; }

    public void mostrar() {
        System.out.println("Exame solicitado: " + descritivo + " na data " + data);
    }
}