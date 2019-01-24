# Trabajo Final de Administración de Redes y Seguridad: "Autenticación Multifactor"
## Licenciatura en Sistemas 
## UNPSJB - Facultad de Ingeniería - Sede Trelew 

Este trabajo tiene como objetivos explicar qué es la autenticación y sus distintos factores, haciendo hincapié en las diferencias entre autenticación simple y autenticación multifactor, por qué es recomendable usarla, y cómo activarla en algunos servicios públicos. 

Además se explican los distintos problemas que existen al utilizar las contraseñas como único factor de autenticación, dando
ejemplos de casos reales de robos masivos de contraseñas. Se define qué es un gestor de contraseñas y hasta qué punto es útil
utilzarlo, su seguridad y sus funcionalidades. Se mencionan ciertos sitios que nos permiten saber si nuestras credenciales
han sido robadas, y otros sitios que nos permiten evaluar la fortaleza de las contraseñas. 

Se detalle la autenticación haciendo uso de OTPs (One Time Passwords): qué es, cómo se generan, cómo se distribuyen, su uso como segundo factor de autenticación y las estandarizaciones existentes, principalmente HOTP y TOTP, definidas en la RFC 4226 y la RFC 6238 respectivamente. Se describe de forma general el funcionamiento de estos dos algoritmos de autenticación.

Se ha desarrollado una aplicación de escritorio utilizando el lenguaje Java y el IDE Netbeans 8.2 que implementa los estándares HOTP y TOTP. Presenta una pantalla principal, donde el usuario puede elegir entre registrarse o iniciar sesión. 
Cuando el usuario inicia sesión, se le da la posibilidad de habilitar la autenticación multifactor. El segundo factor de autenticación es un factor de posesión. El usuario deberá escanear el código QR a través de alguna app de autenticación, como Duo, la cual generará los códigos OTP. 

## Pantalla Principal.
<img src="/Imagenes/principal.png" alt="Pantalla Principal"/>

## Registrar Usuario.
<img src="/Imagenes/registrar_usuario.png" alt="Registrar Usuario"/>

## Primer Factor de Autenticación: solicitud de contraseña.
<img src="/Imagenes/registrar_usuario.png" alt="1er factor"/>

## Segundo Factor de Autenticación: solicitud de OTP.
<img src="/Imagenes/solicitud_otp.png" alt="2do factor"/>

**Contenido de las Carpetas**
- **Base de Datos:** contiene un backup de la base de datos "logina2f" en PostgreSQL.
- **Proyecto:** contiene la aplicación Java para Netbeans que implementa los estándares HOTP y TOTP.
- **Implementacion_python.ipynb:** aplicación python en Jupyter Lab que permite generar distintas URIs codificadas en QRs siguiendo los parámetros definidos por OATH para comparar distintas aplicaciones de autenticación.

El resto de las carpetas no son relevantes.

**Alumno:**
- Parra, Iván Javier.

