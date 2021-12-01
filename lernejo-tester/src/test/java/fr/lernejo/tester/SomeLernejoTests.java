package fr.lernejo.tester;

import fr.lernejo.tester.api.TestMethod;

public class SomeLernejoTests {
    @TestMethod
    public void ok()
    {

    }
    @TestMethod
    public void ko(){

    }
    public void none(){
        throw (new java.lang.IllegalStateException());
    }
}
