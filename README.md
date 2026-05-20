# Parcial Backend de Aplicaciones 2026

### Introducción

El contexto de este parcial es el de generación de liquidaciones de tarjetas de crédito. Se entrega, junto a
este enunciado, los scripts de creación de una base de datos reducida para este fin, como así también un
script con datos iniciales. Estos archivos son **schema.sql** (estructura) y **data.sql** (datos).

El parcial consta de dos partes: una parte de preparación, que los alumnos desarrollan libremente en sus
casas (con las asistencias que deseen) y una parte presencial en la que deben desarrollar en la Universidad, de
manera presencial y con acceso únicamente a las Fichas de Clase.

### Estructura de la base de datos

La base de datos se compone de las siguientes tablas:

**COTIZACIONES**

Contiene una fila por cada tasa de cambio entre el Peso Argentino y otra moneda. Por ejemplo, una fila con
Moneda = USD y TASA_CAMBIO = 1.400 significa que un USD vale ARS 1.400.

**TARJETAS**

Contiene una fila por cada tarjeta de crédito.

Columnas

ID: Auto incremental con el identificador
NUMERO: Número de tarjeta como figura en el plástico
TITULAR: Titular de la tarjeta como figura en el plástico
LIMITE_CREDITO: El límite de crédito de la tarjeta (Cúanto tiene disponible para consumos por mes)

**CONSUMOS**

Contiene una fila por cada consumo realizado. Este consumo está asociado a una tarjeta de crédito. El
consumo está asociado.

Columnas

ID: Auto incremental con el identificador
ID_TARJETA: El ID (no el número) de la tarjeta de crédito asociada al consumo
MONTO: El monto del consumo
DIA: El día del mes cuando se realizó el consumo
MES: El mes de realización del consumo
ANIO: El año de realización del consumo
RUBRO: El rubro al que pertenece el consumo (si es supermercado, indumentaria, etc.)
MONEDA: El código de moneda del consumo (ARS, USD, EUR, etc.). Para fines del parcial, no importa el
País de consumo, solamente la moneda.

**LIQUIDACIONES**

Contiene una fila por cada liquidación realizada. Una liquidación es el resumen de lo que debe pagar el dueño
de la tarjeta. Nosotros vamos a considerar mes completo (del primer al último día de cada mes).

### Preparación

Para esta etapa, los alumnos deben generar un proyecto Maven con las dependencias necesarias para
trabajar con JPA (y lombok si así lo desean), pero no debe ser un proyecto SpringBoot. Pueden tomar como
base Java 17, Hibernate 6.4.4 y JUnit (que debe estar presente) 6.

Para esta etapa los alumnos deben preparar:

* Todo el código y configuraciones necesarias para poder trabajar con las entidades: **Tarjeta,** **Consumo,**
**Moneda** **y** **Liquidacion**. Este código debe estar previsto para trabajar de acuerdo a la especificación
JPA, mapeando correctamente entidades y sus relaciones.
* Todas las configuraciones necesarias para que se ejecuten, al inicio del programa, la creación de la
base de datos y la carga de datos iniciales.
* El código de acceso a datos que permita realizar la obtención de todos los objetos y las operaciones
CRUD sobre todas las entidades, además de las siguientes consultas:
Consumos de una tarjeta que correspondan a un año y un mes específico.
Las tarjetas que no tienen liquidación para un año/mes específico.
La liquidación de una tarjeta para un año/mes específico, buscando por número de tarjeta, año y
mes.
Es importante que la base de datos se recree en cada inicio de la aplicación. Cada vez que la aplicación, la
base de datos debe quedar con la estructura creada (de acuerdo al archivo **schema.sql**) y **únicamente** con los
datos que están en el script adjunto **data.sql**. En la etapa presencial, **data.sql** podría reemplazarse por uno
distinto y sus datos deberían ser importados.
Se puede trabajar con la bsae de datos en memoria o en un archivo, siempre y cuando se cumpla lo ya
mencionado de que, en cada arranque de la aplicación, la base de datos se recree.
Una manera de lograr esto es mediante las siguiente propiedades en **persistence.xml** (y los archivos .sql en
la carpeta **resources**):

```xml
<property name="jakarta.persistence.schema-
generation.database.action" value="drop-and-create"/>
<property name="jakarta.persistence.schema-generation.create-
source" value="script"/>
<property name="jakarta.persistence.schema-generation.create-
script-source" value="schema.sql"/>
<property name="jakarta.persistence.sql-load-script-source"
value="data.sql"/>
```

No es obligatorio realizar la creación de la estructura y la carga de datos de esta manera, los alumnos pueden
optar por el mecanismo que consideren más conveniente.

El desarrollo que realicen como parte de esta etapa de preparación deberán tenerlo disponible en el
momento de la etapa presencial. Pueden subirlo a su repositorio personal de la facultad o tenerlo disponible
de otra forma, pero **deben estar preparados para desarrollar el examen en las computadoras de la
Universidad**, en caso de que así se requiera.

### Etapa Presencial

Se solicitará realizar agregados y cambios al proyecto de acuerdo a las restricciones ya mencionadas. Si un
alumno decide rendir la etapa presencial sin tener completa la etapa de preparación, tendrá menos tiempo
para realizar los cambios solicitados (porque deberá completar código que ya debería tener disponible).