package es.cda.agenda.modelo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.DAO.AgendaDAO;

public class TelefonoTest
{
    Telefono telefono;
    Persona persona;
    AgendaDAO agendaDAO;

    @Before
    public void inicializar() throws NumeroDeTelefonoInexistenteException,
            NombreDePersonaRepetidoException
    {
        telefono = new Telefono("964964964");
        persona = new Persona("pepe");
        
        agendaDAO = mock(AgendaDAO.class);
        telefono.establecerAgendaDAO(agendaDAO);
        telefono.agregarPersona(persona);
    }

    @Test
    public void agregarPersonaLlamaAlMetodoDePersistencia()
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        verify(agendaDAO).actualizar(telefono);
    }

    @Test(expected = NombreDePersonaRepetidoException.class)
    public void agregarPersonaCompruebaSilaPersonaYaEstabaAgregada()
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        telefono.agregarPersona(persona);
    }

    @Test
    public void seRecuperaUnaPersonaDespuesDeAgregarlaAUnTelefono()
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        Persona personaRecuperada = telefono.recuperarListaDePersonas().get(0);

        Assert.assertEquals(persona.recuperarNombre(), personaRecuperada.recuperarNombre());
    }
    
    @Test
    public void dosTelefonosSonIgualesSiCoincideSuNumeroNormalizado()
    {
        Telefono otroTelefono = new Telefono("999999999");

        Assert.assertFalse(telefono.sonIguales(otroTelefono));

        Assert.assertTrue(telefono.sonIguales(telefono));
    }
}
