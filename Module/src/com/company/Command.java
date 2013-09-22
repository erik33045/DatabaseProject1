package com.company;

/**
 * Data container for an individual Command
 *
 * @author dbettis
 */
public class Command {

    public Command(String command, String[] parameters) {
        super();
        this.command = command;
        this.parameters = parameters;
    }

    public String getCommand() {
        return command;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Command: ").append(command).append("\n");
        for (int i = 0; i < parameters.length; i++) {
            sb.append("parameters[");
            sb.append(i);
            sb.append("] = ");
            sb.append(parameters[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    private final String command;
    private final String[] parameters;
}
