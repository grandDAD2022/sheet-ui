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

## Relaciones
- 1:N
  - Un usuario puede tener varias publicaciones
  - Una publicación puede tener múltiples comentarios
  - Un usuario puede tener múltiples comentarios
- N:N
  - Varias notificaciones pueden llegar a varios usuarios

## Servicios internos
- Interfaz de usuario
  - Autenticación
  - Notificaciones
  - Publicaciones
  - Obtención de muro
- Almacenamiento de datos
- Envío de correo
- Escalado de imágenes
- Compresión de vídeo
