package es.cda.agenda.integracion;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.DAO.PersonaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;

public class AgregarPersonaTest
{
    Agenda agenda;
    PersonaDAO personaDAO;
    String nombreDePersonaAAgregar;

    @Before
    public void init()
    {
        agenda = new Agenda();
        personaDAO = new PersonaDAOEnMemoria();

        Agenda.establecerPersonaDAO(personaDAO);
    }

    @Test(expected = NombreDePersonaRepetidoException.class)
    public void agregarPersomaVerificaSiEstabaRepetida()
            throws NombreDePersonaRepetidoException
    {
        nombreDePersonaAAgregar = "Pepe Tous";

        agenda.agregarPersona(nombreDePersonaAAgregar);
        agenda.agregarPersona(nombreDePersonaAAgregar);
    }

    @Test(expected = NombreDePersonaInexistenteException.class)
    public void recuperarPersonaVerificaQueLaPersonaExiste()
            throws NombreDePersonaInexistenteException
    {
        agenda.recuperarPersona("Joan Serra");
    }

    @Test
    public void siInsertamosUnaPersonaSePersisteParaPoderRecuperarla()
            throws NombreDePersonaInexistenteException, NombreDePersonaRepetidoException
    {
        nombreDePersonaAAgregar = "Pepe Tous";

        agenda.agregarPersona(nombreDePersonaAAgregar);

        Persona personaRecuperada = agenda.recuperarPersona(nombreDePersonaAAgregar);

        Assert.assertTrue(personaRecuperada.sonIguales(nombreDePersonaAAgregar));
    }
}
