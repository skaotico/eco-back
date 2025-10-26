-- =====================================================
-- Enums
-- =====================================================

-- =====================================================
-- Enum: tipo_area_enum
-- Descripción: Tipos de área permitidos en un recinto.
-- Sirve para clasificar las áreas según su función o ubicación.
-- =====================================================
CREATE TYPE tipo_area_enum AS ENUM (
    'invernadero',
    'exterior',
    'interior',
    'hidroponico'
);

-- =====================================================
-- Enum: rol_usuario_enum
-- Descripción: Roles de usuario permitidos en el sistema.
-- Se utiliza en la tabla rol para asignar permisos.
-- =====================================================
CREATE TYPE rol_usuario_enum AS ENUM (
    'admin',
    'usuario',
    'supervisor'
);

-- =====================================================
-- Enum: estado_conservacion_enum
-- Descripción: Estados de conservación de especies.
-- Se utiliza en arbolesNativo para definir el riesgo de extinción de cada especie.
-- =====================================================
CREATE TYPE estado_conservacion_enum AS ENUM (
    'Preocupación menor',
    'Casi amenazado',
    'Vulnerable',
    'En peligro',
    'En peligro crítico',
    'Extinto en estado silvestre',
    'Extinto'
);

-- =====================================================
-- Tablas base
-- =====================================================

-- =====================================================
-- Tabla: nacionalidad
-- Descripción: Lista de nacionalidades disponibles para los usuarios.
-- Campo clave: id, usado en usuario.nacionalidad_id para asociar un usuario con su nacionalidad.
-- =====================================================
CREATE TABLE nacionalidad (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- Tabla: usuario
-- Descripción: Usuarios del sistema con datos personales, contacto y metadata.
-- FKs:
--   - nacionalidad_id: referencia a la nacionalidad del usuario.
-- =====================================================
CREATE TABLE usuario (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    apellido TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    celular VARCHAR(20),
    telefono_fijo VARCHAR(20),
    rut VARCHAR(15),
    direccion TEXT,
    ciudad TEXT,
    nacionalidad_id INT REFERENCES nacionalidad(id),
    fecha_nacimiento DATE,
    genero VARCHAR(15) CHECK (genero IN ('MASCULINO', 'FEMENINO', 'OTRO')),
    activo BOOLEAN DEFAULT TRUE,
    ultimo_login TIMESTAMP,
    foto_perfil VARCHAR(255),
    metadata JSONB,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: rol
-- Descripción: Roles del sistema para gestión de permisos.
-- Se utiliza para asociar roles a usuarios mediante usuario_rol.
-- =====================================================
CREATE TABLE rol (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre rol_usuario_enum UNIQUE NOT NULL,
    descripcion TEXT,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: usuario_rol
-- Descripción: Relación muchos a muchos entre usuarios y roles.
-- Permite que un usuario tenga múltiples roles y viceversa.
-- =====================================================
CREATE TABLE usuario_rol (
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    rol_id BIGINT NOT NULL REFERENCES rol(id),
    PRIMARY KEY (usuario_id, rol_id),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: acceso_usuario
-- Descripción: Historial de accesos de los usuarios al sistema.
-- Guarda cuándo y desde dónde un usuario inició sesión.
-- =====================================================
CREATE TABLE acceso_usuario (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip TEXT,
    exito BOOLEAN NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: recinto
-- Descripción: Contiene los recintos que albergan áreas.
-- =====================================================
CREATE TABLE recinto (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    ubicacion TEXT,
    capacidad_areas INT,
    descripcion TEXT,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: acceso_recinto
-- Descripción: Relación muchos a muchos entre usuarios y recintos.
-- Permite definir qué recintos puede gestionar o ver cada usuario.
-- =====================================================
CREATE TABLE acceso_recinto (
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    recinto_id BIGINT NOT NULL REFERENCES recinto(id),
    permisos JSONB, -- Opcional: reglas específicas de acceso por recinto
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin TIMESTAMP,
    PRIMARY KEY (usuario_id, recinto_id),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: area
-- Descripción: Áreas dentro de un recinto.
-- =====================================================
CREATE TABLE area (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nombre TEXT NOT NULL,
    recinto_id BIGINT NOT NULL REFERENCES recinto(id),
    superficie_m2 NUMERIC(8,2),
    tipo_area tipo_area_enum,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: arbol
-- Descripción: Árboles plantados en un área.
-- =====================================================
CREATE TABLE arbol (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    especie TEXT,
    area_id BIGINT NOT NULL REFERENCES area(id),
    creado_por BIGINT REFERENCES usuario(id),
    gps_point TEXT,
    metadata JSONB,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: tutor
-- Descripción: Dispositivos que miden humedad en un área.
-- =====================================================
CREATE TABLE tutor (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    codigo_serial TEXT UNIQUE NOT NULL,
    area_id BIGINT NOT NULL REFERENCES area(id),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: lectura
-- Descripción: Lecturas periódicas de humedad tomadas por tutores.
-- =====================================================
CREATE TABLE lectura (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tutor_id BIGINT NOT NULL REFERENCES tutor(id),
    arbol_id BIGINT NOT NULL REFERENCES arbol(id),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    datos_ambientales JSONB,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: lectura_arbol
-- Descripción: Registro de lecturas de humedad y condiciones ambientales asociadas a árboles.
-- =====================================================
CREATE TABLE lectura_arbol (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    arbol_id BIGINT NOT NULL REFERENCES arbol(id),
    tutor_id BIGINT REFERENCES tutor(id),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    humedad NUMERIC(5,2) NOT NULL,
    inicia_riego BOOLEAN DEFAULT FALSE,
    metadata JSONB,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- Tabla: arbolesNativo
-- Descripción: Registro de especies de árboles nativos.
-- =====================================================
CREATE TABLE arbolesNativo (
    id SERIAL PRIMARY KEY,
    especie_cientifica VARCHAR(255) NOT NULL,
    nombre_comun VARCHAR(255),
    estado_conservacion estado_conservacion_enum NOT NULL,
    tipo_bosque VARCHAR(255),
    caracteristicas TEXT,
    altura_maxima DECIMAL(5,2),
    diametro_tronco DECIMAL(5,2),
    distribucion_geografica TEXT,
    usos TEXT,
    fecha_registro TIMESTAMP DEFAULT NOW(),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- =====================================================
-- Tabla: esp32_device
-- Descripción: Dispositivos ESP32 registrados en el sistema.
-- Se utiliza para mapear los dispositivos, identificar su hardware y estado de conexión.
-- =====================================================
CREATE TABLE esp32_device (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    mac VARCHAR(17) NOT NULL UNIQUE,         -- Dirección MAC del ESP32
    chip_id VARCHAR(20) NOT NULL UNIQUE,     -- Chip ID único
    model VARCHAR(50) NOT NULL,              -- Modelo del dispositivo (ej. ESP32)
    cores SMALLINT NOT NULL,                 -- Número de núcleos
    revision SMALLINT NOT NULL,              -- Revisión del chip
    flash BIGINT NOT NULL,                   -- Tamaño de flash en bytes
    sdk VARCHAR(20) NOT NULL,                -- Versión del SDK
    ip INET,                                 -- IP asignada
    rssi SMALLINT,                           -- Intensidad de señal WiFi
    uptime BIGINT,                            -- Tiempo activo desde último reset (segundos)
    last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Última vez que se reportó online
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);