# TP-Algo3

Integrantes
  
Kriger, Lucas. Padrón: 109344

Molina, Taiel. Padrón: 109458

## TODO

- [X] Agregar lo de la visualizacion semanal (manejarse con enums).
- [X] Agregar lo de ver eventos/tareas del lapso pertinente.
- [X] Agregar lo del guardado y recuperado.
- [X] Ninguna ventana extra debe extender Application.
- [X] Actividades entre fechas estaba mal (hacer prototype?).
- [X] Agregar bien la funcionalidad de actividades entre fechas (rompe cuando se ingresa un dia inicial en el que no hay actividad).
- [X] Falta lo de crear las alarmas para los eventos que se repiten. 
- [X] Modificar lo de proxima alarma en Calendario. Deberia o bien guardarme la actividad en la alarma (mas no que si), o cuando devuelvo la proxima alarma, devolver
un Map<IDActividad, Alarma> cosa de poder mostrar un mensaje diciendo ALARMA NOTIFICACION de calendario.obtenerNombre(IDActividad) o algo así.
NOTA: utilizar **AbstractMap.SimpleEntry**.
- [X] Cuando se agrega un evento que se repite dsp no se puede cargar el programa cuando se vuelve abrir (cuando se agregan tareas no rompe) (QUE MIERDA PASA)
PD: Que retraso como no iba a fallar:))
- [X] Arreglar que cuando se crea una tarea/evento nueva se rompe el programa porque hay un problema con la actualizacion de vistaActividades.
- [X] Arreglar que funciona mal la logica de ver los días cuando un evento se repite.
- [X] Hacer que la vista reciba el objeto Tarea/el objeto Evento, lo del arrayList es una garcha (no se puede xd)
- [X] Cambiar los ifs a switchCase
- [X] Cambiar que cuando es dia completo el texto muestra la fecha con formmater sin horas y diga "de dia completo"
- [X] Cambiar lo del Visitor, ver clase diego 29/5 (en Slack tambien) (completado tiene que estar como box para seleccionar/deseleccionar).
- [X] Cambiar retorno de los visitadores a tipo void.
- [X] Arreglar lo del exception por no deseleccionar la celda antes de cambiar de rango.
- [ ] Arreglar lo del combobox del + Crear. (no tener que elegir la opcion nula para que se vuelva a elegir)
- [X] Asociar el checkbox de cada celda de una tarea a la tarea, para que cuando esté seleccionada el checkbox la tarea esté completada, si no no.
- [X] Cuando cargo todo deberían aparecer ya seleccionadas aquellas tareas que ya estén completadas.
- [X] Agregar disparado de alarmas. (la verga esa de timetasker no sirve, tengo que usar otra cosa)
- [X] Hacer un archivo css con todo (agregar hover de los textfield)
- [X] Agregar las otras frecuencias, haria un combobox que tenga por default marcado sin repeticion y que despues si se elije otra distinta entre las opciones de {repeticionDiaria, repeticionSemanal, repeticionMensual, repeticionAnual}. Todas lanzarian la misma ventana que la que ya esta, menos la de la repeticion semanal que tendria que hacer DayWeekPicker o algo así (por ahora lo mejor que se me ocurre es utilizar 7 checkbox que tengan arriba/abajo las letras L/M/M/J/V/S/D (o en ingles)), y dsp los checkbox reviso si estan seleccionados o no e utilizo los ordinal de DayOfWeek.
- [ ] Hacer archivo css con modo oscuro
- [ ] Agregar logica para switchear los modos.
- [ ] Separar en dos paquetes grandes: Logica de negocio y GUI.
- [ ] Emprolijar todo el codigo
- [ ] Hacer informe en un .md

## Informe

### Diseño



### Responsabilidades de las clases principales

- Actividad

Almacena el ID, nombre, descripcion, fecha de inicio, si es o no una actividad de todo el día y las alarmas de una actividad. Tiene sus getters y setters correspondientes a estos atributos mencionados.

- Tarea

Extiende Actividad, almacena además el estado de una tarea y tiene su getter y setter correspondiente a este.

- Evento

Extiende Actividad, almacena la fecha de fin del evento (el fin de las repeticiones), la duracion del evento y la frecuencia con la que se repite el evento.

- Alarma

Representa una alarma de una actividad, almacena la fecha en la que suena y el efecto que se desea que produzca. Dada una fecha puede decir cuanto tiempo falta para que suene.

- Frecuencia

Representa cada cuanto tiempo se repite un evento, existiendo 4 subclases que la extienden que indican de que tipo es la frecuencia: diaria, semanal, mensual y anual. Dada dos fechas puede indicar todas las repeticiones que hay de un evento.

- Calendario

    - Almacena una colección de actividades. 
    - Dada dos fechas puede indicar cual es la siguiente alarma existente en ese lapso de tiempo. 
    - Puede guardar y recuperar su estado.
    - Proporciona una fachada para los getters y setters de todas las actividades pertenecientes al mismo.
    
- VistaActividad

Dada una actividad se encarga de almacenar el color que mostrará la GUI para representarla, y setea la información resumida y la información completa que mostrará la GUI en el momento adecuado.

- AppCalendario

Es la GUI de la clase Calendario. Muestra en pantalla las actividades que hay en el lapso pertinente (mes, semana, día). Cada vez que se quiere crear una actividad nueva, lanza una ventana correspondiente para crear la actividad correspondiente, que a su vez cada ventana puede lanzar otras ventanas para ingresar algunos datos específicos, o para mostrar que hubo un error ingresando alguno.


### Patrones de diseño utilizados

- Prototype

Lo utilizan los eventos al momento de devolver las repeticiones del mismo en el método actividadesEntreFechas, en el cual además se hace un prototype de las alarmas
correspondientes a la actividad original, y se agregan sus clones, con las fechas de activación ajustadas, al evento clon.

- Facade

La clase Calendario es una fachada la cual muestra una interfaz más sencilla para el usuario al momento de crear Eventos y Tareas.

- Visitor

Hay dos visitor utilizados en el tp. El primero se utiliza para obtener las vistas de las actividades (vistaEvento y vistaTarea). El segundo se utiliza para obtener
la frecuencia de un evento, si es que la tiene.

- Observer

Se utiliza en la GUI para desde stages distintos al stage principal mandar mensajes al Calendario. Se ve en las ventanas de crear nuevas actividades, que reciben una interfaz de guardado para que una vez se valida que los datos ingresados para crear la actividad son válidos, se envía el mensaje para que se cree y se agregue la nueva actividad, además de luego realizar un nuevo guardado del estado actual del calendario.


### Diagrama de clases
