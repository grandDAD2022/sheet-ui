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
- Publicaciones
- Notificaciones
- Comunidades

## Modelo Entidad/Relación

![Image text](https://github.com/grandDAD2022/sheet/blob/main/img/modelo_er.png)

- 1:N
  - Un usuario puede compartir varias publicaciones o ninguna.
  - Las publicaciones solo pueden ser creadas por un usuario.
  - Un usuario puede administrar (crear, modificar y eliminar) varias comunidades.
  - Una comunidad solo puede ser administrada por un usuario.

- N:N
  - Los usuarios tienen la opción de comentar en las publicaciones, y las publicaciones podrán ser comentadas por varios usuarios (Siempre se tendrá un comentario que contendra la fecha y el usuario de la publicación, y de forma opcional una descripción).
  - Un usuario puede estar o tener varias comunidades.
  - Las comunidades pueden tener varios usuarios (mínimo 1).
  - Los usuarios podrán seguirse entre ellos, por lo que, una persona puede seguir a varios perfiles, y estos, podrían seguirle a él.
  - Las comunidades pueden contener varias publicaciones, al igual que, una publicación puede estar en distintas comunidades.
  - Los usuario recibiran varias publicaciones, además, existirán notificaciones del sistema que serán recibidas por todos los usuarios.

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
