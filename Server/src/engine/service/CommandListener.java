package engine.service;

import engine.service.commands.Command;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;

public class CommandListener implements Runnable{

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void executeCommand(String commandName) {
        try {
            String className = "engine.service.commands." + capitalize(commandName);
            Class<?> clazz = Class.forName(className);

            if (Command.class.isAssignableFrom(clazz)) {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                Command commandInstance = (Command) constructor.newInstance();
                commandInstance.execute();
            } else {
                System.out.println("Unknown command.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Command not found: " + commandName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true){
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
