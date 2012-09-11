package es.cda.agenda.DAO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.modelo.Persona;

public class PersonaDAOTest
{
    PersonaDAO personaDAO;
    Persona persona;
    String nombrePersona;

    @Before
    public void inicializar() throws NombreDePersonaRepetidoException
    {
        personaDAO = new PersonaDAOEnMemoria();

        nombrePersona = "Pepet";
        persona = new Persona(nombrePersona);

        personaDAO.agregarPersona(persona);
    }

    @Test(expected = NombreDePersonaRepetidoException.class)
    public void agregarPersonaVerificaQueEsteRepetida() throws NombreDePersonaRepetidoException
    {
        personaDAO.agregarPersona(persona);
    }

    @Test
    public void agregarPersonaLaPersisteParaPoderRecuperarla()
            throws NombreDePersonaRepetidoException, NombreDePersonaInexistenteException
    {
        Persona personaAlmacenada = personaDAO.recuperarPersona(nombrePersona);

        Assert.assertTrue(personaAlmacenada.sonIguales(nombrePersona));
    }

    @Test
    public void recuperarPersonaRecuperaLaPersonaSiExiste()
            throws NombreDePersonaInexistenteException
    {
        Persona personaAlmacenada = personaDAO.recuperarPersona(nombrePersona);

        Assert.assertTrue(personaAlmacenada.sonIguales(nombrePersona));
    }

    @Test(expected = NombreDePersonaInexistenteException.class)
    public void recuperarPersonaVerificaQueLaPersonaExista()
            throws NombreDePersonaInexistenteException
    {
        personaDAO.recuperarPersona("JuanS");
    }

    @Test
    public void actualizarPersonaActualizaElCorreoElectronico()
            throws NombreDePersonaInexistenteException
    {
        String nuevoCorreo = "correo@nuevo.es";

        persona.establecerCorreoElectronico(nuevoCorreo);

        Persona personaRecuperada = actualizarYRecuperarPersona(nombrePersona);

        Assert.assertEquals(nuevoCorreo, personaRecuperada.recuperarCorreoElectronico());
    }

    @Test
    public void actualizarPersonaActualizaLaDireccion() throws NombreDePersonaInexistenteException
    {
        String nuevaDireccion = "direccion nueva";

        persona.establecerDireccion(nuevaDireccion);

        Persona personaRecuperada = actualizarYRecuperarPersona(nombrePersona);

        Assert.assertEquals(nuevaDireccion, personaRecuperada.recuperarDireccion());
    }

    @Test
    public void actualizarPersonaActualizaElNombre() throws NombreDePersonaInexistenteException
    {
        String nuevoNombre = "Joanet";

        persona.establecerNombre(nuevoNombre);

        Persona personaRecuperada = actualizarYRecuperarPersona(nuevoNombre);

        Assert.assertEquals(nuevoNombre, personaRecuperada.recuperarNombre());
    }

    private Persona actualizarYRecuperarPersona(String nombre)
            throws NombreDePersonaInexistenteException
    {
        personaDAO.actualizar(persona);

        Persona personaRecuperada = personaDAO.recuperarPersona(nombre);

        return personaRecuperada;
    }
}
