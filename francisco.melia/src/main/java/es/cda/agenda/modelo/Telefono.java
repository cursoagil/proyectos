package es.cda.agenda.modelo;

import java.util.ArrayList;
import java.util.List;

import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.DAO.AgendaDAO;

public class Telefono
{
    private String numeroTelefonoNormalizado;
    private List<Persona> personas;
    
    private static AgendaDAO agendaDAO;

    public Telefono(String numeroTelefonoNormalizado)
    {
        this.numeroTelefonoNormalizado = numeroTelefonoNormalizado;
        personas = new ArrayList<Persona>();
    }

    public void establecerAgendaDAO(AgendaDAO agendaDAO)
    {
        Telefono.agendaDAO = agendaDAO;
    }

    public void establecerListaDePersonas(List<Persona> listaPersonas)
    {
        this.personas = listaPersonas;
    }

    public List<Persona> recuperarListaDePersonas()
    {
        return this.personas;
    }

    public void establecerTelefonoNormalizado(String numeroTelefonoNormalizado)
    {
        this.numeroTelefonoNormalizado = numeroTelefonoNormalizado;
    }

    public String recuperarNumeroTelefonoNormalizado()
    {
        return numeroTelefonoNormalizado;
    }

    public void agregarPersona(Persona persona) throws NumeroDeTelefonoInexistenteException,
            NombreDePersonaRepetidoException
    {
        comprobarQuePersonaNoEstaAgregada(persona);

        personas.add(persona);
        agendaDAO.actualizar(this);
    }

    private void comprobarQuePersonaNoEstaAgregada(Persona persona)
            throws NombreDePersonaRepetidoException
    {
        for (Persona personaDeLaLista : personas)
        {
            if (sonIguales(persona, personaDeLaLista))
            {
                throw new NombreDePersonaRepetidoException();
            }
        }
    }

    private boolean sonIguales(Persona persona, Persona personaDeLaLista)
    {
        return personaDeLaLista.recuperarNombre().equals(persona.recuperarNombre());
    }

    public boolean sonIguales(Telefono otroTelefono)
    {
        return sonIguales(otroTelefono.recuperarNumeroTelefonoNormalizado());
    }

    public boolean sonIguales(String otroNumeroTelefonoNormalizado)
    {
        if (this.recuperarNumeroTelefonoNormalizado().equals(otroNumeroTelefonoNormalizado))
        {
            return true;
        }

        return false;
    }
}
