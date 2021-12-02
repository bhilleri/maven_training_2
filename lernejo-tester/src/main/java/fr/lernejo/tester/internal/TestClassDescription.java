package fr.lernejo.tester.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestClassDescription {
    private Class<?>  targetClass;
    public Class<?> getTargetClass(){
        return this.targetClass;
    }

    public TestClassDescription(Class<?> targetClass){
        this.targetClass = targetClass;
    }

    public List<Method>  listTestMethods(){
        Predicate<java.lang.annotation.Annotation> predicate = p -> p.toString().equals("@fr.lernejo.tester.api.TestMethod()");
        List <Method> listMethod = new ArrayList<>();
        for (Method declaredMethod : targetClass.getDeclaredMethods()) {
            if (Modifier.isPublic(declaredMethod.getModifiers()))
                if (declaredMethod.getReturnType().getName() == "void")
                    if (declaredMethod.getParameterCount() == 0)
                        if (Arrays.stream(declaredMethod.getAnnotations()).anyMatch(predicate))
                            listMethod.add(declaredMethod);
        }
        return listMethod;

    }
}
