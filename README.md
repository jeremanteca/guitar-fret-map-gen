1.- Contexto del proyecto:
Aplicación de Android, la cual permite la generación de mapas de diapasón de
guitarra, diseñada para músicos y entusiastas de la guitarra que buscan explorar y
visualizar diferentes afinaciones de manera personalizada. La aplicación permite al
usuario ingresar una afinación específica para generar un mapa de diapasón que
muestra las notas correspondientes a cada cuerda y traste. Este recurso es
especialmente útil para músicos que desean experimentar con afinaciones
alternativas y tener una representación visual clara de cómo se distribuyen las notas
en el mástil en función de la afinación elegida.

2.- Requerimientos con descripción, wireframe relacionado y casos de uso del requerimiento:
Requisitos Funcionales de Usuario
• El usuario debe poder ingresar una afinación personalizada para la guitarra.
• El usuario debe poder generar un mapa del diapasón basado en la afinación
ingresada.
• El usuario debe poder guardar afinaciones favoritas para su uso futuro.
• El usuario debe poder acceder rápidamente a las afinaciones comunes
recomendadas.
• El usuario debe tener la opción de escuchar las notas en el mapa del diapasón.
• El usuario debe poder interactuar fácilmente con la interfaz para navegar entre
diferentes funcionalidades.

Requisitos Funcionales del Sistema
• El sistema debe permitir el ingreso de afinaciones personalizadas por parte del
usuario.
• El sistema debe generar un mapa visual del diapasón con las notas
correspondientes a la afinación.
• El sistema debe guardar afinaciones personalizadas en una base de datos local y
mostrarlas en otra pantalla, para ser elegidas o borradas.
• El sistema debe proporcionar una lista de afinaciones comunes para que el usuario
las seleccione.
• El sistema debe habilitar la funcionalidad de reproducción de notas individuales en
el mapa del diapasón.
• El sistema debe ofrecer una interfaz amigable e intuitiva para que los usuarios
puedan interactuar fácilmente.
• El sistema debe permitir la visualización del mapa en tiempo real al ingresar una
nueva afinación.
• El sistema debe tener capacidad para almacenar un número limitado de afinaciones
favoritas.
• El sistema debe estar optimizado para su uso en dispositivos móviles Android.
• El sistema debe actualizarse automáticamente cuando el usuario modifique la
afinación de las cuerdas.

Requisitos No Funcionales del Producto
• La aplicación debe ser fácil de usar, con una interfaz intuitiva que minimice el tiempo
de aprendizaje.
• La respuesta del sistema al ingresar una nueva afinación debe ser rápida, sin
retardos notables.
• La aplicación debe ser visualmente atractiva, con un diseño moderno y minimalista.
• La aplicación debe consumir una cantidad mínima de recursos del dispositivo para
evitar afectar su rendimiento.
• El sistema debe estar disponible en español y en inglés para una mayor
accesibilidad.
• Requisitos No Funcionales Organizacionales
• La aplicación debe estar alineada con las mejores prácticas de desarrollo de
software ágil (Scrum).
• El proyecto debe seguir una estructura modular para facilitar su mantenimiento y
actualización futura.
• La aplicación debe cumplir con los estándares de accesibilidad para permitir que
una mayor audiencia pueda utilizarla.

![image](https://github.com/user-attachments/assets/e8413f4f-b3c6-4a90-bcb6-5c22e4c9b371)
![image](https://github.com/user-attachments/assets/048d2ce1-e94f-440b-b833-a70ffcf40a3f)

*Casos de uso*
- Generar mapa ingresando manualmente una afinación en el TextField correspondiente: El usuario ingresa una afinación manualmente en el Textfield de la pantalla "Principal" y el mapa se genera correctamente.
- Generar mapa usando afinación de la lista de recomendadas: El usuario genera un mapa seleccionando una afinación de la lista de recomendadas y el mapa aparece correctamente.
- Guardar afinación en lista de pantalla “Favoritas”: El usuario guarda una afinación como "Favorita" y esta queda en la lista presente en la pantalla "Favoritas".
- Dirigirse a pantalla “Favoritas” a través del botón correspondiente: El usuario ingresa a pantalla "Favoritas" y esta se visualiza correctamente, mostrando las favoritas que ha dejado.
- Desplazarse horizontalmente por el mapa generado: El usuario se desplaza por el mapa generado y éste se muestra correctamente.
- Usar afinación de la lista de “Favoritas” para generar un mapa: El usuario selecciona una afinación de su lista de "Favoritas", la cual se carga correctamente en el Textfield de "Principal" y genera el mapa correctamente.
- Eliminar afinación de la lista de “Favoritas”: El usuario elimina una afinación de su lista de "Favoritas", y esta se quita de la lista correctamente.
- Volver desde la pantalla “Favoritas” a la “Principal”: El usuario regresa a la pantalla "Principal" y funciona correctamente.
- Desplegar lista de afinaciones recomendadas: El usuario despliega la lista de afinaciones recomendadas y estas se visualizan correctamente.
- Generar mapa con afinación que tenga notas medias (sostenidas): El usuario ingresa una afinación que contiene notas medias y el mapa se genera correctamente.
- Cerrar app y que se mantengan los datos guardados: El usuario cierra la app, al volver abrirla sus favoritas se mantiene, junto con todos los cambios que ha realizado.

-Caso de uso común
![image](https://github.com/user-attachments/assets/0467bca2-d693-4df0-aa6b-8c52bcc19ef0)


3.- Información técnica de la APP (Ej: Versiones de Android soportadas, permisos utilizados, dependencias instaladas)

- Versión mínima de Android: 8.1 (SDK 27)
- Permisos: N/A
- Dependencias: 
implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    val nav_version = "2.8.0"
    implementation("androidx.navigation:navigation-compose:$nav_version")

