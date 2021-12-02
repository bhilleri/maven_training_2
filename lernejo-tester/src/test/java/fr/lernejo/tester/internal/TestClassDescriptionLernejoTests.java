package fr.lernejo.tester.internal;

import fr.lernejo.tester.SomeLernejoTests;
import fr.lernejo.tester.api.TestMethod;

import java.lang.reflect.Method;

public class TestClassDescriptionLernejoTests {

    public static void main(String [] args){
        TestClassDescription testClassDescription = new TestClassDescription(SomeLernejoTests.class);
        testClassDescription.listTestMethods().forEach((Method method)->{
            System.out.print(method.toString() + " ");
        });

    }
    @TestMethod
    public void test(){

    }
}
