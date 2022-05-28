package com.aprilhorizon.bot;

import com.aprilhorizon.accountmanagement.AccountService;
import com.aprilhorizon.advice.models.LoggedArgument;
import com.aprilhorizon.advice.models.LoggedMethod;
import com.aprilhorizon.logger.ASELogger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ASEBot {

    private final ASELogger logger = new ASELogger();
    private AccountService accountService;

    private Long executedTime;
    private final Long maxDelayTime = Long.valueOf(2000);

    public void replicateActions() throws Exception {
        String actions = "[" + logger.read() + "]";
        LoggedMethod[] loggedMethods = asObject(actions, LoggedMethod[].class);

        for (LoggedMethod loggedMethod : loggedMethods) {
            delayExecutionWithRealTimeDelay(loggedMethod);
            invokeMethod(loggedMethod);
        }
    }

    private void delayExecutionWithRealTimeDelay(LoggedMethod loggedMethod) throws Exception {
        String dateTimeString = loggedMethod.getExecutionDateTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = simpleDateFormat.parse(dateTimeString);
        Long millis = date.getTime();
        if (this.executedTime != null) {
            Long delayTime = (millis - this.executedTime) < maxDelayTime ? (millis - this.executedTime) : maxDelayTime;
            Thread.sleep(delayTime);
        }
        this.executedTime = millis;
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
        return aClass.getDeclaredConstructor().newInstance();
    }

    private Class<?>[] constructParameterTypes(ArrayList<LoggedArgument> arguments) {
        return arguments.stream().map(
                argument -> {
                    try {
                        return Class.forName(argument.getTypeName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).toArray(Class<?>[]::new);
    }

    private Object[] constructArgs(ArrayList<LoggedArgument> arguments) {
        return arguments.stream().map(
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
        ).toArray(Object[]::new);
    }

    private <T> T asObject(String result, Class<T> type) throws Exception {
        return new ObjectMapper().readValue(result, type);
    }
}
