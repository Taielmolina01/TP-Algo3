package tp;

import org.junit.Test;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class CalendarioTest {

    @Test
    public void alarmasEnEventoyTarea() {
        Calendario nuevoCalendario = new Calendario();

        String nombreEvento = "Evento";
        String descripcionEvento = "descripcion del evento";
        LocalDateTime fechaInicioEvento = LocalDateTime.of(2023, 4, 30, 0, 0, 0);
        Duration duracion = Duration.ofHours(2);

        String nombreTarea = "Tarea";
        String descripcionTarea = "descripcion de la tarea";
        LocalDateTime fechaInicioTarea = LocalDateTime.of(2023, 4, 30, 0, 0, 0);

        nuevoCalendario.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        nuevoCalendario.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);

        nuevoCalendario.agregarAlarma(0, Alarma.Efecto.EMAIL, Duration.ofMinutes(30));
        nuevoCalendario.agregarAlarma(0, Alarma.Efecto.EMAIL, Duration.ofHours(2));

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0), nuevoCalendario.obtenerSiguienteAlarma().obtenerFechaActivacion());
        assertEquals(Alarma.Efecto.EMAIL, nuevoCalendario.obtenerSiguienteAlarma().dispararAlarma());

        nuevoCalendario.agregarAlarma(1, Alarma.Efecto.SONIDO, Duration.ofHours(3));

        assertEquals(LocalDateTime.of(2023, 4, 29, 21, 0, 0), nuevoCalendario.obtenerSiguienteAlarma().obtenerFechaActivacion());

        nuevoCalendario.eliminarAlarma(1, 0);

        assertEquals(LocalDateTime.of(2023, 4, 29, 22, 0, 0), nuevoCalendario.obtenerSiguienteAlarma().obtenerFechaActivacion());

        nuevoCalendario.modificarEfectoAlarma(0, 1, Alarma.Efecto.NOTIFICACION);
        assertEquals(Alarma.Efecto.NOTIFICACION, nuevoCalendario.obtenerSiguienteAlarma().dispararAlarma());
    }

    @Test
    public void testSetters() {
        Calendario nuevoCalendario = new Calendario();

        String nombreEvento = "Evento";
        String descripcionEvento = "descripcion del evento";
        LocalDateTime fechaInicioEvento = LocalDateTime.of(2023, 4, 1, 0, 0, 0);
        Duration duracion = Duration.ofHours(3);

        String nombreTarea = "Tarea";
        String descripcionTarea = "descripcion de la tarea";
        LocalDateTime fechaInicioTarea = LocalDateTime.of(2023, 4, 30, 0, 0, 0);

        nuevoCalendario.crearEvento(nombreEvento, descripcionEvento, fechaInicioEvento, duracion, false);
        nuevoCalendario.crearTarea(nombreTarea, descripcionTarea, fechaInicioTarea, false);

        assertEquals(nombreEvento, nuevoCalendario.obtenerNombre(0));
        assertEquals(nombreTarea, nuevoCalendario.obtenerNombre(1));

        String nuevaDescripcionEvento = "descripcion del evento actualizada";
        String nuevaDescripcionTarea = "descripcion de la tarea actualizada";


        nuevoCalendario.modificarDescripcion(0, nuevaDescripcionEvento);
        nuevoCalendario.modificarDescripcion(1, nuevaDescripcionTarea);

        assertEquals(nuevaDescripcionEvento, nuevoCalendario.obtenerDescripcion(0));
        assertEquals(nuevaDescripcionTarea, nuevoCalendario.obtenerDescripcion(1));
    }

    public static class AlarmaTest {

        @Test
        public void crearAlarmaFechaAbsolutaTest() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
            var otraFecha = LocalDateTime.of(1, 1, 1, 1, 1);
            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);
            assertEquals(fechaAbsoluta, alarma.obtenerFechaActivacion());
        }

        @Test
        public void testCrearAlarmaTiempoRelativo() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
            var fechaDisparador = LocalDateTime.of(2019, 12, 31, 23, 30);
            var tiempo = Duration.of(30, ChronoUnit.MINUTES);
            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta, tiempo);
            assertEquals(fechaDisparador, alarma.obtenerFechaActivacion());
        }

        @Test
        public void testCuantoFaltaAntesDeFechaAlarma() {
            var fechaAntes = LocalDateTime.of(2020, 1, 5, 0, 0, 0, 0);
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

            assertEquals(5, alarma.cuantoFaltaParaDisparar(fechaAntes).toDays());

            assertTrue(alarma.cuantoFaltaParaDisparar(fechaAntes).isPositive());
            assertFalse(alarma.cuantoFaltaParaDisparar(fechaAntes).isZero());
            assertFalse(alarma.cuantoFaltaParaDisparar(fechaAntes).isNegative());
        }

        @Test
        public void testCuantoFaltaEnFechaAlarma() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);

            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

            assertEquals(0, alarma.cuantoFaltaParaDisparar(fechaAbsoluta).toDays());

            assertFalse(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isPositive());
            assertTrue(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isZero());
            assertFalse(alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isNegative());
        }

        @Test
        public  void testCuantoFaltaDespuesDeFechaAlarma() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 0, 0, 0, 0);
            var fechaDespues = LocalDateTime.of(2020, 1, 15, 0, 0, 0, 0);

            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

            assertEquals(-5, alarma.cuantoFaltaParaDisparar(fechaDespues).toDays());

            assertFalse(alarma.cuantoFaltaParaDisparar(fechaDespues).isPositive());
            assertFalse(alarma.cuantoFaltaParaDisparar(fechaDespues).isZero());
            assertTrue(alarma.cuantoFaltaParaDisparar(fechaDespues).isNegative());
        }

        @Test
        public void testDispararAlarmaNotificacion() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);
            assertEquals(Alarma.Efecto.NOTIFICACION, alarma.dispararAlarma());
        }

        @Test
        public void testDispararAlarmaSonido() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
            var alarma = new Alarma(Alarma.Efecto.SONIDO, fechaAbsoluta);
            assertEquals(Alarma.Efecto.SONIDO, alarma.dispararAlarma());
        }

        @Test
        public void testDispararAlarmaEmailTest() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
            var alarma = new Alarma(Alarma.Efecto.EMAIL, fechaAbsoluta);
            assertEquals(Alarma.Efecto.EMAIL, alarma.dispararAlarma());
        }

        @Test
        public void intentarDispararAlarmaAntesDeFechaTest() {
            var fechaAntes = LocalDateTime.of(2020, 1, 5, 00, 0, 0, 0);
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 00, 0, 0, 0);

            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

            if (alarma.cuantoFaltaParaDisparar(fechaAntes).isPositive()) {
                assertTrue(true);
            } else if (alarma.cuantoFaltaParaDisparar(fechaAntes).isZero()) {
                assertTrue(false);
            } else if (alarma.cuantoFaltaParaDisparar(fechaAntes).isNegative()) {
                assertTrue(false);
            }
        }

        @Test
        public void intentarDispararAlarmaEnFechaTest() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 1, 00, 0, 0, 0);

            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

            if (alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isPositive()) {
                assertTrue(false);
            } else if (alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isZero()) {
                assertEquals(Alarma.Efecto.NOTIFICACION, alarma.dispararAlarma());
            } else if (alarma.cuantoFaltaParaDisparar(fechaAbsoluta).isNegative()) {
                assertTrue(false);
            }
        }

        @Test
        public void intentarDispararAlarmaDespuesDeFechaTest() {
            var fechaAbsoluta = LocalDateTime.of(2020, 1, 10, 00, 0, 0, 0);
            var fechaDespues = LocalDateTime.of(2020, 1, 15, 00, 0, 0, 0);

            var alarma = new Alarma(Alarma.Efecto.NOTIFICACION, fechaAbsoluta);

            if (alarma.cuantoFaltaParaDisparar(fechaDespues).isPositive()) {
                assertTrue(false);
            } else if (alarma.cuantoFaltaParaDisparar(fechaDespues).isZero()) {
                assertTrue(false);
            } else if (alarma.cuantoFaltaParaDisparar(fechaDespues).isNegative()) {
                assertTrue(true);
            }
        }

        @Test
        public void modificarAlarmas() {
            var fechaAbsoluta = LocalDateTime.of(2023, 5, 10, 0, 0, 0, 0);
            var fechaDisparador = LocalDateTime.of(2023, 5, 9, 23, 30, 0);
            var tiempo = Duration.of(30, ChronoUnit.MINUTES);
            var efecto = Alarma.Efecto.NOTIFICACION;
            var alarma = new Alarma(efecto, fechaAbsoluta, tiempo);

            assertEquals(fechaDisparador, alarma.obtenerFechaActivacion());
            assertEquals(efecto, alarma.dispararAlarma());

            var fechaNueva = LocalDateTime.of(2023, 5, 8, 0, 0, 0);
            var efectoNuevo = Alarma.Efecto.SONIDO;

            alarma.modificarFechaActivacion(fechaNueva);
            alarma.modificarEfecto(efectoNuevo);

            assertEquals(fechaNueva, alarma.obtenerFechaActivacion());
            assertEquals(efectoNuevo, alarma.dispararAlarma());
        }
    }

    public static class Alarma implements Serializable {

        private Efecto efectoProducido;
        private LocalDateTime fechaActivacion;

        public enum Efecto {
            NOTIFICACION,
            SONIDO,
            EMAIL,
        }

        public Alarma(Efecto efectoProducido, LocalDateTime fechaArbitraria, Duration intervaloTiempo) {
            this.efectoProducido = efectoProducido;
            this.establecerFechas(fechaArbitraria, intervaloTiempo);
        }

        public Alarma(Efecto efectoProducido, LocalDateTime fechaAbsoluta) {
            this.efectoProducido = efectoProducido;
            this.establecerFechas(fechaAbsoluta);
        }

        public Duration cuantoFaltaParaDisparar(LocalDateTime fechaActual) {
            return Duration.between(fechaActual, this.fechaActivacion);
        }

        public Efecto dispararAlarma() {
            return this.efectoProducido;
        }

        public LocalDateTime obtenerFechaActivacion() {
            return this.fechaActivacion;
        }

        protected static int compararAlarmas(Alarma alarma1, Alarma alarma2) {
            var fechaActivacion1 = alarma1.obtenerFechaActivacion();
            var fechaActivacion2 = alarma2.obtenerFechaActivacion();
            if (fechaActivacion1.isEqual(fechaActivacion2)) {
                return 0;
            } else if (fechaActivacion2.isBefore(fechaActivacion1)) {
                return 1;
            } else {
                return -1;
            }
        }

        public void modificarEfecto(Efecto nuevoEfecto) {
            this.efectoProducido = nuevoEfecto;
        }

        public void modificarFechaActivacion(LocalDateTime fechaArbitrariaNueva, Duration intervaloTiempoNuevo) {
            this.establecerFechas(fechaArbitrariaNueva, intervaloTiempoNuevo);
        }

        public void modificarFechaActivacion(LocalDateTime fechaAbsolutaNueva) {
            this.establecerFechas(fechaAbsolutaNueva);
        }

        private void establecerFechas(LocalDateTime fechaArbitraria, Duration intervaloTiempo) {
            this.fechaActivacion = fechaArbitraria.minus(intervaloTiempo);
        }

        private void establecerFechas(LocalDateTime fechaAbsoluta) {
            this.fechaActivacion = fechaAbsoluta;
        }
    }
}