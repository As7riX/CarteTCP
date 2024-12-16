package engine.service.commands;

public class SampleCommand implements Command {
    private String[] params;

    public SampleCommand() {
        // Costruttore vuoto
    }

    public SampleCommand(String[] params) {
        this.params = params;
    }

    @Override
    public void execute() {
        if (params != null && params.length > 0) {
            System.out.println("Command executed with parameters: " + String.join(", ", params));
        } else {
            System.out.println("Command executed without parameters.");
        }
    }
}