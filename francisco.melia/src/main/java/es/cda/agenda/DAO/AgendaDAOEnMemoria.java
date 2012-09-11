package es.cda.agenda.DAO;

import java.util.ArrayList;
import java.util.List;

import es.cda.agenda.NumeroDeTelefonoInexistenteException;
import es.cda.agenda.NumeroDeTelefonoRepetidoException;
import es.cda.agenda.modelo.Persona;
import es.cda.agenda.modelo.Telefono;

public class AgendaDAOEnMemoria implements AgendaDAO
{
    List<Telefono> telefonos;

    public AgendaDAOEnMemoria()
    {
        telefonos = new ArrayList<Telefono>();
    }

    public void agregarTelefono(Telefono telefono) throws NumeroDeTelefonoRepetidoException
    {
        comprobarSiYaExisteTelefono(telefono);

        telefonos.add(telefono);
    }

    private void comprobarSiYaExisteTelefono(Telefono telefono)
            throws NumeroDeTelefonoRepetidoException
    {
        for (Telefono telefonoDeLaLista : telefonos)
        {
            if (telefono.sonIguales(telefonoDeLaLista))
            {
                throw new NumeroDeTelefonoRepetidoException();
            }
        }
    }

    public Telefono recuperarTelefono(String telefonoNormalizado)
            throws NumeroDeTelefonoInexistenteException
    {
        for (Telefono telefonoDeLaLista : telefonos)
        {
            if (telefonoDeLaLista.sonIguales(telefonoNormalizado))
            {
                return telefonoDeLaLista;
            }
        }

        throw new NumeroDeTelefonoInexistenteException();
    }

    public void actualizar(Telefono telefono) throws NumeroDeTelefonoInexistenteException
    {
        Telefono telefonoNoActualizado = recuperarTelefono(telefono
                .recuperarNumeroTelefonoNormalizado());

        telefonoNoActualizado.establecerListaDePersonas(telefono.recuperarListaDePersonas());
        telefonoNoActualizado.establecerTelefonoNormalizado(telefono
                .recuperarNumeroTelefonoNormalizado());
    }

    public List<Telefono> recuperarTelefonosDePersona(Persona persona)
    {
        List<Telefono> telefonosDeLaPersona = new ArrayList<Telefono>();

        for (Telefono telefonoDeLaLista : telefonos)
        {
            if (comprobarSiCoincideAlgunaPersona(persona, telefonoDeLaLista))
            {
                telefonosDeLaPersona.add(telefonoDeLaLista);
            }
        }

        return telefonosDeLaPersona;
    }

    public Boolean comprobarSiCoincideAlgunaPersona(Persona persona, Telefono telefonoDeLaLista)
    {
        List<Persona> personasDelTelefono = telefonoDeLaLista.recuperarListaDePersonas();

        for (Persona personaDeLaLista : personasDelTelefono)
        {
            if (persona.sonIguales(personaDeLaLista))
            {
                return true;
            }
        }

        return false;
    }
}
