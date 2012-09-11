package es.cda.agenda.DAO;

import java.util.ArrayList;
import java.util.List;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.modelo.Persona;

public class PersonaDAOEnMemoria implements PersonaDAO
{
    List<Persona> personas;

    public PersonaDAOEnMemoria()
    {
        personas = new ArrayList<Persona>();
    }

    public void agregarPersona(Persona persona) throws NombreDePersonaRepetidoException
    {
        comprobarSiYaExistePersona(persona);
        personas.add(persona);
    }

    private void comprobarSiYaExistePersona(Persona persona)
            throws NombreDePersonaRepetidoException
    {
        for (Persona personaDeLaLista : personas)
        {
            if (persona.sonIguales(personaDeLaLista))
            {
                throw new NombreDePersonaRepetidoException();
            }
        }
    }

    public Persona recuperarPersona(String nombrePersona)
            throws NombreDePersonaInexistenteException
    {
        for (Persona personaDeLaLista : personas)
        {
            if (personaDeLaLista.sonIguales(nombrePersona))
            {
                return personaDeLaLista;
            }
        }

        throw new NombreDePersonaInexistenteException();
    }

    public void actualizar(Persona persona) throws NombreDePersonaInexistenteException
    {
        Persona personaNoActualizada = recuperarPersona(persona.recuperarNombre());

        personaNoActualizada.establecerCorreoElectronico(persona.recuperarCorreoElectronico());
        personaNoActualizada.establecerDireccion(persona.recuperarDireccion());
    }
}
