# ARSW-Laboratorio3

## Integrantes:
  ```
  Nicolas Cardenas Chaparro
  Daniel Felipe Rodriguez Villalba
  ```
## Compile and run instructions:

  - Compilar: Use el comando `mvn package`
  - Ejecutar Pruebas: Use el comando `mvn test`
  
# Introduction to Spring and Configuration using annotations
## Part I - Basic workshop 

  To illustrate the use of the Spring framework, and the development environment for its use through Maven (and NetBeans), the              configuration of a text analysis application will be made, which makes use of a grammar verifier that requires a spelling checker. The grammar checker will be injected, at the time of execution, with the spelling checker required (for now, there are two available: English and Spanish).
  
  1.  Open the project sources in NetBeans.

  2.  Review the Spring configuration file already included in the project (src / main / resources). It indicates that Spring will automatically search for the 'Beans' available in the indicated package.
  
  ```
  <?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.2.xsd
">

    <context:component-scan base-package="edu.eci.arsw" />
    
</beans>

  ```

  3.  Making use of the Spring configuration based on annotations mark with the annotations @Autowired and @Service the dependencies that must be injected, and the 'beans' candidates to be injected -respectively-:

      - GrammarChecker will be a bean, which depends on something like 'SpellChecker'.
      - EnglishSpellChecker and SpanishSpellChecker are the two possible candidates to be injected. One must be selected, or another, but NOT both (there would be dependency resolution conflict). For now, have EnglishSpellChecker used. 
      
  ```
  package edu.eci.arsw.springdemo;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SpanishSpellChecker implements SpellChecker {

	@Override
	public String checkSpell(String text) {
		return "revisando ("+text+") con el verificador de sintaxis del espanol";
                
                
	}

}
  ```

  4.  Make a test program, where an instance of GrammarChecker is created by Spring, and use it:
  
  ```
  public class Main {

    public static void main(String a[]) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        GrammarChecker gc = ac.getBean(GrammarChecker.class);
        System.out.println(gc.check("la la la "));
    }

}
  ```
####  Resultado:
  
  `
  Spell checking output:revisando (la la la ) con el verificador de sintaxis del espanolPlagiarism checking output: Not available yet
  `
    

## Part II

  1.  Modify the configuration with annotations so that the Bean 'GrammarChecker' now makes use of the SpanishSpellChecker class (so that GrammarChecker is injected with EnglishSpellChecker instead of SpanishSpellChecker.) Verify the new result.
  
  ```
  package edu.eci.arsw.springdemo;

import org.springframework.stereotype.Service;

@Service
public class EnglishSpellChecker implements SpellChecker {

	@Override
	public String checkSpell(String text) {		
		return "Checked with english checker:"+text;
	}        
}
  ```
####  Resultado

`
Spell checking output:Checked with english checker:la la la Plagiarism checking output: Not available yet
`
  
  
  
# Blueprint Management 1
## Part I

Configure the application to work under a dependency injection scheme, as shown in the previous diagram.
The above requires:

  1.  Add the dependencies of Spring. Add the Spring settings. Configure the application - by means of annotations - so that the persistence scheme is injected when the BlueprintServices bean is created. Complete the getBluePrint() and getBlueprintsByAuthor() operations.Implement everything required from the lower layers (for now, the available persistence scheme InMemoryBlueprintPersistence) by adding the corresponding tests in InMemoryPersistenceTest.

  2.  Make a program in which you create (through Spring) an instance of BlueprintServices, and rectify its functionality: register plans, consult plans, register specific plans, etc.
  
  ```
  public class Main {

    public static void main(String a[]) throws BlueprintNotFoundException, BlueprintPersistenceException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Point[] pts2 = new Point[]{new Point(12, 12), new Point(20, 20), new Point(15, 20)};
        Blueprint bp0 = new Blueprint("john", "thepaint", pts1);
        Blueprint bp1 = new Blueprint("john", "hello", pts2);
        Blueprint bp2 = new Blueprint("david", "odisea", pts2);
        bps.addNewBlueprint(bp0);
        bps.addNewBlueprint(bp1);
        bps.addNewBlueprint(bp2);
        
        Blueprint pru = bps.getBlueprint("john", "thepaint");
        System.out.println("Nombre del autor "+pru.getAuthor() + " ,nombre de la obra "+pru.getName());
        System.out.println("Ademas este autor escribio ");
        
        Set<Blueprint> pruV = bps.getBlueprintsByAuthor("john");
        for (Blueprint b : pruV){
            System.out.println(b.getName());
        }
    }
}
  ```
  
  3.  You want the plan query operations to perform a filtering process, before returning the planes consulted. These filters are looking to reduce the size of the plans, removing redundant data or simply sub-sampling, before returning them. Adjust the application (adding the abstractions and implementations you consider) so that the BlueprintServices class is injected with one of two possible 'filters' (or possible future filters). The use of more than one at a time is not contemplated:
  
