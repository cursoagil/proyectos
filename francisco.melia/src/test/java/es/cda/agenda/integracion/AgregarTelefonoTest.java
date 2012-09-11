package es.cda.agenda.integracion;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.DAO.AgendaDAO;
import es.cda.agenda.DAO.AgendaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Telefono;
import es.cda.agenda.normalizadorFormatoTelefono.NormalizadorFormatoTelefono;
import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class AgregarTelefonoTest
{
    Agenda agenda;
    VerificadorFormatoTelefono verificadorFormatoTelefono;
    NormalizadorFormatoTelefono normalizadorFormatoTelefono;
    AgendaDAO agendaDAO;

    @Before
    public void init()
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
    }

    @Test(expected = FormatoDeNumeroDeTelefonoIncorrectoException.class)
    public void agregarTelefonosVerificaFormato()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        String telefonoAAgregar = "96 96 96 96";

        agenda.agregarTelefono(telefonoAAgregar);
    }

    @Test(expected = NumeroDeTelefonoRepetidoException.class)
    public void agregarTelefonosVerificaQueNoEstabaRepetido()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        String telefonoAAgregar = "964 964 964";

        agenda.agregarTelefono(telefonoAAgregar);
        agenda.agregarTelefono("(964) 964964");
    }

    @Test(expected = NumeroDeTelefonoInexistenteException.class)
    public void recuperarTelefonoVerificaQueExiste()
            throws NumeroDeTelefonoInexistenteException
    {
        agenda.recuperarTelefono("123");
    }

    @Test
    public void losTelefonosSeRecuperanEnFormatoNormalizado()
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException,
            NumeroDeTelefonoInexistenteException
    {
        String telefonoAAgregar = "964 964 964";
        String telefonoNormalizado = normalizadorFormatoTelefono.normaliza(telefonoAAgregar);

        agenda.agregarTelefono(telefonoAAgregar);
        Telefono telefonoRecuperado = agenda.recuperarTelefono(telefonoAAgregar);

        Assert.assertTrue(telefonoRecuperado.sonIguales(telefonoNormalizado));
    }
}
