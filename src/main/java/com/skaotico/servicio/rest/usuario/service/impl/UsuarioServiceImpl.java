package com.skaotico.servicio.rest.usuario.service.impl;

import com.skaotico.servicio.rest.arbol.dto.ImagenResponse;
import com.skaotico.servicio.rest.rol.model.RolUsuarioEnum;
import com.skaotico.servicio.rest.storage.minio.MinioService;
import com.skaotico.servicio.rest.usuario.dto.UsuarioCreateDTO;
import com.skaotico.servicio.rest.usuario.mapper.UsuarioMapper;
import com.skaotico.servicio.rest.usuario.model.Usuario;
import com.skaotico.servicio.rest.usuario.repository.UsuarioRepository;
import com.skaotico.servicio.rest.usuario.service.UsuarioService;
import org.springframework.security.oauth2.jwt.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio de gestión de usuarios.
 * <p>
 * Esta clase proporciona la lógica de negocio para:
 * <ul>
 *     <li>Crear, obtener, listar y eliminar usuarios.</li>
 *     <li>Codificar contraseñas de manera segura usando BCrypt.</li>
 *     <li>Gestionar la carga de imágenes de perfil mediante MinIO.</li>
 * </ul>
 * </p>
 *
 * <h2>Campos</h2>
 * <ul>
 *     <li>{@link #usuarioRepository} (obligatorio): Repositorio para operaciones CRUD de usuarios.</li>
 *     <li>{@link #passwordEncoder} (obligatorio): Codificador de contraseñas.</li>
 *     <li>{@link #minioService} (obligatorio): Servicio de almacenamiento en MinIO.</li>
 *     <li>{@link #usuarioMapper} (obligatorio): Mapper para convertir entre DTO y entidad {@link Usuario}.</li>
 *     <li>{@link #bucketUsuarios} (obligatorio): Nombre del bucket en MinIO donde se guardan las imágenes de usuario.</li>
 * </ul>
 *
 * <h2>Reglas de negocio y validaciones</h2>
 * <ul>
 *     <li>La contraseña se codifica automáticamente al crear un usuario.</li>
 *     <li>El email debe ser único; se obtiene el usuario mediante el email del token JWT.</li>
 *     <li>Al subir una imagen de perfil, se genera un nombre único con UUID y se reemplazan los espacios por guiones bajos.</li>
 *     <li>Si el bucket de MinIO no existe, se crea automáticamente.</li>
 *     <li>La ruta del archivo se guarda en {@code fotoPerfil} del usuario.</li>
 *     <li>Todos los métodos lanzan {@link RuntimeException} ante errores de validación o persistencia.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <pre>{@code
 * @Autowired
 * private UsuarioService usuarioService;
 *
 * // Crear usuario
 * UsuarioCreateDTO dto = new UsuarioCreateDTO();
 * dto.setNombre("Juan");
 * dto.setApellido("Perez");
 * dto.setEmail("juan.perez@example.com");
 * dto.setPassword("123456");
 * Usuario usuarioCreado = usuarioService.crearUsuario(dto);
 *
 * // Obtener usuario por email desde JWT
 * Jwt token = ... // token con claim "usuEmail"
 * Usuario usuario = usuarioService.obtenerUsuarioPorEmail(token);
 *
 * // Subir imagen de perfil
 * MultipartFile file = ... // archivo desde frontend
 * Usuario usuarioConFoto = usuarioService.guardarImagenUsuario(file, token);
 *
 * // Listar usuarios
 * List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
 *
 * // Eliminar usuario
 * usuarioService.eliminarUsuario(usuarioCreado.getId());
 * }</pre>
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /** Repositorio de acceso a datos de usuarios. */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /** Codificador de contraseñas para almacenar de forma segura. */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /** Servicio para interactuar con MinIO para almacenamiento de archivos. */
    private final MinioService minioService;

    /** Mapper para convertir entre DTO y entidad Usuario. */
    private final UsuarioMapper usuarioMapper;

    /** Nombre del bucket en MinIO donde se almacenan las imágenes de usuario. */
    @Value("${minio.bucket.usuarios}")
    private String bucketUsuarios;

    /**
     * Constructor para inyectar dependencias finales.
     *
     * @param minioService  Servicio de almacenamiento MinIO.
     * @param usuarioMapper Mapper para Usuario.
     */
    public UsuarioServiceImpl(MinioService minioService, UsuarioMapper usuarioMapper) {
        this.minioService = minioService;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuarioDto DTO con los datos del usuario a crear.
     * @return Usuario creado con ID generado.
     * @throws RuntimeException si ocurre un error al guardar el usuario.
     */
    public Usuario crearUsuario(UsuarioCreateDTO usuarioDto) {
        try {
            Usuario usuario = usuarioMapper.toModel(usuarioDto);
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario correspondiente al ID.
     * @throws RuntimeException si el usuario no existe.
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            if (usuario.isPresent()) {
                return usuario.get();
            } else {
                throw new RuntimeException("Usuario no encontrado con id: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @throws RuntimeException si el usuario no existe.
     */
    public void eliminarUsuario(Long id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new RuntimeException("Usuario no encontrado con id: " + id);
            }
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return Lista de usuarios.
     * @throws RuntimeException si ocurre un error al listar.
     */
    public List<Usuario> listarTodosUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene un usuario por su email contenido en el token JWT.
     *
     * @param token Token JWT del usuario autenticado.
     * @return Usuario correspondiente al email.
     * @throws RuntimeException si el usuario no existe.
     */
    @Override
    public Usuario obtenerUsuarioPorEmail(Jwt token) {
        try {
            String email = token.getClaimAsString("usuEmail");
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        } catch (Exception e) {
            System.out.println("Error al obtener usuario por email: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Guarda la imagen de perfil de un usuario autenticado.
     * <p>
     * La imagen se sube a MinIO y se actualiza la URL en {@code fotoPerfil}.
     * </p>
     *
     * @param file  Archivo de imagen a subir.
     * @param token Token JWT del usuario autenticado.
     * @return Usuario actualizado con la URL de la imagen de perfil.
     * @throws RuntimeException si ocurre un error al subir la imagen.
     */
    @Override
    public Usuario guardarImagenUsuario(MultipartFile file, Jwt token) {
        System.out.println("Inicio del método guardarImagenUsuario");

        Usuario usuario = this.obtenerUsuarioPorEmail(token);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado para el email");
        }
        System.out.println("Usuario encontrado: " + usuario.getRut() + " - " + usuario.getNombre());

        String originalFilename = file.getOriginalFilename();
        System.out.println("Nombre original del archivo: " + originalFilename);

        String nombreArchivo = UUID.randomUUID() + "_" +
                (originalFilename != null ? originalFilename.replaceAll("\\s+", "_") : "imagen");
        String objectPath = usuario.getRut() + "/" + nombreArchivo;
        System.out.println("Nombre final del archivo: " + nombreArchivo);
        System.out.println("Ruta en bucket: " + objectPath);

        try {
            System.out.println("Verificando existencia del bucket: " + bucketUsuarios);
            minioService.createBucketIfNotExists(bucketUsuarios);

            System.out.println("Subiendo archivo al bucket...");
            try (InputStream is = file.getInputStream()) {
                minioService.uploadFile(bucketUsuarios, objectPath, is, file.getSize(), file.getContentType());
            }

            String url = minioService.getFileUrl(bucketUsuarios, objectPath);
            System.out.println("Archivo subido con éxito. URL obtenida: " + url);

            usuario.setFotoPerfil(objectPath);
            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            System.out.println("Usuario actualizado y guardado correctamente: " + usuarioGuardado.getRut());

            return usuarioGuardado;
        } catch (Exception e) {
            System.out.println("Error al guardar la imagen de perfil: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo guardar la imagen de perfil", e);
        }
    }

    /**
     * Obtiene un usuario por su email.
     * <p>
     * Este método consulta el repositorio de usuarios buscando un registro cuyo email
     * coincida con el proporcionado. Si no se encuentra ningún usuario, se lanza
     * {@link RuntimeException}. Además, captura y registra cualquier excepción que
     * ocurra durante la consulta.
     * </p>
     *
     * <h3>Ejemplo de uso:</h3>
     * <pre>{@code
     * String email = "juan.perez@example.com";
     * Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
     * System.out.println(usuario.getNombre());
     * }</pre>
     *
     * @param email Email del usuario a buscar. Debe ser único y no nulo.
     * @return El {@link Usuario} correspondiente al email proporcionado.
     * @throws RuntimeException Si no se encuentra ningún usuario con el email especificado
     *                          o si ocurre algún error durante la consulta.
     */
    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        try {
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        } catch (Exception e) {
            System.out.println("Error al obtener usuario por email: " + e.getMessage());
            throw e;
        }
    }
}
