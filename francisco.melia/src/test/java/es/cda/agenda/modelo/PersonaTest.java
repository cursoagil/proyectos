package es.cda.agenda.modelo;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class PersonaTest
{
    Persona persona;

    PersonaDAO personaDAO;
    VerificadorFormatoTelefono verificadorFormatoTelefono;

    @Before
    public void inicializar()
    {
        persona = new Persona("persona");

        personaDAO = mock(PersonaDAO.class);
        verificadorFormatoTelefono = mock(VerificadorFormatoTelefono.class);
        
        Persona.establecerPersonaDAO(personaDAO);
    }

    @Test
    public void establecerDireccionLlamaAlMetodoDePersistencia()
            throws FormatoDeNumeroDeTelefonoIncorrectoException,
            NombreDePersonaInexistenteException
    {
        persona.establecerYActualizarDireccion(anyString());

        verify(personaDAO).actualizar(persona);
    }

    @Test
    public void seAgregaUnaDireccionParaPoderRecuperarla()
            throws NombreDePersonaInexistenteException
    {
        String direccion = "mi direccion postal";

        persona.establecerYActualizarDireccion(direccion);

        Assert.assertEquals(direccion, persona.recuperarDireccion());
    }

    @Test
    public void establecerCorreoElectronicoLlamaAlMetodoDePersistencia()
            throws FormatoDeNumeroDeTelefonoIncorrectoException,
            NombreDePersonaInexistenteException
    {
        persona.establecerYActualizarCorreoElectronico(anyString());

        verify(personaDAO).actualizar(persona);
    }

    @Test
    public void seAgregaUnCorreoElectronicoParaPoderRecuperarlo()
            throws NombreDePersonaInexistenteException
    {
        String correoElectronico = "direccion@mail.es";

        persona.establecerCorreoElectronico(correoElectronico);

        Assert.assertEquals(correoElectronico, persona.recuperarCorreoElectronico());
    }

    @Test
    public void dosPersonasSonIgualesSiCoincideSuNombre()
    {
        Persona otraPersona = new Persona("persona diferente");

        Assert.assertFalse(persona.sonIguales(otraPersona));

        Assert.assertTrue(persona.sonIguales(persona));
    }
}
