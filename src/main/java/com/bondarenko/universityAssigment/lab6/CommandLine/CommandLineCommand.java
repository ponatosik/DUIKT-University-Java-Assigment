package com.bondarenko.universityAssigment.lab6.CommandLine;

import lombok.*;

import java.util.function.Consumer;

@Getter
@Setter
@AllArgsConstructor
public class CommandLineCommand {
    public enum ParameterType {STRING, INTEGER, DECIMAL}

    private String commandName;
    private ParameterType[] parameterTypes;
    private Consumer<Object[]> action;

    public int getParametersCount() {
        return parameterTypes.length;
    }

    public void execute(Object[] parameters) {
        action.accept(parameters);
    }
}
