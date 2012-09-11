package es.cda.agenda.modelo;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.DAO.PersonaDAO;

public class Persona
{
    private String direccion;

    private String correoElectronico;
    private String nombre;

    private static PersonaDAO personaDAO;

    public static void establecerPersonaDAO(PersonaDAO personaDAO)
    {
        Persona.personaDAO = personaDAO;
    }

    public Persona(String nombrePersona)
    {
        this.nombre = nombrePersona;
    }

    public String recuperarNombre()
    {
        return this.nombre;
    }

    public void establecerNombre(String nombrePersona)
    {
        this.nombre = nombrePersona;
    }

    public void establecerDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public String recuperarDireccion()
    {
        return this.direccion;
    }

    public void establecerCorreoElectronico(String correoElectronico)
    {
        this.correoElectronico = correoElectronico;
    }

    public String recuperarCorreoElectronico()
    {
        return correoElectronico;
    }

    public void establecerYActualizarCorreoElectronico(String correoElectronico)
            throws NombreDePersonaInexistenteException
    {
        establecerCorreoElectronico(correoElectronico);
        actualizar();
    }

    public void establecerYActualizarDireccion(String direccion)
            throws NombreDePersonaInexistenteException
    {
        establecerDireccion(direccion);
        actualizar();
    }

    private void actualizar() throws NombreDePersonaInexistenteException
    {
        personaDAO.actualizar(this);
    }

    public boolean sonIguales(Persona otraPersona)
    {
        return sonIguales(otraPersona.recuperarNombre());
    }

    public boolean sonIguales(String nombreOtraPersona)
    {
        if (this.recuperarNombre().equals(nombreOtraPersona))
        {
            return true;
        }

        return false;
    }
}
