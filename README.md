# TP-Algo3

Integrantes
  
Kriger, Lucas. Padrón: 109344

Molina, Taiel. Padrón: 109458

## Requerimientos etapa 1

1. En un **calendario** se pueden crear, modificar y eliminar **eventos** y **tareas**.
2. Tanto los **eventos** como **tareas** pueden tener un **título** y una **descripción**.
3. Las **tareas** pueden marcarse como **completadas**.
4. Los **eventos** pueden ser:
      1. De día completo.
      2. Comenzar en una fecha y hora y tener una duración arbitrarios.
      En ambos casos, el evento puede comenzar en un día y terminar en otro.

5. Las **tareas** no tienen duración, pudiendo ser:
      1. De día completo.
      2. Tener una fecha y hora de vencimiento.
6. Los **eventos** se pueden **repetir**:
      1. Con frecuencia diaria, semanal, mensual o anual.
      2. En caso de frecuencia diaria, es posible definir un intervalo (ej: “cada 3 días”).
      3. En caso de frecuencia semanal, es posible definir los días de la semana (ej: “todos los martes y jueves”).
      4. La repetición puede ser:
         1. Infinita.
         2. Terminar en una fecha determinada (ej: hasta el 13 de enero).
         3. Terminar luego de una cantidad de repeticiones dada (ej: luego de 20 ocurrencias).
            
      5. Al modificar o eliminar un evento con repetición, el cambio o eliminación se aplica a todas sus repeticiones.
7. En un **evento** o **tarea** se pueden configurar una o más **alarmas**:
      1. La alarma se dispara en un instante de tiempo, que se puede determinar de dos maneras:

                1. Una fecha y hora absoluta.
                2. Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej: “30 minutos antes”).
      2. El efecto producido al dispararse la alarma es configurable:

                1. Mostrar una notificación.
                2. Reproducir un sonido.
                3. Enviar un email.
                Nota: dado que en la primera etapa no se implementa la interacción con el usuario, no se deben implementar los 
                efectos de las alarmas; pero sí deben tener pruebas asociadas.
                
                
### Notas Evento

Hay varios escenarios posibles a la hora de crear un evento:

Según su repetición:
    + De única vez.
    + Repetición diaria (todos los días o cada tantos dias).
    + Repeticion semanal (todos los martes y jueves por ej).
    + Repeticion mensual (todos los 10 de cada mes).
    + Repeticion anual.

Según su fechaFinDefinitivo:
    + Dada su fecha de fin.
    + Dada la cantidad de veces que se repite el evento (20 repeticiones).
    
A su vez se pueden combinar las primeras cuatro condiciones de repeticion con cualquiera de las ultimas dos condiciones de fechaFinDefinitivo.

#### Ideas

Repeticion:

Si es de unica vez, la fechaFinDefinitivo == fechaFin.
Repeticion diaria le paso un Integer al constructor, y ya se que es cada esa cantidad de dias.
Repeticion semanal paso un []String con los días en los que se repite el Evento (ej: ["lunes", "miercoles"]) entonces el evento se repetiria todos los lunes y miercoles de cada semana.
Repeticion semanal paso un Integer que sea 30 y asumo que es todos los meses ?
Repeticion anual paso Integer 365 y asumo que es una vez al año ? 

Frecuencia:

.......


Cosas a implementar:

- Si se repite infinitamente poner en fechaFinalDefinitivo como MAX de LocalDateTime (creo que es LocalDateTime.MAX)
- Para ver si hay eventos antes o despues utilizar isAfter/isBefore
- Manejarme con un constructor con un Integer para decir cada cuantos dias y otro con strings que simplmente diga
  los dias de la semana en los que se repite.
