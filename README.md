# 🐟 LampreasVioleta

Ejercicio de Acceso a Datos – Ampliación

En este proyecto se amplía el proyecto base incluyendo nuevas entidades Repartidor y Comercial, se integran en el modelo de datos existente y 
se introducen nuevas funcionalidades de  exportaciçon e importación a JSON.

---

## Estructura del proyecto 
```
LampreasVioleta/
    ├── data/               # Archivos de persistencia JSON
    │   ├── lampreasvioleta_export.json
    │   └── repartidores_export.json
    ├── src/
    │   └── main/
    │       └── java/
    │           ├── app/    # Lógica de ejecución y Vistas
    │           │   ├── ClientesView.java
    │           │   ├── DemoRelaciones.java
    │           │   └──LampreasVioletaApp.java
    │           │
    │           ├── dao/    # Data Access Objects
    │           │   ├── ClienteDAO.java
    │           │   ├── ComercialDAO.java
    │           │   ├── DetalleClienteDAO.java
    │           │   ├── DetallePedidoDAO.java
    │           │   ├── PedidoDAO.java
    │           │   ├── ProductoDAO.java
    │           │   └── RepartidorDAO.java
    │           │
    │           ├── db/     # Conexión/Gestión de Base de Datos
    │           │   └── Db.java
    │           │
    │           ├── model/  # Entidades de datos (POJOs)
    │           │   ├── AppData.java
    │           │   ├── Cliente.java
    │           │   ├── Comercial.java
    │           │   ├── DetalleCliente.java
    │           │   ├── DetallePedido.java
    │           │   ├── Pedido.java
    │           │   ├── Producto.java
    │           │   ├── Repartidor.java
    │           │   └── RepartidoresData.java
    │           │
    │           └── services/ # Servicios auxiliares y Entrada/Salida
    │               ├── ClienteDetalle.java
    │               └── JsonIO.java
    │
    ├── target/             # Archivos compilados (generado por Maven)
    ├── .gitignore          # Archivos excluidos de Git
    └── pom.xml             # Configuración de dependencias Maven
```

## 🎯 Objetivos del trabajo

1. Extender el modelo de datos agregando dos nuevas clases relacionadas con el contexto del sistema:  
   - **Comercial**  
   - **Repartidor**

2. Implementar un **DAO** para cada nueva clase, siguiendo el patrón DAO:  
   - Métodos CRUD (`insert`, `delete`, `findById`, `findAll`)  
   - Integración con la lógica existente (uso de JDBC, PreparedStatement, manejo de relaciones y claves foráneas).

3. Actualizar el programa principal (`DemoRelaciones`) para:  
   - Incluir opciones en el menú para gestionar las nuevas entidades.  
   - Permitir **alta**, **consulta**, **listado** y **eliminación**.

4. Añadir funcionalidad de **exportación de datos a JSON**:  
   - Usando la librería **Jackson (ObjectMapper)**  
   - Exportar listas completas de entidades a JSON estructurado  
   - Los ficheros se generan en la carpeta del proyecto.

---

## 🧩 Ampliación del Proyecto

### ✅ Nuevas entidades
Se han añadido las siguientes tablas en la base de datos:

- **Comercial**: id, nombre, email, telefono  
- **Repartidor**: id, nombre, email, telefono  

Cada entidad tiene su **clase de modelo** en Java 
---
###  📦 Nuevos DAO

Se incluyen DAO para las neuvas entidades incorporadas: 

- **ComercialDAO**
- **RepartidorDAO**
  
Cada DAO incluye metodos CRUD : `insert`, `findAll`,`findById`, `update` y `delete`.
Cada DAO gestiona conexiones a la BD, ejecución de SQL mediante PreparedStatement y mantiene la integridad de la base de datos.

---
###  📂 Nuevo DTO para repartidores

- **RepartidoresData**
  
Se crea una clase contenedora `RepartidoresData` para la exportacion/ importacion JSON de repartidores. Se incluye un atributo lista para almacenar los repartidores existentes en la base de datos 
, un constructor vacío,  y getter/setter. 

---
### 🔗  Integración con el modelo de datos existente

- Las nuevas entidades integradas, Comercial y Repartidor, se integran con el modelo de datos existente.

