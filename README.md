# Entrega final

La aplicación ha sido puesta en un contenedor de Docker para facilitar su puesta en marcha.  
La infraestructura (persistencia y sistema de colas) también han sido puestas en un contenedor de Docker.

## Pre-requisitos

* Tener conexión a internet
* Tener Docker instalado (comprobar que docker-compose funciona)
* Es recomendable tener abiertas 2 terminales distintas para la ejecución de los comandos de puesta en marca
* Comprobar los permisos del fichero gradlew (este fichero se encuentra en la raíz del proyecto, y es lo que se emplea para lanzar la aplicación).
  Es muy probable que la terminal muestre que no tenemos permisos para ejecutarlo, por lo que habrá que cambiarlos mediante `chmod u+x gradlew`
* Tener `curl` instalado
* Tener libres los siguientes puertos:
    * 8081
    * 3306
    * 5672
    * 15672
* Tener libres las siguientes direcciones IP:
    * 172.0.0.2
    * 172.0.0.3
    * 172.0.0.4
  
## Puesta en marcha

1) En una terminal ejecutar `docker-compose -f infrastructure.yml up`.  
   Esto pondrá en marcha la infraestructura necesaria para el proyecto 
2) En otra terminal distinta ejecutar `docker-compose -f app.yml up`.   
   Esto pondrá en marcha el proyecto completo  
   Los diferentes servicios que se ponen en marcha dejan la terminal ocupada con la salida que van mostrando, por eso es
   mejor tener dos terminales abiertas antes de empezar

## Parar el proyecto

Se puede parar de dos formas distintas:

* Primer método: en cada una de las terminales anteriores pulsar la combinación de teclas CTRL+C (las propias terminales mostrarán un
   mensaje que indicará que se están parando los diferentes servicios)
    * Primero parar la terminal que tiene corriendo la aplicación
    * Después parar la terminar que tiene corriendo la infraestructura
   
* Segundo método: en otra terminal distinta a las anteriores, ejecutar los siguientes comandos (en este orden)
    * `docker-compose -f app.yml down`
    * `docker-compose -f infrastructure.yml down`

## Probar la aplicación

Se describe a continuación un flujo de ejecución para probar la aplicación:
1) Crear un usuario `curl -v -X POST "http://172.0.0.4:8081/user/addUser?name=testUser&userId=1"`
2) Añadir un Pokémon como favorito del usuario anterior `curl -v -X PUT -H "id:1" "http://172.0.0.4:8081/user/addFavouritePokemon?id=1"`
3) Consultar la información del Pokémon anteriormente añadido como favorito `curl -v "http://172.0.0.4:8081/pokemon/1"`

## Ejecutar los test

Si se desea ejecutar los test unitarios, ejecutar por separado estos comandos (no importa el orden):

* `./gradlew cleanTest test --tests 'com.ccm.pokemon.pokemon.application.usecases*'`
* `./gradlew cleanTest test --tests 'com.ccm.pokemon.pokemon.domain.services*'`  
* `./gradlew cleanTest test --tests 'com.ccm.user.user.application.usecases*'`  
* `./gradlew cleanTest test --tests 'com.ccm.user.user.domain.services*'`

Si se desea ejecutar los test de aceptación e integración (es imprescindible que la base de datos y RabbitMQ estén
en marcha), ejecutar por separado estos comandos (no importa el orden):

* `./gradlew cleanTest test --tests 'com.ccm.user.user.infrastructure*'`  
* `./gradlew cleanTest test --tests 'com.ccm.pokemon.pokemon.infrastructure*'`  
* `./gradlew cleanTest test --tests 'com.ccm.integration*'`

