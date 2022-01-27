# Sheet

Sheet es una red social que permite a sus usuarios centralizar las publicaciones de sus redes en una sola.
Se podrá iniciar sesión con cuentas de otras redes sociales,
así como seguir a usuarios que se unan, ver sus publicaciones y comentar en ellas.

## Funcionalidad
- Pública
  - Acceso a perfiles públicos
  - Registro de cuenta nueva
- Privada
  - Administrar perfil
  - Seguir cuentas
  - Ver publicaciones
  - Crear publicaciones
  - Comentar
  - Recibir notificaciones

## Entidades
- Usuarios
- Seguidos
- Publicaciones
- Notificaciones
- Comentarios
- Comunidades

## Relaciones
- 1:N
  - Un usuario puede tener varias publicaciones.
  - Una publicación puede tener múltiples comentarios.
  - Un usuario puede crear comunidades.
- N:N
  - Varios usuarios pueden pertenecer a mismas comunidades, es decir, los usuarios pueden estar o no en una comunidad, y ésta puede tener más de un usuario.
  - Varias notificaciones pueden llegar a varios usuarios.
  - Los usuarios podrán seguirse entre ellos, por lo que, una persona puede seguir a varios perfiles, y estos, podrían seguirle a él.

## Servicios web
- Interfaz de usuario
- Publicaciones
- Obtención de muro
- Almacenamiento de datos

## Servicios internos
- Autenticación
- Notificaciones
- Envío de correo
- Escalado de imágenes
- Compresión de vídeo
