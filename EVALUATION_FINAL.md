## Hace todos los puntos pedidos (40%)

#### El nombre del repositorio es el correcto (mdas-web-${nombre}_${apellido})

OK

#### Permite obtener los detalles de un pokemon vía endpoint (datos + número de veces que se ha marcado como favorito)

OK

#### Actualiza el contador de favoritos vía eventos

OK

#### ¿Se controlan las excepciones de dominio? Y si se lanza una excepción desde el dominio, ¿se traduce en infraestructura a un código HTTP?

OK

#### Tests unitarios

OK

#### Tests de aceptación

OK

#### Tests de integración

OK

**Puntuación: 40/40**

## Se aplican conceptos explicados (50%)

#### Separación correcta de capas (application, domain, infrastructure + BC/module/layer)

- La responsabilidad de crear el evento y añadirlo la tiene el agregado, no el caso de uso. El caso de uso tiene la
  responsabilidad de publicar el evento.

#### Aggregates + VOs

OK

#### No se trabajan con tipos primitivos en dominio

OK

#### Hay servicios de dominio

OK

#### Hay use cases en aplicación reutilizables

OK

#### Se aplica el patrón repositorio

OK

#### Se usan subscribers

OK. Aunque la solución actual no es muy escalable, ya que está orientada a que haya solo un evento en todo el sistema,
cuando una arquitectura basada en eventos, escucha y lanza muchos eventos. Siguiendo el ejemplo comentado en clase, la
anotación `RabbitListener` hubiera sido todo más legible y sencillo de implementar ;-)

#### Se lanzan eventos de dominio

OK

#### Se utilizan object mothers

- Están creados en el package main cuando es algo de uso exclusivo para tests.
- No se utilizan mucho, ya que hay muchos valores creados directamente con los objetos de dominio en sí.

**Puntuación: 45/50**

## Facilidad setup + README (10%)

#### El README contiene al menos los apartados "cómo ejecutar la aplicación", "cómo usar la aplicación"

OK

#### Es sencillo seguir el apartado "cómo ejecutar la aplicación"

OK, aunque te has complicado más de lo necesario. Haciendo las peticiones con `localhost:8081`, a mi me funcionaba, pero
si las hacía con `http://172.0.0.4:8081` no me funcionaba.

**Puntuación: 8/10**

## Extras

- Añadido un docker-compose con todos los servicios necesarios +5
- Añadida una BBDD real +5
- Añadida una cache que permite no andar pidiendo los datos de un pokemon a la poke-api continuamente +5

**Puntuación: +15**

## Observaciones

- Los eventos de dominio suelen llamarse `objectActionEvent`, te ha faltado la acción. En vez
  de `NewFavouritePokemonEvent`, debería ser `FavouritePokemonAddedEvent` or `FavouritePokemonCreatedEvent`. Para que
  nos informen de que es lo que ha sucedido en el sistema.

- Los `LOGGER.info` entiendo que los hayas usado para probar la aplicación, pero es un código que no debería
  entregarse (al igual que el código comentado). No aporta valor, pero si ruido.

- Los eventos también suelen tener object mothers

**PUNTUACIÓN FINAL: 108/100**
