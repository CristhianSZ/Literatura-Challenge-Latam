package ar.com.alura.literalura.principal;

import ar.com.alura.literalura.model.Autor;
import ar.com.alura.literalura.model.Libro;
import ar.com.alura.literalura.model.LibroDTO;
import ar.com.alura.literalura.repository.LibroRepository;
import ar.com.alura.literalura.service.ConsumoAPI;
import ar.com.alura.literalura.service.ConvertirDatos;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConvertirDatos convertirDatos;

    private final Scanner lectura = new Scanner(System.in);

    public Principal(LibroRepository libroRepository, ConsumoAPI consumoAPI, ConvertirDatos convertirDatos) {
        this.libroRepository = libroRepository;
        this.consumoAPI = consumoAPI;
        this.convertirDatos = convertirDatos;
    }

    public void executar() {
        boolean running = true;
        while (running) {
            mostrarMenu();
            var opcion = lectura.nextInt();
            lectura.nextLine();

            switch (opcion) {
                case 1 -> buscarLibrosPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarAutoresVivosRefinado();
                case 6 -> listarAutoresPorAnoDeMuerte();
                case 7 -> listarLibrosPorIdioma();
                case 0 -> {
                    System.out.println("Cerrando LiterAlura!");
                    running = false;
                }
                default -> System.out.println("¡Opción inválida!");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("""
            ===========================================================
                                LITERALURA
                   ¡Una aplicación para ti que amas los libros!
                   Elige un número del menú a continuación:
            -----------------------------------------------------------
                                 Menú
                       1- Buscar libros por título
                       2- Listar libros registrados
                       3- Listar autores registrados
                       4- Listar autores vivos en un año determinado
                       5- Listar autores nacidos en un año determinado
                       6- Listar autores por año de su muerte
                       7- Listar libros en un idioma determinado
                       0- Salir
            ===========================================================
            """);
    }

    private void salvarLibros(List<Libro> libros) {
        libros.forEach(libroRepository::save);
    }

    private void buscarLibrosPorTitulo() {
        String baseURL = "https://gutendex.com/books?search=";

        try {
            System.out.println("Digite el título del libro: ");
            String titulo = lectura.nextLine();
            String direccion = baseURL + titulo.replace(" ", "%20");
            System.out.println("URL de la API: " + direccion);

            String jsonResponse = consumoAPI.obtenerDatos(direccion);
            System.out.println("Respuesta de la API: " + jsonResponse);

            if (jsonResponse.isEmpty()) {
                System.out.println("La respuesta de la API está vacía.");
                return;
            }

            // Extrae la lista de libros de la clave "results"
            JsonNode rootNode = convertirDatos.getObjectMapper().readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");

            if (resultsNode.isEmpty()) {
                System.out.println("No se pudo encontrar el libro buscado.");
                return;
            }

            // Convierte los resultados de la API en objetos LibroDTO
            List<LibroDTO> librosDTO = convertirDatos.getObjectMapper()
                    .readerForListOf(LibroDTO.class)
                    .readValue(resultsNode);

            // Elimina los duplicados existentes en la base de datos
            List<Libro> librosExistentes = libroRepository.findByTitulo(titulo);
            if (!librosExistentes.isEmpty()) {
                System.out.println("Eliminando libros duplicados ya existentes en la base de datos...");
                for (Libro libroExistente : librosExistentes) {
                    librosDTO.removeIf(libroDTO -> libroExistente.getTitulo().equals(libroDTO.titulo()));
                }
            }

            // Guarda los nuevos libros en la base de datos
            if (!librosDTO.isEmpty()) {
                System.out.println("Guardando nuevos libros encontrados...");
                List<Libro> nuevosLibros = librosDTO.stream().map(Libro::new).collect(Collectors.toList());
                salvarLibros(nuevosLibros);
                System.out.println("¡Libros guardados con éxito!");
            } else {
                System.out.println("Todos los libros ya están registrados en la base de datos.");
            }

            // Muestra los libros encontrados
            if (!librosDTO.isEmpty()) {
                System.out.println("Libros encontrados:");
                Set<String> titulosMostrados = new HashSet<>(); // Para controlar títulos ya mostrados
                for (LibroDTO libro : librosDTO) {
                    if (!titulosMostrados.contains(libro.titulo())) {
                        System.out.println(libro);
                        titulosMostrados.add(libro.titulo());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("Ningún libro registrado.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("Ningún autor registrado.");
        } else {
            libros.stream()
                    .map(Libro::getAutor)
                    .distinct()
                    .forEach(autor -> System.out.println(autor.getAutor()));
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Digite el año: ");
        Integer ano = lectura.nextInt();
        lectura.nextLine();

        Year year = Year.of(ano);

        List<Autor> autores = libroRepository.findAutoresVivos(year);
        if (autores.isEmpty()) {
            System.out.println("Ningún autor vivo encontrado.");
        } else {
            System.out.println("Lista de autores vivos en el año " + ano + ":\n");

            autores.forEach(autor -> {
                if(Autor.possuiAno(autor.getAnoNacimiento()) && Autor.possuiAno(autor.getAnoFallecimiento())){
                    String nombreAutor = autor.getAutor();
                    String anoNacimiento = autor.getAnoNacimiento().toString();
                    String anoFallecimiento = autor.getAnoFallecimiento().toString();
                    System.out.println(nombreAutor + " (" + anoNacimiento + " - " + anoFallecimiento + ")");
                }
            });
        }
    }

    private void listarAutoresVivosRefinado() {
        System.out.println("Digite el año: ");
        Integer ano = lectura.nextInt();
        lectura.nextLine();

        Year year = Year.of(ano);

        List<Autor> autores = libroRepository.findAutoresVivosRefinado(year);
        if (autores.isEmpty()) {
            System.out.println("Ningún autor vivo encontrado.");
        } else {
            System.out.println("Lista de autores nacidos en el año " + ano + ":\n");

            autores.forEach(autor -> {
                if(Autor.possuiAno(autor.getAnoNacimiento()) && Autor.possuiAno(autor.getAnoFallecimiento())){
                    String nombreAutor = autor.getAutor();
                    String anoNacimiento = autor.getAnoNacimiento().toString();
                    String anoFallecimiento = autor.getAnoFallecimiento().toString();
                    System.out.println(nombreAutor + " (" + anoNacimiento + " - " + anoFallecimiento + ")");
                }
            });
        }
    }

    private void listarAutoresPorAnoDeMuerte() {
        System.out.println("Digite el año: ");
        Integer ano = lectura.nextInt();
        lectura.nextLine();

        Year year = Year.of(ano);

        List<Autor> autores = libroRepository.findAutoresPorAnoDeMuerte(year);
        if (autores.isEmpty()) {
            System.out.println("Ningún autor encontrado.");
        } else {
            System.out.println("Lista de autores que murieron en el año " + ano + ":\n");

            autores.forEach(autor -> {
                if (Autor.possuiAno(autor.getAnoNacimiento()) && Autor.possuiAno(autor.getAnoFallecimiento())){
                    String nombreAutor = autor.getAutor();
                    String anoNacimiento = autor.getAnoNacimiento().toString();
                    String anoFallecimiento = autor.getAnoFallecimiento().toString();
                    System.out.println(nombreAutor + " (" + anoNacimiento + " - " + anoFallecimiento + ")");
                }
            });
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            Digite el idioma deseado:
            Inglés (en)
            Portugués (pt)
            Español (es)
            Francés (fr)
            Alemán (de)
            """);
        String idioma = lectura.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("Ningún libro encontrado en el idioma especificado.");
        } else {
            libros.forEach(libro -> {
                String titulo = libro.getTitulo();
                String autor = libro.getAutor().getAutor();
                String idiomaLibro = libro.getIdioma();

                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autor);
                System.out.println("Idioma: " + idiomaLibro);
                System.out.println("----------------------------------------");
            });
        }
    }
}