-	(A) Redundancy filtering: deletes consecutive points from the plane that are repeated.
      
```
	  @Service
	public class RedundancyFiltering implements Filter {

	    @Override
	    public Blueprint filtrar(Blueprint bp) {
		List<Point> actual = bp.getPoints();
		Point[] nueva = new Point[bp.getPoints().size()];
		int i = 0;
		int con = 0;
		for (Point p : actual) {
		    boolean var = false;
		    if (nueva.length > 0) {
			for (int j = 0; j < nueva.length; j++) {
			    if (nueva[j] != null) {
				if (nueva[j].getX() == p.getX() && nueva[j].getY() == p.getY()) {var = true;}
			    }
			}
		    }
		    if (!var) {
			nueva[i] = p;
			con++;
			i++;
		    }
		}
		Point[] cont = new Point[con];
		for (int j = 0; j < con; j++) {
			cont[j]=nueva[j];
			System.out.println(cont[j].getX());
			System.out.println(cont[j].getY());
		 }
		Blueprint finala = new Blueprint(bp.getAuthor(), bp.getName(), cont);
		return finala;
	    }
	}
```
  
  
-	(B) Subsampling filtering: suppresses 1 out of every 2 points in the plane, interspersed. 	
      





```

	  @Service
	public class SubsamplingFiltering implements Filter{

	    @Override
	    public Blueprint filtrar(Blueprint bp) {
		List<Point> actual = bp.getPoints();
		Point[] nueva = new Point[bp.getPoints().size()];
		boolean flag = true;
		int i = 0;
		int con = 0;
		for (Point p : actual){
		    if(!flag){
			nueva[i] = p;
			flag = true;
			i++;
			con++;
		    }
		    else{flag = false;}
		}
		Point[] fixedArray = new Point[con];
		for (int j = 0; j < con; j++) {
			fixedArray[j]=nueva[j];            
	     }
		Blueprint finala = new Blueprint(bp.getAuthor(), bp.getName(), fixedArray);
		return finala;
	    }       
	}
  ```
  4.  Add the corresponding tests to each of these filters, and test its operation in the test program, verifying that only by changing the position of the annotations - without changing anything else - the program returns the filtered planes in the way (A) or in the way (B).
  
  ```
  @Test
    public void pruebaFiltroRedundancia() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence imbp=new InMemoryBlueprintPersistence();
        RedundancyFiltering rf = new RedundancyFiltering();
//      Blueprint with redundancy.
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15), new Point(40, 40), new Point(40, 40), new Point(15, 15), new Point(30, 30)};
        Blueprint bp0=new Blueprint("nico", "dracken",pts0);
//      Blueprint without redundancy, the one being compared to.
        Point[] pts1=new Point[]{new Point(40, 40),new Point(15, 15), new Point(30, 30)};
        Blueprint bp1=new Blueprint("dani", "futhenchi",pts1);
        imbp.saveBlueprint(bp0);
        imbp.saveBlueprint(bp1);
        Blueprint bpToTest = rf.filtrar(imbp.getBlueprint("nico", "dracken"));//        
        assertTrue(bpToTest.equals(bp1)); 
    }
    
    @Test
    public void pruebaFiltroSubSampling() throws BlueprintPersistenceException, BlueprintNotFoundException{
    	InMemoryBlueprintPersistence imbp2=new InMemoryBlueprintPersistence();
    	SubsamplingFiltering ssf = new SubsamplingFiltering();
//      Blueprint to filter with the subsampling filter.
    	Point[] pts0=new Point[]{new Point(10, 10),new Point(20, 20), new Point(30, 30), new Point(40, 40), new Point(50, 50), new Point(60, 60)};
        Blueprint bp0=new Blueprint("nico", "dracken",pts0);
//      Expected Blueprint, the one being compared to.
        Point[] pts1=new Point[]{new Point(20, 20),new Point(40, 40), new Point(60, 60)};
        Blueprint bp1=new Blueprint("dani", "futhenchi",pts1);
        imbp2.saveBlueprint(bp0);
        imbp2.saveBlueprint(bp1);
        Blueprint bpToTest = ssf.filtrar(imbp2.getBlueprint("nico", "dracken"));
        assertTrue(bpToTest.equals(bp1));
    }
  ```
