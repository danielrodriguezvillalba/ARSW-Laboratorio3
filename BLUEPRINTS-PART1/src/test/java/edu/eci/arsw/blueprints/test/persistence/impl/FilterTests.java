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

import java.awt.List;

/**
 *
 * @author 2115253
 */
public class FilterTests {
    @Test
    public void pruebaFiltroRedundancia() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        RedundancyFiltering rf = new RedundancyFiltering();
//      Blueprint with redundancy.
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15), new Point(40, 40), new Point(40, 40), new Point(15, 15), new Point(30, 30)};
        Blueprint bp0=new Blueprint("nico", "dracken",pts0);
//      Blueprint without redundancy, the one being compared to.
        Point[] pts1=new Point[]{new Point(40, 40),new Point(15, 15), new Point(30, 30)};
        Blueprint bp1=new Blueprint("dani", "futhenchi",pts1);
        ibpp.saveBlueprint(bp0);
        ibpp.saveBlueprint(bp1);
        System.out.println(ibpp.getBlueprint("", "mypaint"));
        Blueprint bpToTest = rf.filtrar(ibpp.getBlueprint("nico", "dracken"));
//        System.out.println(bpToTest.getPoints().toArray().toString());
//        System.out.println(new Object[]{new Point(40, 40),new Point(15, 15), new Point(30, 30)}.toString());
//        String t = new String("b");
//        System.out.println("b"==t);
        
        assertTrue(bpToTest.equals(bp1)); 
    }
}
