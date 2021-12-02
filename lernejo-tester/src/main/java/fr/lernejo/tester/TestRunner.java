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
            TestClassDiscoverer testClassDiscoverer = new TestClassDiscoverer(arg);
            List<TestClassDescription> listTestClassDescription =  testClassDiscoverer.listTestClasses();
            listTestClassDescription.forEach(( TestClassDescription testClassDescription)->{
                long timeBeginMethodeTest =0;
                try {
                    Object testInstance = testClassDescription.getTargetClass().getConstructor().newInstance();

                    for (Method testMethod : testClassDescription.listTestMethods()) {

                        try
                        {
                            timeBeginMethodeTest = System.currentTimeMillis();
                            testMethod.invoke(testInstance);
                            long duration = System.currentTimeMillis() - timeBeginMethodeTest;
                            String nameOfClass = testMethod.getDeclaringClass().getName().replace("class ","");;
                            System.out.println(nameOfClass+"#" +testMethod.getName()+ " OK " + duration +" ms" );
                            success.getAndIncrement();
                        }
                        catch (InvocationTargetException e)
                        {
                            long duration = System.currentTimeMillis() - timeBeginMethodeTest;
                            String nameOfClass = testMethod.getDeclaringClass().getName().replace("class ","");;
                            System.out.println(nameOfClass+"#" + testMethod.getName()+ " KO " + duration +" ms" );
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
        System.out.println("test : " + (success.get()+failure.get()) + ", succes : " + success.get() + ", echec : " + failure.get() + ", temps total : " + (System.currentTimeMillis()- timeTest) + " ms");
    }
}
