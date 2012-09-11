package es.cda.agenda;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.AgendaAPI;
import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;
import es.cda.agenda.modelo.Telefono;

public class AgendaAPITest
{
    Persona persona = mock(Persona.class);
    Agenda agenda = mock(Agenda.class);

    @Before
    public void init()
    {
        AgendaAPI.establecerAgenda(agenda);
    }

    @Test
    public void agregarTelefonoLlamaAlMetodoDelModelo()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        AgendaAPI.agregarTelefono(anyString());

        verify(agenda).agregarTelefono(anyString());
    }

    @Test
    public void recuperarTelefonoLlamaAlMetodoDelModelo()
            throws NumeroDeTelefonoInexistenteException
    {
        AgendaAPI.recuperarTelefono(anyString());

        verify(agenda).recuperarTelefono(anyString());
    }

    @Test
    public void agregarPersonaLlamaAlMetodoDelModelo() throws NombreDePersonaRepetidoException
    {
        AgendaAPI.agregarPersona(anyString());

        verify(agenda).agregarPersona(anyString());
    }

    @Test
    public void recuperarPersonaLlamaAlMetodoDelModelo() throws NombreDePersonaInexistenteException
    {
        AgendaAPI.recuperarPersona(anyString());

        verify(agenda).recuperarPersona(anyString());
    }

    @Test
    public void agregarTelefonoAPersonaLlamaAlMetodoDelModelo()
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        Telefono telefono = mock(Telefono.class);

        AgendaAPI.agregarTelefonoAPersona(telefono, any(Persona.class));

        verify(telefono).agregarPersona(any(Persona.class));
    }
    
    @Test
    public void recuperarTelefonosDePersonaLlamaAlMetodoDelModelo()
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        AgendaAPI.recuperarTelefonosDePersona(any(Persona.class));

        verify(agenda).recuperarTelefonosDePersona(any(Persona.class));
    }

    @Test
    public void establecerDireccionLlamaAlMetodoDelModelo()
            throws FormatoDeNumeroDeTelefonoIncorrectoException,
            NombreDePersonaInexistenteException
    {
        AgendaAPI.establecerDireccionDePersona(persona, new String());

        verify(persona).establecerYActualizarDireccion(anyString());
    }

    @Test
    public void establecerCorreoElectronicoLlamaAlMetodoDelModelo()
            throws FormatoDeNumeroDeTelefonoIncorrectoException,
            NombreDePersonaInexistenteException
    {
        AgendaAPI.establecerCorreoElectronicoDePersona(persona, new String());

        verify(persona).establecerYActualizarCorreoElectronico(anyString());
    }

    @Test
    public void hacerLlamadaLlamaAlMetodoDelModelo()
    {
        Telefono telefono = new Telefono("");

        AgendaAPI.hacerLlamada(telefono);

        verify(agenda).hacerLlamada(telefono);
    }
}
