package es.cda.agenda.integracion;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.DAO.AgendaDAO;
import es.cda.agenda.DAO.AgendaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;
import es.cda.agenda.modelo.Telefono;
import es.cda.agenda.normalizadorFormatoTelefono.NormalizadorFormatoTelefono;
import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class AgregarYRecuperarPersonasDeTelefonoTest
{
    Agenda agenda;
    AgendaDAO agendaDAO;

    String nombreDeLaPersonaAAgregar = "Juan Garcia";
    String primerNumeroTelefonoAgregadoNormalizado = "964964964";
    String segundoNumeroTelefonoAgregadoNormalizado = "964252525";
    
    Persona personaAAgregar;

    @Before
    public void inicializarEntorno() throws FormatoDeNumeroDeTelefonoIncorrectoException,
            NumeroDeTelefonoRepetidoException, NumeroDeTelefonoInexistenteException,
            NombreDePersonaRepetidoException
    {
        agenda = new Agenda();
        agendaDAO = new AgendaDAOEnMemoria();
        VerificadorFormatoTelefono verificadorFormatoTelefono = new VerificadorFormatoTelefono();
        NormalizadorFormatoTelefono normalizadorFormatoTelefono = new NormalizadorFormatoTelefono();

        Agenda.establecerAgendaDAO(agendaDAO);
        Agenda.establecerNormalizadorFormatoTelefono(normalizadorFormatoTelefono);
        Agenda.establecerVerificadorFormatoTelefono(verificadorFormatoTelefono);
    }

    @Test
    public void agregarUnaPersonaAUnTelefonoLaPersisteParaPoderRecuperarla()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException,
            NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        Telefono telefonoAgregado = agregarTelefono(primerNumeroTelefonoAgregadoNormalizado);

        agregarPersonaATelefono(telefonoAgregado);

        Persona personaRecuperada = recuperarPersonaAgregada(primerNumeroTelefonoAgregadoNormalizado);

        Assert.assertTrue(personaRecuperada.sonIguales(nombreDeLaPersonaAAgregar));
    }

    @Test
    public void recuperarTelefonosDeUnaPersona()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException,
            NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        Telefono primerTelefonoAgregado = agregarTelefono(primerNumeroTelefonoAgregadoNormalizado);
        Telefono segundoTelefonoAgregado = agregarTelefono(segundoNumeroTelefonoAgregadoNormalizado);

        agregarPersonaATelefono(primerTelefonoAgregado);
        agregarPersonaATelefono(segundoTelefonoAgregado);
        
        List<Telefono> telefonosDeLaPersonaRecuperados = agenda.recuperarTelefonosDePersona(personaAAgregar);
        
        Assert.assertTrue(primerTelefonoAgregado.sonIguales(telefonosDeLaPersonaRecuperados.get(0)));
        Assert.assertTrue(segundoTelefonoAgregado.sonIguales(telefonosDeLaPersonaRecuperados.get(1)));
    }

    private Persona recuperarPersonaAgregada(String numeroDeTelefonoAgregadoNormalizado)
            throws NumeroDeTelefonoInexistenteException
    {
        Telefono telefonoRecuperado = agenda
                .recuperarTelefono(primerNumeroTelefonoAgregadoNormalizado);

        Persona personaRecuperada = telefonoRecuperado.recuperarListaDePersonas().get(0);

        return personaRecuperada;
    }

    private void agregarPersonaATelefono(Telefono telefonoAgregado)
            throws NumeroDeTelefonoInexistenteException, NombreDePersonaRepetidoException
    {
        personaAAgregar = new Persona(nombreDeLaPersonaAAgregar);

        telefonoAgregado.agregarPersona(personaAAgregar);
    }

    private Telefono agregarTelefono(String numeroDeTelefonoAAgregarNormalizado)
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        Telefono telefonoAgregado = agenda.agregarTelefono(numeroDeTelefonoAAgregarNormalizado);
        telefonoAgregado.establecerAgendaDAO(agendaDAO);

        return telefonoAgregado;
    }
}
