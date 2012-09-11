package es.cda.agenda.DAO;

import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.modelo.Persona;

public interface PersonaDAO
{
    void actualizar(Persona persona) throws NombreDePersonaInexistenteException;

    void agregarPersona(Persona persona) throws NombreDePersonaRepetidoException;

    Persona recuperarPersona(String nombrePersona) throws NombreDePersonaInexistenteException;
}