Cliente - Comercial -> Relacion 1:N ( un cliente se relaciona con un solo comercial y un comercial tieen varios clientes) 
Pedido - Repartidor -> Relacion 1:N ( un pedido es repartido por un solo repartidor y un repartidor reparte varios pedidos) 

- Para implementar tales relaciones se modifica la clase `Cliente` y `Pedido` incorporándo
  un nuevo atributo para  representar las claves foráneas.
  
- Igualmente, se modifican `ClienteDao` y `PedidoDAO` para reflejar estas relaciones. Se modifican los métodos `insert` y `mapRow`.
  
- Se modifica el menu principal en `DemoRelaciones` para incluir nuevas opciones para comerciales, repartidores y las funcionalidades
  de exportacion e importacion a JSON.

- Se incluyen  en nuevos metodos `DemoRelaciones` para insertar, listar,buscar por id, y  eliminar  comerciales y repartidores. 
---
## 📤 Exportación e importación de Repartidores a JSON

En esta ampliación se ha añadido la funcionalidad de exportar e importar los datos de **repartidores** en formato JSON, usando la clase de utilidad `JsonIO` y la clase contenedora `RepartidoresData`.

---

### 🧩 Clases clave

- **Repartidor**: clase de modelo que representa un repartidor con los campos `id`, `nombre`, `email` y `telefono`.  
- **RepartidoresData**: contenedor exclusivo de repartidores, con:
  - Lista interna `List<Repartidor> repartidores`
  - Constructor vacío
  - Getters y setters  
  Esta clase permite serializar y deserializar listas de repartidores de forma sencilla usando Jackson.

- **JsonIO**: clase utilitaria para manejar JSON:
  - `write(File file, T data)`: serializa cualquier objeto Java a JSON
  - `read(File file, Class<T> type)`: deserializa JSON a un objeto Java del tipo indicado
  - Configurada para "pretty print" y manejo correcto de fechas (Java 8+).

---

### 📤 Exportar repartidores a JSON

La exportacion de los repartidores a JSON se realiza de la siguiente manera: 

1. Se obtiene la lista completa de repartidores desde la base de datos utilizando el metodo `findAll` de `RepartidoresDAO`:

```java
List<Repartidor> repartidores = repartidorDAO.findAll();
```

2. Se crea un objeto contenedor RepartidoresData y se rellena con la lista:
   
```java
RepartidoresData data = new RepartidoresData();
data.setRepartidores(repartidores);
```

3. Se escribe el JSON en un fichero usando JsonIO:
 
```java
JsonIO.write(JSON_REPARTIDOR, data);
```

4. Se genera un JSON legible (pretty print) en la carpeta data del proyecto:
   
```json
{
  "repartidores" : [ {
    "id" : 1,
    "nombre" : "Diego",
    "email" : "diegoreparte@ejemplo.com",
    "telefono" : "123321123"
  }, {
    "id" : 2,
    "nombre" : "Fernando Peréz",
    "email" : "fernandovespino@gmail.com",
    "telefono" : "987789987"
  }, {
    "id" : 3,
    "nombre" : "Marina",
    "email" : "marinalopezlopez@gmail.com",
    "telefono" : "123456789"
  } ]
}
```
---
### IMPORTACION DE REPARTIDORES 

1. El ficehro JSON es leido usando `JsonIo.read' para obtener  la lista de repartidores:
   
   ```java RepartidoresData data = JsonIO.read(JSON_REPARTIDOR, RepartidoresData.class);```
   
2. Se recorre la lista de repartidores y se insertan en la base de datos usando el DAO:
   
   ```java
   for (Repartidor r : data.getRepartidores()) {
      repartidorDAO.insert(r);} ```

3. Si un repartidor con el mismo id ya existe en la base de datos, la inserción fallará de forma controlada para mantener la integridad de la clave primaria.


## SQL 

Se  proporcionan las consultas sql utilizadas en aras de facilitar la prueba local del proyecto. 

### MODELO BASE 

```sql
-- CLIENTE (1)
CREATE TABLE IF NOT EXISTS cliente (
  id        INT PRIMARY KEY,
  nombre    VARCHAR(120) NOT NULL,
  email     VARCHAR(200) NOT NULL UNIQUE
);

