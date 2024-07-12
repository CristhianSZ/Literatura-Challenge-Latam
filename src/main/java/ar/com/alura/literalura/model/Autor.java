package ar.com.alura.literalura.model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String autor;

    @Column(name = "ano_nacimiento")
    private Year anoNacimiento;

    @Column(name = "ano_fallecimiento")
    private Year anoFallecimiento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Year getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Year anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Year getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(Year anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Constructores
    public Autor() {}

    public static boolean possuiAno(Year ano) {
        return ano != null && !ano.equals(Year.of(0));
    }

    public Autor(AutorDTO autorDTO) {
        this.autor = autorDTO.autor();
        this.anoNacimiento = autorDTO.anoNacimiento() != null ? Year.of(autorDTO.anoNacimiento()) : null;
        this.anoFallecimiento = autorDTO.anoFallecimiento() != null ? Year.of(autorDTO.anoFallecimiento()) : null;
    }

    public Autor(String autor, Year anoNacimiento, Year anoFallecimiento) {
        this.autor = autor;
        this.anoNacimiento = anoNacimiento;
        this.anoFallecimiento = anoFallecimiento;
    }

    @Override
    public String toString() {
        String anoNacimientoStr = anoNacimiento != null ? anoNacimiento.toString() : "Desconocido";
        String anoFallecimientoStr = anoFallecimiento != null ? anoFallecimiento.toString() : "Desconocido";

        return "Autor: " + autor + " (nacido en " + anoNacimientoStr + ", fallecido en " + anoFallecimientoStr + ")";
    }
}
