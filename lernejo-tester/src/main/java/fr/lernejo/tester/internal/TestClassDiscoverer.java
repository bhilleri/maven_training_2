package fr.lernejo.tester.internal;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestClassDiscoverer {
    private String name;
    public TestClassDiscoverer(String name){
        this.name = name;
    }
    public List<TestClassDescription> listTestClasses(){
        Reflections reflections = new Reflections(name, new SubTypesScanner(false));
        Set<Class<?>> allTypes = reflections.getSubTypesOf(Object.class);
        Predicate<Class<?>> CheckEndName = p-> p.getName().endsWith("LernejoTests");

        List <Class<?>> SelectedMethod = allTypes.stream().filter(CheckEndName).collect(Collectors.toList());

        Predicate<java.lang.annotation.Annotation> CheckAnnotation = p -> p.toString().equals("@fr.lernejo.tester.api.TestMethod()");
        Predicate<Method> CheckMethod = m -> Arrays.stream(m.getAnnotations()).anyMatch(CheckAnnotation);
        Predicate<Class<?>> CheckifMoreOneAnotationInClass = c -> Arrays.stream(c.getDeclaredMethods()).anyMatch(CheckMethod);

        List <Class<?>> SelectedMethod2 =  SelectedMethod.stream().filter(CheckifMoreOneAnotationInClass).collect(Collectors.toList());
        List<TestClassDescription> testClassDescriptions = new ArrayList<>();
        SelectedMethod2.forEach((Class <?> clazz)->{
            testClassDescriptions.add(new TestClassDescription(clazz));
        });
        return testClassDescriptions;
    }
}
