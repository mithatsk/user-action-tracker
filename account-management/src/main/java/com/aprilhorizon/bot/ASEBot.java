package com.aprilhorizon.bot;

import com.aprilhorizon.accountmanagement.AccountService;
import com.aprilhorizon.advice.models.LoggedArgument;
import com.aprilhorizon.advice.models.LoggedMethod;
import com.aprilhorizon.logger.ASELogger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ASEBot {

    private ASELogger logger = new ASELogger();

    public void replicateActions() throws Exception {
        String actions = "[" + logger.read() + "]";
        LoggedMethod[] loggedMethods = asObject(actions, LoggedMethod[].class);

        for(LoggedMethod loggedMethod : loggedMethods) {
            invokeMethod(loggedMethod);
        }
    }

    private void invokeMethod(LoggedMethod loggedMethod) throws Exception {
        AccountService accountService = (AccountService) constructService(loggedMethod);

        ArrayList<LoggedArgument> arguments = loggedMethod.getArguments();
        Class<?>[] parameterTypes = constructParameterTypes(arguments);
        Method method = accountService.getClass().getDeclaredMethod(loggedMethod.getMethodName(), parameterTypes);

        Object[] args = constructArgs(arguments);
        method.invoke(accountService, args);
    }

    private Object constructService(LoggedMethod loggedMethod) throws Exception {
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
                        return toObject(classType, argument.getArgument());
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

    public static Object toObject(Class clazz, String value) {
        if (Boolean.class == clazz) return Boolean.parseBoolean(value);
        if (Byte.class == clazz) return Byte.parseByte(value);
        if (Short.class == clazz) return Short.parseShort(value);
        if (Integer.class == clazz) return Integer.parseInt(value);
        if (Long.class == clazz) return Long.parseLong(value);
        if (Float.class == clazz) return Float.parseFloat(value);
        if (Double.class == clazz) return Double.parseDouble(value);
        return value;
    }
}
