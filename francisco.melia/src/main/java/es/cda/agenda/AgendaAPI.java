package es.cda.agenda;

import java.util.List;

import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;
import es.cda.agenda.modelo.Telefono;

public class AgendaAPI
{
    private static Agenda agenda;

    public static void establecerAgenda(Agenda agenda)
    {
        AgendaAPI.agenda = agenda;
    }

    public static Telefono agregarTelefono(String numeroTelefono)
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        return agenda.agregarTelefono(numeroTelefono);
    }

    public static Telefono recuperarTelefono(String numeroTelefono)
            throws NumeroDeTelefonoInexistenteException
    {
        return agenda.recuperarTelefono(numeroTelefono);
    }

    public static Boolean hacerLlamada(Telefono telefono)
    {
        return agenda.hacerLlamada(telefono);
    }

    public static Persona agregarPersona(String nombrePersona)
            throws NombreDePersonaRepetidoException
    {
        return agenda.agregarPersona(nombrePersona);
    }

    public static Persona recuperarPersona(String nombrePersona)
            throws NombreDePersonaInexistenteException
    {
        return agenda.recuperarPersona(nombrePersona);
    }

    public static void agregarTelefonoAPersona(Telefono telefono, Persona persona)
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        telefono.agregarPersona(persona);
    }

    public static List<Telefono> recuperarTelefonosDePersona(Persona persona)
    {
        return agenda.recuperarTelefonosDePersona(persona);
    }

    public static void establecerDireccionDePersona(Persona persona, String direccion)
            throws NombreDePersonaInexistenteException
    {
        persona.establecerYActualizarDireccion(direccion);
    }

    public static void establecerCorreoElectronicoDePersona(Persona persona,
            String correoElectronico) throws NombreDePersonaInexistenteException
    {
        persona.establecerYActualizarCorreoElectronico(correoElectronico);
    }
}
