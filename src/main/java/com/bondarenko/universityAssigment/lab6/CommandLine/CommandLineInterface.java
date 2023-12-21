package com.bondarenko.universityAssigment.lab6.CommandLine;

import lombok.Value;

import java.io.Console;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandLineInterface {

    @Value
    private class CommandWithPattern {
        CommandLineCommand command;
        Pattern pattern;

        CommandWithPattern(CommandLineCommand command) {
            this.command = command;
            this.pattern = compileCommandPattern(command);
        }
    }

    private final Map<String, CommandWithPattern> commands;
    private boolean isExecuting = false;

    public CommandLineInterface(List<CommandLineCommand> commands) {
        this.commands = commands.stream().collect(Collectors.toMap(
                command -> command.getCommandName().toLowerCase(),
                CommandWithPattern::new
        ));
    }

    public void addCommand(CommandLineCommand command) {
        commands.put(command.getCommandName(), new CommandWithPattern(command));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        PrintStream errorStream = System.out;

        isExecuting = true;
        while (isExecuting) {
            try {
                String inputLine = scanner.nextLine().toLowerCase();

                CommandWithPattern command = findCommand(inputLine);
                Object[] commandArguments = readCommandArguments(command, inputLine);
                command.command.execute(commandArguments);
            } catch (Exception e) {
                errorStream.println(e.getMessage());
            }
        }
    }

    private CommandWithPattern findCommand(String inputLine) {
        if (inputLine.isEmpty() || inputLine.isBlank()) {
            throw new IllegalArgumentException("Command name is missing");
        }

        String commandName = inputLine.split("\\s")[0];
        if (!commands.containsKey(commandName)) {
            throw new IllegalArgumentException("Invalid command: " + commandName);
        }

        return  commands.get(commandName);
    }

    private Object[] readCommandArguments(CommandWithPattern command, String inputLine) {
        Object[] commandArguments = new Object[command.command.getParametersCount()];
        CommandLineCommand.ParameterType[] parameterTypes = command.getCommand().getParameterTypes();
        Matcher matcher = command.pattern.matcher(inputLine);

        if (!matcher.find()) {
            String errorMessage = "Invalid arguments for command: " + command.command.getCommandName();
            errorMessage += "\n Expected arguments: " + getCommandExpectedArguments(command.command);
            throw new IllegalArgumentException(errorMessage);
        }

        for (int i = 0; i < matcher.groupCount() ; i++) {
            commandArguments[i] = castToParameter(matcher.group(i + 1), parameterTypes[i]);
        }

        return commandArguments;
    }

    public void exit() {
        isExecuting = false;
    }

    private Pattern compileCommandPattern(CommandLineCommand command) {
        Stream<String> parameterRegexes = Arrays.stream(command.getParameterTypes()).map(this::getParameterTypePattern);
        Stream<String> patternTokens = Stream.concat(Stream.of(command.getCommandName()), parameterRegexes);
        String regexPattern = patternTokens.collect(Collectors.joining("\\s+"));
        return Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
    }

    private Object castToParameter(String str, CommandLineCommand.ParameterType type) {
        return switch (type) {
            case INTEGER -> Integer.valueOf(Integer.parseInt(str));
            case DECIMAL -> Double.parseDouble(str);
            case STRING -> str;
        };
    }

    private String getParameterTypePattern(CommandLineCommand.ParameterType type) {
        return switch (type) {
            case INTEGER -> "(-?\\d+)";
            case DECIMAL -> "(-?\\d+[.,]\\d*)";
            case STRING -> "((?:\".*\")|(?:\\w\\S*))";
        };
    }

    private String getCommandExpectedArguments(CommandLineCommand command) {
        return  Arrays.stream(command.getParameterTypes())
                .map(type -> "[" + type.toString() + "]")
                .collect(Collectors.joining(" "));
    }
}
