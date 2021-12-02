package fr.lernejo.tester;

import fr.lernejo.tester.internal.TestClassDescription;
import fr.lernejo.tester.internal.TestClassDiscoverer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner {
    public static void main(String [] args){
        AtomicInteger failure = new AtomicInteger();
        AtomicInteger success = new AtomicInteger();
        long timeTest = System.currentTimeMillis();
        failure.set(0);
        success.set(0);
        for (String arg : args) {
            arg = "fr.lernejo.tester";
            TestClassDiscoverer testClassDiscoverer = new TestClassDiscoverer(arg);
            List<TestClassDescription> listTestClassDescription =  testClassDiscoverer.listTestClasses();
            listTestClassDescription.forEach(( TestClassDescription testClassDescription)->{
            long timeBeginMethodeTest =0;
                try {
                    Object testInstance = testClassDescription.getClass().getDeclaredConstructor().newInstance();

                    for (Method testMethod : testClassDescription.listTestMethods()) {

                        try
                        {
                            timeBeginMethodeTest = System.currentTimeMillis();
                            testMethod.invoke(testInstance);
                            long duration = System.currentTimeMillis() - timeBeginMethodeTest;
                            System.out.println(testMethod.getName()+ " OK " + duration +" ms" );
                            success.getAndIncrement();
                        }
                        catch (InvocationTargetException e)
                        {
                            long duration = System.currentTimeMillis() - timeBeginMethodeTest;
                            System.out.println(testMethod.getName()+ " KO " + duration +" ms" );
                            failure.getAndIncrement();
                        }
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            });
        }
        System.out.println();
        System.out.println("test : " + success.get()+failure.get() + ", succés : " + success.get() + ", échec : " + failure.get() + ", temps total : " + (System.currentTimeMillis()- timeTest) + " ms");
    }
}
