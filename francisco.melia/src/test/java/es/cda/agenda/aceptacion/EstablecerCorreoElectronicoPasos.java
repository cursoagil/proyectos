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

public class EstablecerCorreoElectronicoPasos
{
    Agenda agenda;

    Persona persona;
    PersonaDAO personaDAO;

    String correoElectronicoEstablecido;

    public EstablecerCorreoElectronicoPasos()
    {
        agenda = new Agenda();
        AgendaAPI.establecerAgenda(agenda);

        personaDAO = new PersonaDAOEnMemoria();
        Persona.establecerPersonaDAO(personaDAO);
        Agenda.establecerPersonaDAO(personaDAO);
    }

    @Given("Yo tengo una persona")
    public void crearPersona() throws NombreDePersonaRepetidoException,
            NombreDePersonaInexistenteException
    {
        persona = agenda.agregarPersona("persona");
    }

    @When("Yo establezco a la persona la direccion de correo electronico: $correoElectronico")
    public void establecerCorreoElectronico(String correoElectronico)
            throws NombreDePersonaInexistenteException
    {
        correoElectronicoEstablecido = correoElectronico;
        AgendaAPI.establecerCorreoElectronicoDePersona(persona, correoElectronico);
    }

    @Then("La persona tiene asociada esa direccion")
    public void comprobarCorreoElecronico()
    {
        String correoElectronicoDeLaPersona = persona.recuperarCorreoElectronico();
        Assert.assertEquals(correoElectronicoDeLaPersona, correoElectronicoEstablecido);
    }
}
