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

public class AgregarPersonaPasos
{
    Agenda agenda;
    PersonaDAO personaDAO;

    Persona pesonaAgregada;
    String nombreDePersonaParaAgregar;

    public AgregarPersonaPasos()
    {
        agenda = new Agenda();
        personaDAO = new PersonaDAOEnMemoria();

        Agenda.establecerPersonaDAO(personaDAO);

        AgendaAPI.establecerAgenda(agenda);
    }

    @Given("Yo tengo a una persona llamada $nombrePersona")
    public void prepararPersona(String nombrePersona)
    {
        this.nombreDePersonaParaAgregar = nombrePersona;
    }

    @When("Yo agrego la persona a la agenda y la recupero posteriormente")
    public void agregarPersona() throws NombreDePersonaInexistenteException,
            NombreDePersonaRepetidoException
    {
        AgendaAPI.agregarPersona(this.nombreDePersonaParaAgregar);
        pesonaAgregada = AgendaAPI.recuperarPersona(this.nombreDePersonaParaAgregar);
    }

    @Then("La persona recuperada debe coincidir con la que se ha agregado")
    public void compruebaPersona()
    {
        Assert.assertTrue(pesonaAgregada.sonIguales(nombreDePersonaParaAgregar));
    }
}
