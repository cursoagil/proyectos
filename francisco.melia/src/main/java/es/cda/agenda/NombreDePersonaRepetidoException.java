package es.cda.agenda;

@SuppressWarnings("serial")
public class NombreDePersonaRepetidoException extends Exception
{
    public NombreDePersonaRepetidoException()
    {
        super("Este nombre de persona está repetido");
    }
    
    public NombreDePersonaRepetidoException(String message)
    {
        super(message);
    }
    
}
