ProyectoSustitutoFCT

# 15/05/2020
1. Comienzo del trabajo de investigación de páginas y aplicaciones de referencia para decidir que funcionalidades necesitaría la aplicación y hacerme una idea de cómo realizar el diseño de las interfaces de usuario. 

# 18/05/2020
1. A lo largo de este fin de semana hasta hoy he continuado con la investigación y he llegado las siguientes conclusiones para el diseño de la red social:
	* La red social se compondrá de múltiples usuarios cada uno con un e-mail, una contraseña y un nombre.

	* Los usuarios podrán tener una relación de amistad con otros usuarios. Para esto previamente uno de los usuarios de la relación deberá enviar una petición de amistad al otro y este decidirá si la acepta o no.

	* Los usuarios podrán publicar post. Los post tienen un contenido de texto, una fecha de publicación y una puntuación en base a los votos de los usuarios. Además, los post pueden ser de dos tipos, públicos que pueden verlos todos los usuarios indiferentemente de si son amigos o no del creador del post o privados, los cuales solo pueden ver aquellos usuarios que sean amigos del creador del post.

	* Los post a su vez pueden recibir comentarios por parte de otros usuarios. Los comentarios poseen un contenido de texto y una fecha.
2. En base a las conclusiones expuestas en el apartado anterior he creado un diagrama entidad-relación en “yEd” según el cual diseñaré la base de datos.

3. También he estado investigando diferentes programas para realizar los diversos diseños de las pantallas de interface de usuario y el diseño de navegación entre pantallas. Finalmente me he decidido por hacer esto en “Mockplus”. Si todo va bien espero poder subirte todos los diseños de pantallas mañana.

# 19/05/2020
1. Diseño de las pantallas de interface de usuario tanto en Web como App móvil y el Diseño de navegación entre pantallas, se han subido imágenes de las pantallas y la navegación en la carpeta “Imágenes pantallas”.

2. También he estado probando como hacer una Aplicación Web en .Net.

3. Comienzo la redactar del anteproyecto.

# 20/05/2020

1. Entrega del anteproyecto.

2. Informándome sobre el OAuth. 

# 21/05/2020

1. Creación de la base de datos subo el archivo “Creación de tablas.sql” con las sentencias de creación.

2. Comienzo del proyecto del servicio REST lo subiré dentro de la carpeta “RestProyect”.

# 22/05/2020

1. Modificación de la tabla amigos de la base de datos. He realizado los cambios en la tabla que me ha aconsejado Javi en la corrección del anteproyecto (eliminación del campo id).

2. Sigo trabajando en el servicio REST.

3. Esta semana no te mandare un video ya que aún no tengo suficiente material para mostrarte.

# 25/05/2020

1. Comienzo el desarrollo del proyecto de la aplicación web lo subiré dentro de la carpeta “WebApplication”.

# 26/05/2020

1. Sigo trabajando en la aplicación.

# 27/05/2020

1. Arreglos en la base de datos, concretamente en la tabla amigos y en la tabla peticiones. Se han actualizado las sentencias de creación del archivo “Creación de tablas.sql”. 

2. Cambios en el servicio REST

	* Creacion del método GET miUsuario en la clase GestorUsuarios que pasando como parámetro nuestro token de autentificación nos devuelve un json con la información del usuario propietario del token.
	
	* Ahora la clase Comentarios tiene un nuevo parámetro tipo string “autor” en el que se pondrá el nombre del usuario que haya creado el comentario.
	
	* Adaptado el método GET comentariosPost de la clase GestorDeComentarios para que el json devuelto también incluya el nuevo parámetro autor.
	
	* Cambios en algunos métodos de la clase GestorDePeticiones.

3. Trabajando en la aplicación web.

	* Trabajando en la "PaginaPrincipal" para que en ella se mostraran los post de nuestro usuario y las peticiones de amistad junto a los botones de aceptar o rechazar la petición.
	
	* Dándole formato a la "PaginaPrincipal" para que sea lo más similar posible al diseño del anteproyecto.

# 28/05/2020

1. Trabajando en la aplicación web

	* Dándole formato a la página "PaginaPrincipal"

		* Agregado un “loader” mientras se carga el contenido de la página.

	* Creación de la página de “Buscar” en la que se nos mostrarán los resultados de la búsqueda de usuarios.

	* Dándole formato a la página “Buscar”

	* Ahora en la página de “Login” se muestra un mensaje de error al intentar loguearte sin rellenar todos los campos.

	* Mejoras en el formato de la página de “Register”.

# 29/05/2020

1. Trabajando en la aplicación web

	* Creación de la página de “CrearPost”.

	* Dándole formato a la página “CrearPost”.

	* Haciendo vídeo explicativo de avance semanal.
	
# 1/06/2020

1. Cambios en el servicio REST

	* Ahora la función comentariosPost de la Clase GestorDeComentarios devuelve los comentarios ordenados del más reciente al más antiguo.

	* Ahora la función obtenerPost de la Clase GestorDePosts devuelve los post ordenados del más reciente al más antiguo.

