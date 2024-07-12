package ar.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO(@JsonAlias("name") String autor,
                       @JsonAlias("birth_year") Integer anoNacimiento,
                       @JsonAlias("death_year") Integer anoFallecimiento) {
    @Override
    public String toString() {
        return "Autor: " + autor;
    }
}