-- DETALLE_CLIENTE (1:1)  → FK única a cliente
CREATE TABLE IF NOT EXISTS detalle_cliente (
  id          INT PRIMARY KEY,                  -- misma PK que cliente.id (opción clásica 1:1)
  direccion   VARCHAR(200),
  telefono    VARCHAR(40),
  notas       VARCHAR(200),
  CONSTRAINT fk_det_cliente FOREIGN KEY (id) REFERENCES cliente(id) ON DELETE CASCADE
);

-- PRODUCTO
CREATE TABLE IF NOT EXISTS producto (
  id      INT PRIMARY KEY,
  nombre  VARCHAR(120) NOT NULL,
  precio  NUMERIC(12,2) NOT NULL CHECK (precio >= 0)
);

-- PEDIDO (N de 1:N con Cliente)
CREATE TABLE IF NOT EXISTS pedido (
  id          INT PRIMARY KEY,
  cliente_id  INT NOT NULL,
  fecha       DATE NOT NULL,
  CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id)
    REFERENCES cliente(id) ON DELETE RESTRICT
);

-- DETALLE_PEDIDO (tabla intermedia N:M)
CREATE TABLE IF NOT EXISTS detalle_pedido (
  pedido_id    INT NOT NULL,
  producto_id  INT NOT NULL,
  cantidad     INT NOT NULL CHECK (cantidad > 0),
  precio_unit  NUMERIC(12,2) NOT NULL CHECK (precio_unit >= 0),
  PRIMARY KEY (pedido_id, producto_id),
  CONSTRAINT fk_dp_pedido   FOREIGN KEY (pedido_id)   REFERENCES pedido(id)   ON DELETE CASCADE,
  CONSTRAINT fk_dp_producto FOREIGN KEY (producto_id) REFERENCES producto(id) ON DELETE RESTRICT
);
```
### COMERCIAL Y REPARTIDOR
```sql
CREATE TABLE IF NOT EXISTS repartidor(
	id  INT NOT NULL PRIMARY KEY,
	nombre VARCHAR(120) NOT NULL,
	email  VARCHAR(200) NOT NULL UNIQUE,
	telefono VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS comercial(
	id  INT NOT NULL PRIMARY KEY,
	nombre VARCHAR(120) NOT NULL,
	email  VARCHAR(200) NOT NULL UNIQUE,
	telefono VARCHAR(40)
);
```
### INTEGRACIÓN DE COMERCIAL Y REPARTIDOR EN EL MODELO DE DATOS 
```sql
--COMERCIAL - CLIENTE 
--creamos el campo de la fk y a continuacion la creamos la Foreing Key
ALTER TABLE cliente
ADD COLUMN comercial_id INT;

ALTER TABLE cliente
ADD CONSTRAINT fk_cliente_comercial 
FOREIGN KEY (comercial_id)
REFERENCES comercial(id)
ON DELETE SET NULL;  -- si se elimina un comercial, dejamos NULL en cliente
--la constraint  tiene que tener un nombre unico no repetido

-- REPARTIDOR - PEDIDO

ALTER TABLE pedido
ADD COLUMN repartidor_id INT;

ALTER TABLE pedido
ADD CONSTRAINT fk_pedido_repartidor
FOREIGN KEY (repartidor_id)
REFERENCES repartidor(id)
ON DELETE SET NULL;  -- si se elimina un repartidor, dejamos NULL en pedido
```

## 🛠 Tecnologías utilizadas

- **Java 17** – Lenguaje principal del proyecto  
- **JDBC** – Conexión y manipulación de la base de datos relacional  
- **DAO pattern** – Para separar la lógica de acceso a datos  
- **Jackson (ObjectMapper)** – Serialización y deserialización JSON  
- **SQL** – Creación y gestión de tablas en RDBMS (PostgreSQL/MySQL/etc.)  
- **Maven/Gradle** – Gestión de dependencias y compilación del proyecto  

---
## 🎓 Objetivos de aprendizaje

- Ampliar un modelo de datos relacional existente manteniendo la integridad referencial  
- Diseñar e implementar DAOs consistentes y reutilizables  
- Integrar nuevas entidades  en un proyecto ya existente sin romper funcionalidades previas  
- Gestionar relaciones en Java y SQL 
- Implementar exportación e importación de datos en formato JSON con Jackson  
- Practicar buenas prácticas de modularidad y organización de código

