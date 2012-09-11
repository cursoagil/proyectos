package es.cda.agenda.modelo;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.DAO.AgendaDAO;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.centralita.Centralita;
import es.cda.agenda.normalizadorFormatoTelefono.NormalizadorFormatoTelefono;
import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class AgendaTest
{
    Agenda agenda;

    VerificadorFormatoTelefono verificadorFormatoTelefono;
    NormalizadorFormatoTelefono normalizadorFormatoTelefono;
    AgendaDAO agendaDAO;
    PersonaDAO personaDAO;

    @Before
    public void init()
    {
        crearColaboradores();

        inyectarColaboradores();
    }

    private void crearColaboradores()
    {
        agenda = new Agenda();
        verificadorFormatoTelefono = mock(VerificadorFormatoTelefono.class);
        normalizadorFormatoTelefono = mock(NormalizadorFormatoTelefono.class);
        agendaDAO = mock(AgendaDAO.class);
        personaDAO = mock(PersonaDAO.class);
    }

    private void inyectarColaboradores()
    {
        Agenda.establecerVerificadorFormatoTelefono(verificadorFormatoTelefono);
        Agenda.establecerNormalizadorFormatoTelefono(normalizadorFormatoTelefono);
        Agenda.establecerAgendaDAO(agendaDAO);
        Agenda.establecerPersonaDAO(personaDAO);
    }

    @Test(expected = FormatoDeNumeroDeTelefonoIncorrectoException.class)
    public void agregarTelefonosVerificaFormato()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        when(verificadorFormatoTelefono.verifica(anyString())).thenReturn(false);

        agenda.agregarTelefono(anyString());

        verify(verificadorFormatoTelefono).verifica(anyString());
    }

    @Test
    public void agregarTelefonosGuardaFormatoDeTelefonoNormalizado()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        when(verificadorFormatoTelefono.verifica(anyString())).thenReturn(true);
        when(normalizadorFormatoTelefono.normaliza(anyString())).thenReturn("");

        agenda.agregarTelefono(anyString());

        verify(normalizadorFormatoTelefono).normaliza(anyString());
    }

    @Test
    public void agregarTelefonosLlamaAlMetodoDePersistencia()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        when(verificadorFormatoTelefono.verifica(anyString())).thenReturn(true);
        when(normalizadorFormatoTelefono.normaliza(anyString())).thenReturn("");

        agenda.agregarTelefono(anyString());

        verify(agendaDAO).agregarTelefono(any(Telefono.class));
    }

    @Test
    public void recuperarTelefonoLlamaAlMetodoDePersistencia()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException,
            NumeroDeTelefonoInexistenteException
    {
        agenda.recuperarTelefono(anyString());

        verify(agendaDAO).recuperarTelefono(anyString());
    }

    @Test
    public void agregarPersonaLlamaAlMetodoDePersistencia()
            throws NombreDePersonaRepetidoException
    {
        agenda.agregarPersona(anyString());

        verify(personaDAO).agregarPersona(any(Persona.class));
    }

    @Test
    public void recuperarPersonaLlamaAlMetodoDePersistencia()
            throws NombreDePersonaInexistenteException
    {
        agenda.recuperarPersona(anyString());

        verify(personaDAO).recuperarPersona(anyString());
    }
    
    @Test
    public void recuperarTelefonosDePersonaLlamaAlMetodoDePersistencia()
    {
        agenda.recuperarTelefonosDePersona(any(Persona.class));

        verify(agendaDAO).recuperarTelefonosDePersona(any(Persona.class));
    }

    @Test
    public void hacerLlamadaLlamaAlMetodoDeLaAPIDeCentralita()
    {
        Centralita centralita = crearCentralita();

        Telefono telefono = new Telefono("");

        agenda.hacerLlamada(telefono);

        verify(centralita).hacerLlamada(telefono);
    }

    @Test
    public void hacerLlamadaDeberiaComprobarQueSeHaPodidoHacerLaMarcacion()
    {
        Centralita centralita = crearCentralita();

        Telefono telefono = new Telefono("");

        when(centralita.hacerLlamada(telefono)).thenReturn(true);

        Assert.assertTrue(centralita.hacerLlamada(telefono));
    }

    private Centralita crearCentralita()
    {
        Centralita centralita = mock(Centralita.class);

        Agenda.estableceCentralita(centralita);

        return centralita;
    }
}
