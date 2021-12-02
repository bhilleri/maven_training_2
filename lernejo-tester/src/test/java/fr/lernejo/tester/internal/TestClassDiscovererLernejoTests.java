package fr.lernejo.tester.internal;

import fr.lernejo.tester.SomeLernejoTests;
import fr.lernejo.tester.api.TestMethod;

import java.lang.reflect.Method;

public class TestClassDiscovererLernejoTests {
    public static void main(String [] args){
        TestClassDiscoverer testClassDiscoverer = new TestClassDiscoverer("fr.lernejo.tester");
        testClassDiscoverer.listTestClasses().forEach((TestClassDescription testClassDescription)->{
            System.out.print(testClassDescription.toString() + " ");
        });
    }

    @TestMethod
    public void test(){

    }
}
