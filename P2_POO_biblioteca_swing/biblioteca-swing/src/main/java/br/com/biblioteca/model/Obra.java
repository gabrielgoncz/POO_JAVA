package br.com.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Obra catalogada (livro, revista, etc.).
 * AGREGAÇÃO: contém várias {@link Copia} — cópias podem existir
 * independentes em coleções, mas pertencem logicamente à obra.
 */
public class Obra {
    private Long id;
    private String titulo;
    private String autor;
    private String editora;
    private Integer ano;
    private String isbn;
    private String categoria;
    private final List<Copia> copias = new ArrayList<>();

    public Obra() {}

    public Obra(Long id, String titulo, String autor, String editora,
                Integer ano, String isbn, String categoria) {
        this.id = id; this.titulo = titulo; this.autor = autor;
        this.editora = editora; this.ano = ano; this.isbn = isbn; this.categoria = categoria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public List<Copia> getCopias() { return copias; }

    @Override public String toString() {
        return titulo + " — " + autor + (ano != null ? " (" + ano + ")" : "");
    }
}
