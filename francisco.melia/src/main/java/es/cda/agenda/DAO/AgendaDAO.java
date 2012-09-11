package es.cda.agenda.DAO;

import java.util.List;

import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.modelo.Persona;
import es.cda.agenda.modelo.Telefono;

public interface AgendaDAO
{
    public void agregarTelefono(Telefono telefono) throws NumeroDeTelefonoRepetidoException;

    public Telefono recuperarTelefono(String telefonoConFormato)
            throws NumeroDeTelefonoInexistenteException;

    public void actualizar(Telefono telefono) throws NumeroDeTelefonoInexistenteException;

    public List<Telefono> recuperarTelefonosDePersona(Persona any);
}
