

---

# Literalura


Literalura es una aplicación Java/Spring Boot para amantes de los libros. Esta aplicación permite buscar libros, listar libros registrados, listar autores y muchas otras funcionalidades relacionadas con la lectura y organización de libros.

## Funcionalidades

1. **Buscar libros por título**: Consulta la API Gutendex para buscar libros por título.
2. **Listar libros registrados**: Muestra todos los libros registrados en la base de datos.
3. **Listar autores registrados**: Muestra todos los autores de los libros registrados.
4. **Listar autores vivos en un determinado año**: Lista autores que estaban vivos en un año específico.
5. **Listar autores nacidos en determinado año**: Lista autores que nacieron en un año específico.
6. **Listar autores por año de su muerte**: Lista autores que murieron en un año específico.
7. **Listar libros en un determinado idioma**: Lista libros registrados en la base de datos en un idioma específico.
8. **Cerrar la aplicación**: Termina el programa.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 2.7**
- **Hibernate**
- **PostgreSQL**
- **Gutendex API**
- **Maven**

## Configuración del Proyecto

### Prerrequisitos

- Java 17 o superior
- Maven
- PostgreSQL



1. Configura la base de datos en el archivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=tu usuario o postgre(por defecto)
   spring.datasource.password=tu contraseña 
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

2. Ejecuta el proyecto:

## Estructura del Proyecto

- `ar.com.alura.literalura`: Paquete principal del proyecto.
   - `principal`: Contiene la clase `Principal`, que gestiona la ejecución de la aplicación.
   - `model`: Contiene las clases de modelo (`Libro`, `Autor`, `LibroDTO`, `AutorDTO`).
   - `repository`: Contiene las interfaces de repositorio Spring Data JPA.
   - `service`: Contiene las clases de servicio (`ConsumoAPI`, `ConverteDados`).

## Uso

Al iniciar la aplicación, se mostrará el menú principal con las opciones disponibles. Solo sigue las instrucciones en pantalla para navegar por las funcionalidades.

### Ejemplo de Uso

1. **Buscar libros por título**:
   - Ingresa `1` y presiona Enter.
   - Introduce el título del libro que deseas buscar.
   - La aplicación consultará la API Gutendex y mostrará los resultados encontrados.

2. **Listar libros registrados**:
   - Ingresa `2` y presiona Enter.
   - La aplicación listará todos los libros registrados en la base de datos.

3. **Listar autores registrados**:
   - Ingresa `3` y presiona Enter.
   - La aplicación listará todos los autores de los libros registrados.

4. **Listar autores vivos en un determinado año**:
   - Ingresa `4` y presiona Enter.
   - Ingresa el año deseado.
   - La aplicación listará los autores que estaban vivos ese año.

5. **Listar autores nacidos en determinado año**:
   - Ingresa `5` y presiona Enter.
   - Ingresa el año deseado.
   - La aplicación listará los autores que nacieron ese año.

6. **Listar autores por año de su muerte**:
   - Ingresa `6` y presiona Enter.
   - Ingresa el año deseado.
   - La aplicación listará los autores que murieron ese año.

7. **Listar libros en un determinado idioma**:
   - Ingresa `7` y presiona Enter.
   - Ingresa el código del idioma deseado (por ejemplo, `en` para inglés, `pt` para portugués).
   - La aplicación listará todos los libros registrados en la base de datos en ese idioma.

8. **Cerrar la aplicación**:
   - Ingresa `0` y presiona Enter.
   - La aplicación se cerrará.

## Imagenes de funcionamiento
![Imagen1](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen1.png)
![Imagen2](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen2.png)
![Imagen3](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen3.png)
![Imagen4](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen4.png)
![Imagen5](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen5.png)
![Imagen6](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen6.png)
![Imagen7](https://github.com/CristhianSZ/Literatura-Challenge-Latam/blob/main/imagenes/imagen7.png)
