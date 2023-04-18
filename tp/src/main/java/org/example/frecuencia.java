package org.example;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum frecuencia {

    DIARIA {
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaDiaria) {
            return fechaInicial.plusDays(frecuenciaDiaria);
        }
    },

    SEMANAL {
        /*diasSemana debe estar en orden segun el ordinal*/
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, DayOfWeek[] diasSemana,
                                             Integer frecuenciaSemanal) {
            DayOfWeek diaActual = fechaInicial.getDayOfWeek();
            LocalDateTime fechaProxima = null;
            for (int i = 0; i < diasSemana.length; i++) {
                var dia = diasSemana[i];
                if (diaActual == dia) {
                    if (i != diasSemana.length - 1) { // ok
                        fechaProxima = fechaInicial.plusDays(diasSemana[i+1].getValue() - diaActual.getValue());
                    } else {
                        fechaProxima = fechaInicial.plusDays((diasSemana[0].getValue() + diaActual.getValue()) % 8 + (frecuenciaSemanal - 1));
                    }
                }
            }
            return fechaProxima;
        }
    },

    MENSUAL {
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaMensual) {
            return fechaInicial.plusMonths(frecuenciaMensual);
        }
    },

    ANUAL {
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaAnual) {
            return fechaInicial.plusYears(frecuenciaAnual);
        }
    };


}
