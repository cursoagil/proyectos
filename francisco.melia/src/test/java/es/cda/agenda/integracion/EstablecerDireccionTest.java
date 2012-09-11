package es.cda.agenda.integracion;

import junit.framework.Assert;

import org.junit.Test;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.DAO.PersonaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;

public class EstablecerDireccionTest
{
    Agenda agenda;
    PersonaDAO personaDAO;

    @Test
    public void siSeEstableceUnaDireccionSePersisteParaPoderRecuperarla()
            throws NombreDePersonaRepetidoException, NombreDePersonaInexistenteException
    {
        establecerEntorno();
        
        String direccionAEstablecer = "direcci—n de la persona";
        String nombreDeLaPersona = "persona";

        prepararDatos(direccionAEstablecer, nombreDeLaPersona);

        verificarAsercion(direccionAEstablecer, nombreDeLaPersona);
    }

    private void verificarAsercion(String direccionAEstablecer, String nombreDeLaPersona)
            throws NombreDePersonaInexistenteException
    {
        Persona personaRecuperada = agenda.recuperarPersona(nombreDeLaPersona);

        Assert.assertEquals(direccionAEstablecer, personaRecuperada.recuperarDireccion());
    }

    private void prepararDatos(String direccionAEstablecer, String nombreDeLaPersona)
            throws NombreDePersonaRepetidoException, NombreDePersonaInexistenteException
    {
        Persona personaAgregada = agenda.agregarPersona(nombreDeLaPersona);

        personaAgregada.establecerYActualizarDireccion(direccionAEstablecer);
    }

    public void establecerEntorno()
    {
        agenda = new Agenda();
        personaDAO = new PersonaDAOEnMemoria();

        Agenda.establecerPersonaDAO(personaDAO);
        Persona.establecerPersonaDAO(personaDAO);
    }
}
