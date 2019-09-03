package edu.eci.arsw.blueprints.main;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Set;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author 2115253
 */
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
