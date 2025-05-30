# Sistema de Recomendación de Películas con Neo4j

Este proyecto es un sistema de recomendación de películas implementado en Java utilizando Neo4j como base de datos de grafos.

## Cómo Ejecutar el Proyecto

### Construir y Ejecutar con Gradle
Para construir el proyecto:

```bash
gradle clean build
```

Para ejecutar el proyecto:

```bash
gradle run
```

Nota: Ejecutar el programa vía `gradle run` puede presentar problemas con la entrada interactiva en consola en algunos entornos. Asegúrate de ejecutar el comando en un terminal que soporte entrada estándar para evitar errores.
"java -jar build/libs/Proy2-AED-all-1.0-SNAPSHOT.jar" codigo para que la terminal soporte la entrada


## Notas

- Asegúrate de actualizar las credenciales de la base de datos Neo4j en `mainclass.java` según sea necesario.
- La aplicación usa la versión 5.28.5 del driver Java de Neo4j.
