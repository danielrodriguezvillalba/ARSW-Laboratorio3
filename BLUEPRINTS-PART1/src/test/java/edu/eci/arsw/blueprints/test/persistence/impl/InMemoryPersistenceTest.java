/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.impl.RedundancyFiltering;
import edu.eci.arsw.blueprints.filters.impl.SubsamplingFiltering;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }
    @Test
    public void showBlueprintWithAuthorAndName() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        ibpp.saveBlueprint(bp);
        assertTrue(bp.equals(ibpp.getBlueprint("john", "thepaint")));
    }
    
    
    @Test
    public void showBlueprintWithAuthor() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        Blueprint bp1=new Blueprint("john", "hello",pts);
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp);
        assertTrue(ibpp.getBlueprintByAuthor("john").contains(bp));
        assertTrue(ibpp.getBlueprintByAuthor("john").contains(bp1));
    }
    
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
    
}
