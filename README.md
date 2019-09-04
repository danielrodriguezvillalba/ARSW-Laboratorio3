# ARSW-Laboratorio3

## Integrantes:
  ```
  Nicolas Cardenas Chaparro
  Daniel Felipe Rodriguez Villalba
  ```
## Compile and run instructions:

  - Compilar: Use el comando `mvn package`
  - Ejecutar Pruebas: Use el comando `mvn test`
  
## Introduction to Spring and Configuration using annotations
## Part I - Basic workshop 

  To illustrate the use of the Spring framework, and the development environment for its use through Maven (and NetBeans), the              configuration of a text analysis application will be made, which makes use of a grammar verifier that requires a spelling checker. The grammar checker will be injected, at the time of execution, with the spelling checker required (for now, there are two available: English and Spanish).
  
  1.  Open the project sources in NetBeans.

  2.  Review the Spring configuration file already included in the project (src / main / resources). It indicates that Spring will automatically search for the 'Beans' available in the indicated package.

  3.  Making use of the Spring configuration based on annotations mark with the annotations @Autowired and @Service the dependencies that must be injected, and the 'beans' candidates to be injected -respectively-:

      - GrammarChecker will be a bean, which depends on something like 'SpellChecker'.
      - EnglishSpellChecker and SpanishSpellChecker are the two possible candidates to be injected. One must be selected, or another, but NOT both (there would be dependency resolution conflict). For now, have EnglishSpellChecker used. 

  4.  Make a test program, where an instance of GrammarChecker is created by Spring, and use it:
  

## Part II

  1.  Modify the configuration with annotations so that the Bean 'GrammarChecker' now makes use of the SpanishSpellChecker class (so that GrammarChecker is injected with EnglishSpellChecker instead of SpanishSpellChecker.) Verify the new result.
  
  
## Blueprint Management 1
## Part I

Configure the application to work under a dependency injection scheme, as shown in the previous diagram.
The above requires:

  1.  Add the dependencies of Spring. Add the Spring settings. Configure the application - by means of annotations - so that the persistence scheme is injected when the BlueprintServices bean is created. Complete the getBluePrint() and getBlueprintsByAuthor() operations.Implement everything required from the lower layers (for now, the available persistence scheme InMemoryBlueprintPersistence) by adding the corresponding tests in InMemoryPersistenceTest.

  2.  Make a program in which you create (through Spring) an instance of BlueprintServices, and rectify its functionality: register plans, consult plans, register specific plans, etc.
  
  3.  You want the plan query operations to perform a filtering process, before returning the planes consulted. These filters are looking to reduce the size of the plans, removing redundant data or simply sub-sampling, before returning them. Adjust the application (adding the abstractions and implementations you consider) so that the BlueprintServices class is injected with one of two possible 'filters' (or possible future filters). The use of more than one at a time is not contemplated:
      - (A) Redundancy filtering: deletes consecutive points from the plane that are repeated.
      - (B) Subsampling filtering: suppresses 1 out of every 2 points in the plane, interspersed. 
      
  4.  Add the corresponding tests to each of these filters, and test its operation in the test program, verifying that only by changing the position of the annotations - without changing anything else - the program returns the filtered planes in the way (A) or in the way (B).
