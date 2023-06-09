# TP-Algo3

Integrantes
  
Kriger, Lucas. Padrón: 109344

Molina, Taiel. Padrón: 109458

## Notas

nota2: no entiendo por que mierda rompe cuando se agrega un evento CON REPETICION, me estoy guardando el hash entero, no tiene sentido.

## TODO


- [X] Agregar lo de la visualizacion semanal (manejarse con enums).
- [X] Agregar lo de ver eventos/tareas del lapso pertinente.
- [X] Agregar lo del guardado y recuperado.
- [X] Ninguna ventana extra debe extender Application.
- [X] Actividades entre fechas estaba mal (hacer prototype?).
- [X] Agregar bien la funcionalidad de actividades entre fechas (rompe cuando se ingresa un dia inicial en el que no hay actividad).
- [X] Falta lo de crear las alarmas para los eventos que se repiten. 
- [ ] Modificar lo de proxima alarma en Calendario. Deberia o bien guardarme la actividad en la alarma (mas no que si), o cuando devuelvo la proxima alarma, devolver
un Map<IDActividad, Alarma> cosa de poder mostrar un mensaje diciendo ALARMA NOTIFICACION de calendario.obtenerNombre(IDActividad) o algo así.
NOTA: utilizar **AbstractMap.SimpleEntry**.
- [ ] Cuando se agrega un evento que se repite dsp no se puede cargar el programa cuando se vuelve abrir (cuando se agregan tareas no rompe) (QUE MIERDA PASA)
- [ ] Arreglar que cuando se crea una tarea/evento nueva se rompe el programa porque hay un problema con la actualizacion de vistaActividades.
- [X] Cambiar que cuando es dia completo el texto muestra la fecha con formmater sin horas y diga "de dia completo"
- [X] Cambiar lo del Visitor, ver clase diego 29/5 (en Slack tambien) (completado tiene que estar como box para seleccionar/deseleccionar).
- [ ] Asociar el checkbox de cada celda de una tarea a la tarea, para que cuando esté seleccionada el checkbox la tarea esté completada, si no no.
        - [ ] Cuando cargo todo deberían aparecer ya seleccionadas aquellas tareas que ya estén completadas.
- [ ] Agregar disparado de alarmas. (la verga esa de timetasker no sirve, tengo que usar otra cosa)
- [ ] Hacer un archivo css con todo (agregar hover de los textfield)
- [ ] Separar en dos paquetes grandes: Logica de negocio y GUI.
- [ ] Modo claro/oscuro.
 
