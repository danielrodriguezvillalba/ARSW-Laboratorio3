/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2115253
 */
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
