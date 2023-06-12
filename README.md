# TP-Algo3

Integrantes
  
Kriger, Lucas. Padrón: 109344

Molina, Taiel. Padrón: 109458

## Notas

nota1: tiene sentido mostrar el ID?

nota2: avance un poco con lo de el esta completada y demas, pero por alguna razon cuando cargo de nuevo el programa o cuando cambio de mes actual, se vuelve a setear
que no esta completada cuando en un inicio me lo detecta bien.

nota3: deberia hacer que las vistas reciban el objeto entero? y despues en cada uno casteo a Evento o a Tarea segun corresponda.

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
- [ ] Asociar el checkbox de cada celda de una tarea a la tarea, para que cuando esté seleccionada el checkbox la tarea esté completada, si no no.
- [ ] Cuando cargo todo deberían aparecer ya seleccionadas aquellas tareas que ya estén completadas.
- [X] Agregar disparado de alarmas. (la verga esa de timetasker no sirve, tengo que usar otra cosa)
- [X] Hacer un archivo css con todo (agregar hover de los textfield)
- [ ] Hacer archivo css con modo oscuro
- [ ] Agregar logica para switchear los modos.
- [ ] Separar en dos paquetes grandes: Logica de negocio y GUI.
- [ ] Emprolijar todo el codigo
- [ ] Hacer informe en un .md
 
