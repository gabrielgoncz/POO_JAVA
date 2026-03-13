class Receita {
    public String consulta;
    public String data;
    public String descritivo;

    public void prescrever() {
        System.out.println("Receita prescrita.");
    }

    public void consultar() {
        System.out.println("Consultando receita...");
    }

    public void mostrar() {
        System.out.println("Receita:");
        System.out.println("Consulta: " + consulta);
        System.out.println("Data: " + data);
        System.out.println("Descritivo: " + descritivo);
        System.out.println();
    }
}
