package engine.service;

import engine.service.commands.Command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class CommandListener implements Runnable {

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void executeCommand(String input) {
        try {
            // Split input into command name and parameters
            String[] parts = input.split(" ");
            String commandName = parts[0];
            String[] params = Arrays.copyOfRange(parts, 1, parts.length);

            // Construct the command class name
            String className = "engine.service.commands." + capitalize(commandName);
            Class<?> clazz = Class.forName(className);

            if (Command.class.isAssignableFrom(clazz)) {
                // Find a suitable constructor (either with or without parameters)
                Constructor<?> constructor;
                Command commandInstance;

                if (params.length == 0) {
                    constructor = clazz.getDeclaredConstructor();
                    commandInstance = (Command) constructor.newInstance();
                } else {
                    constructor = clazz.getDeclaredConstructor(String[].class);
                    commandInstance = (Command) constructor.newInstance((Object) params);
                }

                // Execute the command
                commandInstance.execute();
            } else {
                System.out.println("Unknown command.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Command not found: " + input);
        } catch (NoSuchMethodException e) {
            System.out.println("Command does not support the given parameters: " + input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String input;

                while ((input = reader.readLine()) != null) {
                    if (input.trim().isEmpty()) continue;

                    executeCommand(input.trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
