# TP-Algo3

Integrantes
  
Kriger, Lucas. Padrón: 109344

Molina, Taiel. Padrón: 109458

## Notas

nota1: lo de agregar las alarmas esta funcionando mal, deberia por cada alarma A del evento E original, calcular su duracion (la dif entre la hora que suena
y la hora del evento) y luego agregar en las repeticiones de E las alarmas (con el mismo ID) con la misma dif de horas.

nota2: no entiendo por que mierda rompe cuando se agrega un evento, me estoy guardando el hash entero, no tiene sentido.

## TODO


- [X] Agregar lo de la visualizacion semanal (manejarse con enums).
- [X] Agregar lo de ver eventos/tareas del lapso pertinente.
- [X] Agregar lo del guardado y recuperado.
- [X] Ninguna ventana extra debe extender Application.
- [X] Actividades entre fechas estaba mal (hacer prototype?).
- [X] Agregar bien la funcionalidad de actividades entre fechas (rompe cuando se ingresa un dia inicial en el que no hay actividad).
- [X] Falta lo de crear las alarmas para los eventos que se repiten. (Si un evento tiene una alarma configurada dos horas antes de un evento habria que ponerla dos 
horas antes de cada repeticion?). (En progreso pero es una poronga y no funciona).
- [ ] Modificar lo de proxima alarma en Calendario.
- [ ] Cuando se agrega un evento dsp no se puede cargar el programa cuando se vuelve abrir (cuando se agregan tareas no rompe) (QUE MIERDA PASA)
- [ ] Arreglar que cuando se crea una tarea/evento nueva se rompe el programa porque hay un problema con la actualizacion de vistaActividades.
- [ ] Cambiar que cuando es dia completo el texto muestra la fecha con formmater sin horas y diga "de dia completo"
- [X] Cambiar lo del Visitor, ver clase diego 29/5 (en Slack tambien) (completado tiene que estar como box para seleccionar/deseleccionar).
- [ ] Agregar disparado de alarmas. (la verga esa de timetasker no sirve, tengo que usar otra cosa)
- [ ] Hacer un archivo css con todo (agregar hover de los textfield)
- [ ] Separar en dos paquetes grandes: Logica de negocio y GUI.
- [ ] Modo claro/oscuro.
 
