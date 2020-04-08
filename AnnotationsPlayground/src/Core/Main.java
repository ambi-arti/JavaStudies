package Core;

import java.lang.annotation.*;
import java.lang.reflect.*;

public class Main {

    public static void main(String[] args) {
	System.out.println("Runs!");
	Tester(MyClass.class);

    }
    public static void Tester(Class<?> clazz) {
	try {
	    if (clazz.isAnnotationPresent(MyInterfaceCollection.class)) {
		MyInterface[] mic = clazz.getAnnotation(MyInterfaceCollection.class).value();
	    Constructor<?> ctr = clazz.getDeclaredConstructor();
	    MyClass mc = (MyClass)ctr.newInstance();
	    Method[] meth = clazz.getDeclaredMethods();	
	    for (Method m: meth) {
		    Class<?>[] testClasses = new Class<?>[mic.length];
		    for (int i=0;i<mic.length;i++) {
			testClasses[i] = mic[i].value();
		    }
		    
		    for (Class<?> testClass: testClasses) {
			Class<?> mType = m.getParameterTypes()[0];
			Method[] testMeth = testClass.getDeclaredMethods();
			Constructor<?> testCtr = testClass.getDeclaredConstructor();
			Test t = (Test)testCtr.newInstance();
			for (Method tm: testMeth) {
			    tm.setAccessible(true);
			    if (tm.getReturnType()==mType&&tm.isAnnotationPresent(ITest.class)) {
				m.invoke(mc, tm.invoke(t));
				break;
			    }
			}
		    }

		}
	    }
	}
	catch (IllegalAccessException iae) {
	    System.out.println(iae);
	}
	catch (InvocationTargetException ite) {
	    System.out.println(ite);
	}
	catch (InstantiationException ie) {
	    System.out.println(ie);
	}
	catch (NoSuchMethodException nsme) {
	    System.out.println(nsme);
	}
    }
   
}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MyInterfaceCollection.class)
@interface MyInterface{
    Class<?> value();
}



@Retention(RetentionPolicy.RUNTIME)
@interface MyInterfaceCollection{
    MyInterface[] value();
}


@Retention(RetentionPolicy.RUNTIME)
@interface ITest{
}

@MyInterface(Tests.class)
@MyInterface(Tests2.class)
class MyClass {
	public void testString(String x) {
	    System.out.println("Test: "+x);
	}
    	@MyInterface(Tests.class)
	public void testInt(int x) {
	    System.out.println("Test: "+x);
	}
    	@MyInterface(Tests.class)
	public void testAux(Auxillary x) {
	    System.out.println("Test: "+x);
	}
}

interface Test {
}
