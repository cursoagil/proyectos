Pasos para seguir la integración continua del proyecto.
=======================================================

1.  Usar maven como sistema de descarga de las dependencias del proyecto 
    y herramienta de construcción.
2.  Usar git como sistema de control de versiones.
3.  Tener un ordenador totalmente dedicado para la integración continua con 
    Sonar y Jenkins ejecutándose en él.
4.  Tener otro ordenador totalmente dedicado como entorno de "staging".
5.  Configurar jenkins para que construya la build por cada push hecho 
    al repositorio del proyecto. El proyecto se construirá en 
    la fase "integration-test"
6.  Dentro de la fase "integration-test" de maven se comprobará que el proyecto pasa 
    las pruebas de aceptación y se ejecutarán los controles de inspección configurados
    en el POM del proyecto. 
7.  Añadir en jenkins una acción cuando falle la build para mandar correos 
    a todos los participantes en el correo, indicando el commit culpable de la
    ruptura de la build. Él invitará al almuerzo del viernes.
8.  Evitar el uso de valores "hard-coded" en el código, utilizando para ello 
    ficheros .properties.
    Cada uno deberá ser responsable de utilizar sus propios .properties 
    en sus máquinas y de no subirlo al repositorio compartido, dejando 
    en ellos los valores necesarios para el proceso de integración continua.
9.  Añadir un pre-commit hook en el repositorio (que deberán ejecutar los 
    desarrolladores del proyecto) para que git ejecute un "mvn test" antes de 
    permitir hacer commit, evitando así commits que no pasen tests unitarios.
10. A la tarde de la jornada, dos de los desarrolladores del equipo revisarán
    las últimas violaciones del Sonar, además del aumento de complejidad que va
    tomando el proyecto, para atacar estas partes en un refactoring el día
    siguiente.
