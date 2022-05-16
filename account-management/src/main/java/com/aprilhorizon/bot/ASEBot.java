package com.aprilhorizon.bot;

import com.aprilhorizon.accountmanagement.AccountService;
import com.aprilhorizon.advice.models.LoggedArgument;
import com.aprilhorizon.advice.models.LoggedMethod;
import com.aprilhorizon.logger.ASELogger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ASEBot {

    private ASELogger logger = new ASELogger();
    AccountService accountService;

    public void replicateActions() throws Exception {
        String actions = "[" + logger.read() + "]";
        LoggedMethod[] loggedMethods = asObject(actions, LoggedMethod[].class);

        for(LoggedMethod loggedMethod : loggedMethods) {
            invokeMethod(loggedMethod);
        }
    }

    private void invokeMethod(LoggedMethod loggedMethod) throws Exception {
        if (this.accountService == null) {
            this.accountService = (AccountService) constructInstance(loggedMethod);
        }

        ArrayList<LoggedArgument> arguments = loggedMethod.getArguments();
        Class<?>[] parameterTypes = constructParameterTypes(arguments);
        Method method = accountService.getClass().getDeclaredMethod(loggedMethod.getMethodName(), parameterTypes);

        Object[] args = constructArgs(arguments);
        method.invoke(accountService, args);
    }

    private Object constructInstance(LoggedMethod loggedMethod) throws Exception {
        String fullClassName = loggedMethod.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(' ') + 1);
        Class aClass = Class.forName(className);
        Object instance = aClass.getDeclaredConstructor().newInstance();
        return instance;
    }

    private Class<?>[] constructParameterTypes(ArrayList<LoggedArgument> arguments) {
        List<Class> parameterTypeNames = arguments.stream().map(
                argument -> {
                    try {
                        return Class.forName(argument.getTypeName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());
        Class<?>[] parameterTypes = parameterTypeNames.toArray(new Class<?>[parameterTypeNames.size()]);
        return parameterTypes;
    }

    private Object[] constructArgs(ArrayList<LoggedArgument> arguments) {
        List<Object> argumentList = arguments.stream().map(
                argument -> {
                    try {
                        Class classType = Class.forName(argument.getTypeName());
                        PropertyEditor editor = PropertyEditorManager.findEditor(classType);
                        editor.setAsText(argument.getArgument());
                        return editor.getValue();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());
        Object[] args = argumentList.toArray(new Object[argumentList.size()]);
        return args;
    }

    private <T> T asObject(String result, Class<T> type) throws Exception {
        return new ObjectMapper().readValue(result, type);
    }
}
