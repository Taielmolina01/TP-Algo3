package org.example;
/*
    Los eventos pueden ser:
        - De día completo.
        - Comenzar en una fecha y hora y tener una duración arbitrarios. En ambos casos, el evento puede comenzar en un día y terminar en otro.

    Los eventos se pueden repetir:
        - Con frecuencia diaria, semanal, mensual o anual.
        - En caso de frecuencia diaria, es posible definir un intervalo (ej: “cada 3 días”).
        - En caso de frecuenia semanal, es posible definir los días de la semana (ej: “todos los martes y jueves”).
        - La repetición puede ser:
            + Infinita.
            + Terminar en una fecha determinada (ej: hasta el 13 de enero).
            + Terminar luego de una cantidad de repeticiones dada (ej: luego de 20 ocurrencias).
        - Al modificar o eliminar un evento con repetición, el cambio o eliminación se aplica a todas sus repticiones.

    En un evento o tarea se pueden configurar una o más alarmas:
        - La alarma se dispara en un instante de tiempo, que se puede determinar de dos maneras:
             1. Una fecha y hora absoluta.
             2. Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej: “30 minutos antes”).
        - El efecto producido al dispararse la alarma es configurable:
              1. Mostrar una notificación.
              2. Reproducir un sonido.
              3. Enviar un email.
              Nota: dado que en la primera etapa no se implementa la interacción con el usuario, no se deben implementar los
              efectos de las alarmas; pero sí deben tener pruebas asociadas.


*/
public class Evento {
    private String nombre;
    private String descripcion;

    public Evento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }

}
