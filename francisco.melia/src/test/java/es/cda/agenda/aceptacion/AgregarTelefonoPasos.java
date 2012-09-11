package es.cda.agenda.aceptacion;

import junit.framework.Assert;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import es.cda.agenda.AgendaAPI;
import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.DAO.AgendaDAO;
import es.cda.agenda.DAO.AgendaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Telefono;
import es.cda.agenda.normalizadorFormatoTelefono.NormalizadorFormatoTelefono;
import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class AgregarTelefonoPasos
{
    Agenda agenda;

    VerificadorFormatoTelefono verificadorFormatoTelefono;
    NormalizadorFormatoTelefono normalizadorFormatoTelefono;
    AgendaDAO agendaDAO;

    Telefono telefonoAgregado;
    String numeroTelefonoParaAgregar;

    public AgregarTelefonoPasos()
    {
        crearColaboradores();

        inyectarColaboradores();
    }

    private void crearColaboradores()
    {
        agenda = new Agenda();
        agendaDAO = new AgendaDAOEnMemoria();
        verificadorFormatoTelefono = new VerificadorFormatoTelefono();
        normalizadorFormatoTelefono = new NormalizadorFormatoTelefono();
    }

    private void inyectarColaboradores()
    {
        Agenda.establecerAgendaDAO(agendaDAO);
        Agenda.establecerNormalizadorFormatoTelefono(normalizadorFormatoTelefono);
        Agenda.establecerVerificadorFormatoTelefono(verificadorFormatoTelefono);

        AgendaAPI.establecerAgenda(agenda);
    }

    @Given("Yo tengo el telefono $numeroTelefono")
    public void prepararTelefono(String numeroTelefono)
    {
        this.numeroTelefonoParaAgregar = numeroTelefono;
    }

    @When("Yo agrego el telefono a la agenda y lo recupero posteriormente")
    public void agregarTelefono() throws FormatoDeNumeroDeTelefonoIncorrectoException,
            NumeroDeTelefonoRepetidoException, NumeroDeTelefonoInexistenteException
    {
        AgendaAPI.agregarTelefono(this.numeroTelefonoParaAgregar);
        telefonoAgregado = AgendaAPI.recuperarTelefono(this.numeroTelefonoParaAgregar);
    }

    @Then("El telefono recuperado tiene un formato normalizado")
    public void compruebaTelefono()
    {
        String telefonoNormalizado = normalizadorFormatoTelefono
                .normaliza(this.numeroTelefonoParaAgregar);
        Assert.assertTrue(telefonoAgregado.sonIguales(telefonoNormalizado));
    }
}
