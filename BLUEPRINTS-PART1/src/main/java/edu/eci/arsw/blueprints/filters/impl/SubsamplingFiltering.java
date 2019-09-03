/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2115253
 */
@Service
public class SubsamplingFiltering implements Filter{

    @Override
    public Blueprint filtrar(Blueprint bp) {
        List<Point> actual = bp.getPoints();
        Point[] nueva = new Point[bp.getPoints().size()] ;
        boolean flag = true;
        int i = 0;
        for (Point p : actual){
            if(!flag){
                nueva[i] = p;
                flag = true;
                i++;
            }
            else{flag = false;}
        }
        Blueprint finala = new Blueprint(bp.getAuthor(), bp.getName(), nueva);
        return finala;
    }   
    
}
