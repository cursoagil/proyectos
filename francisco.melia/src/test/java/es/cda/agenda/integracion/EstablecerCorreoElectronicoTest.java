package es.cda.agenda.integracion;

import junit.framework.Assert;

import org.junit.Test;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.DAO.PersonaDAOEnMemoria;
import es.cda.agenda.modelo.Agenda;
import es.cda.agenda.modelo.Persona;

public class EstablecerCorreoElectronicoTest
{
    Agenda agenda;
    PersonaDAO personaDAO;
    
    @Test
    public void siSeEstableceUnCorreoElectronicoSePersisteParaPoderRecuperarlo()
            throws NombreDePersonaRepetidoException, NombreDePersonaInexistenteException
    {
        establecerEntorno();
        
        String correoElectronicoAEstablecer = "email@email.com";
        String nombreDeLaPersona = "persona";

        prepararDatos(correoElectronicoAEstablecer, nombreDeLaPersona);

        verificarAsercion(correoElectronicoAEstablecer, nombreDeLaPersona);
    }

    private void verificarAsercion(String correoElectronicoAEstablecer, String nombreDeLaPersona)
            throws NombreDePersonaInexistenteException
    {
        Persona personaRecuperada = agenda.recuperarPersona(nombreDeLaPersona);

        Assert.assertEquals(correoElectronicoAEstablecer,
                personaRecuperada.recuperarCorreoElectronico());
    }

    private void prepararDatos(String correoElectronicoAEstablecer, String nombreDeLaPersona)
            throws NombreDePersonaRepetidoException, NombreDePersonaInexistenteException
    {
        Persona personaAgregada = agenda.agregarPersona(nombreDeLaPersona);

        personaAgregada.establecerYActualizarCorreoElectronico(correoElectronicoAEstablecer);
    }
    
    private void establecerEntorno()
    {
        agenda = new Agenda();
        personaDAO = new PersonaDAOEnMemoria();

        Agenda.establecerPersonaDAO(personaDAO);
        Persona.establecerPersonaDAO(personaDAO);
    }
}
