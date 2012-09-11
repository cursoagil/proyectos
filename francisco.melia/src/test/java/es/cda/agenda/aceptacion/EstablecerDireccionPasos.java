package es.cda.agenda.aceptacion;

import junit.framework.Assert;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import es.cda.agenda.AgendaAPI;
import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.DAO.PersonaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;

public class EstablecerDireccionPasos
{
    Agenda agenda;

    Persona persona;
    PersonaDAO personaDAO;
 
    String direccionEstablecida;

    public EstablecerDireccionPasos()
    {
        agenda = new Agenda();
        AgendaAPI.establecerAgenda(agenda);

        personaDAO = new PersonaDAOEnMemoria();
        Persona.establecerPersonaDAO(personaDAO);
        Agenda.establecerPersonaDAO(personaDAO);
    }

    @Given("Yo tengo una persona")
    public void crearPersona() throws NombreDePersonaRepetidoException
    {
        persona = agenda.agregarPersona("persona");
    }

    @When("Yo establezco a la persona la direccion: $direccion")
    public void establecerDireccion(String direccion) throws NombreDePersonaInexistenteException
    {
        direccionEstablecida = direccion;
        AgendaAPI.establecerDireccionDePersona(persona, direccion);
    }

    @Then("La persona tiene asociada esa direccion")
    public void comprobarDireccion()
    {
        String direccionDeLaPersona = persona.recuperarDireccion();
        Assert.assertEquals(direccionDeLaPersona, direccionEstablecida);
    }
}
