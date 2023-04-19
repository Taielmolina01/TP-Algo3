package org.example;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum frecuencia {

    DIARIA {
        @Override
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaDiaria) {
            return fechaInicial.plusDays(frecuenciaDiaria);
        }
    },

    SEMANAL {

        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, DayOfWeek[] diasSemana, Integer frecuenciaSemanal) {
            DayOfWeek diaActual = fechaInicial.getDayOfWeek();
            LocalDateTime fechaProxima = null;
            for (int i = 0; i < diasSemana.length; i++) {
                var dia = diasSemana[i];
                if (diaActual == dia) {
                    if (i != diasSemana.length - 1) {
                        fechaProxima = fechaInicial.plusDays(diasSemana[i+1].getValue() - diaActual.getValue());
                    } else {
                        fechaProxima = fechaInicial.plusDays((diasSemana[0].getValue() + diaActual.getValue()) % 8 + (frecuenciaSemanal - 1));
                    }
                }
            }
            return fechaProxima;
        }
    },  /*diasSemana debe estar en orden segun el ordinal*/
    MENSUAL {
        @Override
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaMensual) {
            return fechaInicial.plusMonths(frecuenciaMensual);
        }
    },

    ANUAL {
        @Override
        public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaAnual) {
            return fechaInicial.plusYears(frecuenciaAnual);
        }
    };

    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaAnual) {
        return fechaInicial;
    }

    public LocalDateTime getProximaFecha(LocalDateTime fechaInicial, Integer frecuenciaSemanal,  DayOfWeek[] diasSemana) {
        return fechaInicial;
    }

}