2. Trabajando en la aplicación web

	* Trabajando en la página de “CrearPost” ya se pueden publicar posts.

	* Arreglado un error en la página de “PaginaPrincipal” que causaba que en la fecha de los comentarios se mostrara la fecha del post al que hacían referencia.

# 2/06/2020

1. Cambios en el servicio REST

	* Creación del método POST modificarUsuario  en la clase GestorUsuarios que pasando como parámetro nuestro token de autentificación y la contraseña del usuario propietario del token nos permite modificar los datos del usuario en base a un json mandado en el cuerpo de la petición.

2. Trabajando en la aplicación web

	* Trabajando en la página de “CrearPost”:

		* Cambios en el formato

		* Añadidas más opciones en el cuadro de redacción de post (tamaño de letra y alineación del texto).

	* Creación de la página de “EditarPerfil” desde la que podremos cambiar los datos de nuestro usuario.
	
	* Dándole formato a la página “EditarPerfil”.
	
# 3/06/2020

1. Cambios en el servicio REST

	* Creación del método GET comprobarAmistad en la clase GestorDeAmigos  que pasando como parámetro nuestro token de autentificación y la id de un usuario nos permite comprobar la relación actual entre ambos usuarios (amigos, pendiente de respuesta o desconocidos).

	* Se ha modificado el método GET obtenerUsuario en la clase GestorUsuarios ahora pasando como parámetro nuestro token de autentificación y la id de un usuario nos devolverá un json con la información del usuario (nombre, email …) al que le pertenece la id.

2. Trabajando en la aplicación web

	* Creación de la página de “PantallaOtroUsuario”

		* Dándole formato a la página 

		* Trabajando para que se muestre la información del usuario y posibilitar él envió de la petición de amistad en caso de que el usuario consultado no sea tu amigo.

# 4/06/2020

1. Cambios en el servicio REST

	* Creación del método GET otroUsuario en la clase GestorDeAmigos que pasando como parámetro nuestro token de autentificación y la id de un usuario nos devuelve un json con los amigos del usuario propietario de la id.

2. Trabajando en la aplicación web

	* Trabajando para que en la “PantallaOtroUsuario” se muestren los amigos y los post (en caso de ser amigos se muestran los post públicos y privados si no solo se muestran los públicos) del usuario propietario de la página.
	
# 5/06/2020

1. Trabajando en la aplicación web

	* Trabajando para que en la “PantallaOtroUsuario” se nos dé la posibilidad de realizar comentarios a los post del usuario.

2. Haciendo vídeo explicativo de avance semanal.

# 8/06/2020

1. Buscando información sobre como consumir un servicio REST desde Android.

2. Comienzo del proyecto de la App móvil lo subiré dentro de la carpeta “AppMovil”.

	* Haciendo diversas pruebas para el consumo del servicio REST.

	* Trabajando en la Activity “LoginActivity”.
	
# 9/06/2020

1. Trabajando en la App móvil.

	* Creada la Activity “RegisterActivity” desde la que se puede crear un nuevo usuario en la red social.

	* Trabajando en la Activity “PrincipalActivity” para que en ella se muestre la información de nuestro usuario y nuestros posts.

# 11/06/2020

1. Trabajando en la App móvil.

	* Trabajando en la Activity “PrincipalActivity”.

		* Ahora se muestran también los comentarios que tienen nuestros posts.

		* Se añadió una barra de navegación inferior desde la que se nos dará acceso a otras Activitys de la aplicación.

		* El botón de cerrar sesión ahora es funcional
	
	* Creación de la Activity “BusquedaActivity”.
	
		* Ya se pueden buscar usuarios

		* También cuenta con la barra de navegación inferior

# 12/06/2020

1. Trabajando en la App móvil.

	* Trabajando en la Activity “PrincipalActivity”.

		* Agregado botón de "Crear nuevo post".

		* Cambios de formato.
	
	* Creación de la Activity “AmigosActivity”.
	
		* Ya se muestran nuestros amigos.

		* Trabajando para que se muestren peticiones de amistad, aún en proceso.
	
2. Haciendo vídeo explicativo de avance semanal.

# 15/06/2020

1. Trabajando en la App móvil.

	
	* Trabajando en la Activity “AmigosActivity”.
	
		* Ya se muestran las peticiones de amistad.

		* Ya se pueden aceptar o rechazar las peticiones.
		
		* Cambios en el formato de la activity.
		
# 16/06/2020

1. Trabajando en la App móvil.

	
	* Creación de la Activity “OtroUsuarioActivity”.
	
		* Se pueden consultar los post de otros usuarios.

		* Se pueden mandar solicitudes de amistad al propietario.
		
	* Actualizados los nombres de los usuarios en los resultados de buscar un usuario y los comentarios de los posts de forma que al pulsarlos nos lleven a la "OtroUsuarioActivity" correspondiente a ese usuario.
	
	* Creación de la Activity “ActualizarUsuarioActivity”.
	
	* Creación de la Activity “PublicarActivity”.
		
	

	