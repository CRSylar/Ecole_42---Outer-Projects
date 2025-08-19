package launcher.Errors;

public class UsageException extends Exception{

    public UsageException(String message) {
        super(message + "Intended Usages: \n\t ./run.sh FILENAME \n\t OR \n\t java launcher.Simulator.Simulator FILENAME (inside /src folder)");
    }
}
