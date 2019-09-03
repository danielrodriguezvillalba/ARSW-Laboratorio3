/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.impl.RedundancyFiltering;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 2115253
 */
public class FilterTests {
    @Test
    public void pruebaFiltroRedundancia() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        RedundancyFiltering rf = new RedundancyFiltering();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15), new Point(40, 40), new Point(40, 40), new Point(15, 15), new Point(30, 30)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);

        Blueprint bpToTest = rf.filtrar(ibpp.getBlueprint("mack", "mypaint"));

        assertArrayEquals(new Object[]{new Point(40, 40),new Point(15, 15), new Point(30, 30)}, bpToTest.getPoints().toArray()); 
    }
}
