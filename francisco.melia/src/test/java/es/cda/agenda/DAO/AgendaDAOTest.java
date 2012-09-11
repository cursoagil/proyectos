package es.cda.agenda.DAO;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.modelo.Persona;
import es.cda.agenda.modelo.Telefono;

public class AgendaDAOTest
{
    AgendaDAO agendaDAO;
    Telefono telefono;
    String numeroTelefono;

    @Before
    public void inicializar() throws NumeroDeTelefonoRepetidoException
    {
        agendaDAO = new AgendaDAOEnMemoria();

        numeroTelefono = "954252525";
        telefono = new Telefono(numeroTelefono);

        agendaDAO.agregarTelefono(telefono);
    }

    @Test(expected = NumeroDeTelefonoRepetidoException.class)
    public void agregarTelefonosVerificaQueEsteRepetido() throws NumeroDeTelefonoRepetidoException
    {
        agendaDAO.agregarTelefono(telefono);
    }

    @Test
    public void agregarTelefonosLoPersisteParaPoderRecuperarlo()
            throws NumeroDeTelefonoRepetidoException, NumeroDeTelefonoInexistenteException
    {
        Telefono telefonoAlmacenado = agendaDAO.recuperarTelefono(numeroTelefono);

        Assert.assertTrue(telefonoAlmacenado.sonIguales(numeroTelefono));
    }

    @Test
    public void recuperarTelefonoLoRecupera() throws NumeroDeTelefonoInexistenteException
    {
        Telefono telefonoAlmacenado = agendaDAO.recuperarTelefono(numeroTelefono);

        Assert.assertTrue(telefonoAlmacenado.sonIguales(numeroTelefono));
    }

    @Test(expected = NumeroDeTelefonoInexistenteException.class)
    public void recuperarTelefonoVerificaQueExiste() throws NumeroDeTelefonoInexistenteException
    {
        agendaDAO.recuperarTelefono("123");
    }

    @Test
    public void actualizarTelefonoActualizaElNumero() throws NumeroDeTelefonoInexistenteException
    {
        String nuevoNumeroTelefonoNormalizado = "999999999";

        telefono.establecerTelefonoNormalizado(nuevoNumeroTelefonoNormalizado);

        agendaDAO.actualizar(telefono);

        Telefono telefonoRecuperado = agendaDAO.recuperarTelefono(nuevoNumeroTelefonoNormalizado);

        Assert.assertTrue(telefonoRecuperado.sonIguales(nuevoNumeroTelefonoNormalizado));
    }

    @Test
    public void actualizarTelefonoActualizaLaListaDePersonas()
            throws NumeroDeTelefonoInexistenteException
    {
        Persona personaAAgregar = new Persona("pepe");

        telefono.establecerListaDePersonas(Collections.singletonList(personaAAgregar));

        Telefono telefonoRecuperado = actualizarYRecuperarTelefono();

        Persona personaRecuperada = telefonoRecuperado.recuperarListaDePersonas().get(0);

        Assert.assertTrue(personaAAgregar.sonIguales(personaRecuperada));
    }

    private Telefono actualizarYRecuperarTelefono() throws NumeroDeTelefonoInexistenteException
    {
        agendaDAO.actualizar(telefono);

        Telefono telefonoRecuperado = agendaDAO.recuperarTelefono(numeroTelefono);

        return telefonoRecuperado;
    }

    @Test
    public void recuperarTelefonosDePersona() throws NumeroDeTelefonoRepetidoException
    {
        Telefono segundoTelefono = crearSegundoTelefono();

        Persona personaAAgregar = agregarPersonaALosTelefonos(segundoTelefono);

        recuperarPersonaYAseverarCondiciones(segundoTelefono, personaAAgregar);
    }

    private void recuperarPersonaYAseverarCondiciones(Telefono segundoTelefono,
            Persona personaAAgregar)
    {
        List<Telefono> listaTelefonosRecuperados = agendaDAO
                .recuperarTelefonosDePersona(personaAAgregar);

        Assert.assertTrue(telefono.sonIguales(listaTelefonosRecuperados.get(0)));
        Assert.assertTrue(segundoTelefono.sonIguales(listaTelefonosRecuperados.get(1)));
    }

    private Persona agregarPersonaALosTelefonos(Telefono segundoTelefono)
    {
        Persona personaAAgregar = new Persona("pepe");

        telefono.establecerListaDePersonas(Collections.singletonList(personaAAgregar));
        segundoTelefono.establecerListaDePersonas(Collections.singletonList(personaAAgregar));

        return personaAAgregar;
    }

    private Telefono crearSegundoTelefono() throws NumeroDeTelefonoRepetidoException
    {
        String otroTelefono = "964252525";

        Telefono segundoTelefono = new Telefono(otroTelefono);
        agendaDAO.agregarTelefono(segundoTelefono);

        return segundoTelefono;
    }
}
