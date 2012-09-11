package es.cda.agenda.modelo;

import java.util.List;

import es.cda.agenda.FormatoDeNumeroDeTelefonoIncorrectoException;
import es.cda.agenda.NombreDePersonaInexistenteException;
import es.cda.agenda.NombreDePersonaRepetidoException;
import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.DAO.AgendaDAO;
import es.cda.agenda.DAO.PersonaDAO;
import es.cda.agenda.centralita.Centralita;
import es.cda.agenda.normalizadorFormatoTelefono.NormalizadorFormatoTelefono;
import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class Agenda
{
    private static Centralita centralita;
    private static NormalizadorFormatoTelefono normalizadorFormatoTelefono;
    private static VerificadorFormatoTelefono verificadorFormatoTelefono;
    private static AgendaDAO agendaDAO;
    private static PersonaDAO personaDAO;

    public static void estableceCentralita(Centralita centralita)
    {
        Agenda.centralita = centralita;
    }

    public static void establecerNormalizadorFormatoTelefono(
            NormalizadorFormatoTelefono normalizadorFormatoTelefono)
    {
        Agenda.normalizadorFormatoTelefono = normalizadorFormatoTelefono;
    }

    public static void establecerVerificadorFormatoTelefono(
            VerificadorFormatoTelefono verificadorFormatoTelefono)
    {
        Agenda.verificadorFormatoTelefono = verificadorFormatoTelefono;
    }

    public static void establecerAgendaDAO(AgendaDAO agendaDAO)
    {
        Agenda.agendaDAO = agendaDAO;
    }
    
    public static void establecerPersonaDAO(PersonaDAO personaDAO)
    {
        Agenda.personaDAO = personaDAO;
    }

    public Boolean hacerLlamada(Telefono telefono)
    {
        return centralita.hacerLlamada(telefono);
    }

    public Telefono agregarTelefono(String numeroTelefonoConFormato)
            throws FormatoDeNumeroDeTelefonoIncorrectoException, NumeroDeTelefonoRepetidoException
    {
        verificarFormato(numeroTelefonoConFormato);

        String telefonoNormalizado = normalizadorFormatoTelefono
                .normaliza(numeroTelefonoConFormato);
        
        Telefono telefonoAAgregar = new Telefono(telefonoNormalizado);

        agendaDAO.agregarTelefono(telefonoAAgregar);
        
        return telefonoAAgregar;
    }

    private void verificarFormato(String numeroTelefonoConFormato)
            throws FormatoDeNumeroDeTelefonoIncorrectoException
    {
        if (!Agenda.verificadorFormatoTelefono.verifica(numeroTelefonoConFormato))
        {
            throw new FormatoDeNumeroDeTelefonoIncorrectoException();
        }
    }

    public Telefono recuperarTelefono(String numeroTelefonoConFormato)
            throws NumeroDeTelefonoInexistenteException
    {
        String telefonoNormalizado = normalizadorFormatoTelefono
                .normaliza(numeroTelefonoConFormato);
        
        return agendaDAO.recuperarTelefono(telefonoNormalizado);
    }

    public Persona agregarPersona(String nombrePersona) throws NombreDePersonaRepetidoException
    {
        Persona personaAAgregar = new Persona(nombrePersona);
        
        personaDAO.agregarPersona(personaAAgregar);
        
        return personaAAgregar;
    }

    public Persona recuperarPersona(String nombrePersona)
            throws NombreDePersonaInexistenteException
    {
        return personaDAO.recuperarPersona(nombrePersona);
    }

    public List<Telefono> recuperarTelefonosDePersona(Persona persona)
    {
        return agendaDAO.recuperarTelefonosDePersona(persona);
    }
}
