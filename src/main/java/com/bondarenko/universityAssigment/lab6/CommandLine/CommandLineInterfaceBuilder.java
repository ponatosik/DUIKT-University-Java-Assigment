package com.bondarenko.universityAssigment.lab6.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import com.bondarenko.universityAssigment.lab6.CommandLine.CommandLineCommand.ParameterType;

public class CommandLineInterfaceBuilder {
    private final List<CommandLineCommand> commands = new ArrayList<>();
    private final List<String> exitCommandNames = new ArrayList<>();

    public CommandLineInterfaceBuilder addCommand(String commandName, Runnable action) {
        return  addCommandWithoutParameter(commandName, action);
    }

    public <T> CommandLineInterfaceBuilder addCommand(String commandName, ParameterType type, Consumer<T> action) {
        return  addCommandWithParameter(commandName, type, action);
    }

    public <T> CommandLineInterfaceBuilder addCommand(String commandName, ParameterType type, BiConsumer<T, T> action) {
        return  addCommandWithParameters(commandName, type, 2, (Consumer<List<T>>) params ->
                action.accept(params.get(0), params.get(1)));
    }

    @FunctionalInterface public interface TernaryConsumer <T, U, V> { void accept(T t, U u, V v); }

    public <T> CommandLineInterfaceBuilder addCommand(String commandName, ParameterType type, TernaryConsumer<T, T, T> action) {
        return  addCommandWithParameters(commandName, type, 3, (Consumer<List<T>>) params ->
                action.accept(params.get(0), params.get(1), params.get(2)));
    }

    @FunctionalInterface public interface QuaternaryConsumer <T, U, V, W> { void accept(T t, U u, V v, W w); }

    public <T> CommandLineInterfaceBuilder addCommand(String commandName, ParameterType type, QuaternaryConsumer<T, T, T, T> action) {
        return  addCommandWithParameters(commandName, type, 4, (Consumer<List<T>>) params ->
                action.accept(params.get(0), params.get(1), params.get(2), params.get(3)));
    }

    public CommandLineInterfaceBuilder addCommand(CommandLineCommand command) {
        commands.add(command);
        return this;
    }

    public CommandLineInterfaceBuilder addCommandWithoutParameter(String commandName, Runnable action) {
        CommandLineCommand command = new CommandLineCommand(commandName, new ParameterType[0], params -> action.run());
        return addCommand(command);
    }

    public <T> CommandLineInterfaceBuilder addCommandWithParameter(String commandName, ParameterType type, Consumer<T> action) {
        ParameterType[] parameterTypes = new ParameterType[]{ type };
        Consumer<Object[]> commandAction = params -> action.accept((T) params[0]);

        CommandLineCommand command = new CommandLineCommand(commandName, parameterTypes, commandAction);
        return addCommand(command);
    }

    public <T> CommandLineInterfaceBuilder addCommandWithParameters(String commandName, ParameterType type, int count, Consumer<List<T>> action) {
        ParameterType[] parameterTypes = new ParameterType[count];
        Arrays.fill(parameterTypes, type);
        Consumer<Object[]> commandAction = params -> action.accept(Arrays.stream(params).map(x -> (T)x).toList());

        CommandLineCommand command = new CommandLineCommand(commandName, parameterTypes, commandAction);
        return addCommand(command);
    }

    public CommandLineInterfaceBuilder addExitCommand(String commandName) {
        exitCommandNames.add(commandName);
        return this;
    }

    public CommandLineInterface build() {
        CommandLineInterface cli = new CommandLineInterface(commands);
        for (String exitCommandName : exitCommandNames) {
            CommandLineCommand exitCommand = new CommandLineCommand(exitCommandName, new ParameterType[0], params -> cli.exit());
            cli.addCommand(exitCommand);
        }
        return cli;
    }
}
