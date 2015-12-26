package com.badlogic.androidgames.framework.gl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.androidgames.framework.GameObject;

import android.util.FloatMath;

public class SpatialHashGrid {
    List<GameObject>[] dynamicCells;
    List<GameObject>[] staticCells;
    int cellsPerRow;
    int cellsPerCol;
    float cellSize;
    int[] cellIds = new int[4];
    List<GameObject> foundObjects;
    
    @SuppressWarnings("unchecked")
    public SpatialHashGrid(float worldWidth, float worldHeight, float cellSize) {
        this.cellSize = cellSize;
        this.cellsPerRow = (int)FloatMath.ceil(worldWidth/cellSize);
        this.cellsPerCol = (int)FloatMath.ceil(worldHeight/cellSize);
        int numCells = cellsPerRow * cellsPerCol;
        dynamicCells = new List[numCells];
        staticCells = new List[numCells];
        for(int i = 0; i < numCells; i++) {
            dynamicCells[i] = new ArrayList<GameObject>(10);
            staticCells[i] = new ArrayList<GameObject>(10);
        }
        foundObjects = new ArrayList<GameObject>(10);
    }
    
    public void insertStaticObject(GameObject obj) {
        int[] cellIds = getCellIds(obj);
        int i = 0;
        int cellId = -1;
        while(i <= 3 && (cellId = cellIds[i++]) != -1) {
            staticCells[cellId].add(obj);
        }
    }
    
    public void insertDynamicObject(GameObject obj) {
        int[] cellIds = getCellIds(obj);
        int i = 0;
        int cellId = -1;
        while(i <= 3 && (cellId = cellIds[i++]) != -1) {
            dynamicCells[cellId].add(obj);
        }
    }
    
    public void removeObject(GameObject obj) {
        int[] cellIds = getCellIds(obj);
        int i = 0;
        int cellId = -1;
        while(i <= 3 && (cellId = cellIds[i++]) != -1) {
            dynamicCells[cellId].remove(obj);
            staticCells[cellId].remove(obj);
        }
    }
    
    public void clearDynamicCells(GameObject obj) {
        int len = dynamicCells.length;
        for(int i = 0; i < len; i++) {
            dynamicCells[i].clear();
        }
    }
    
    public List<GameObject> getPotentialColliders(GameObject obj) {
        foundObjects.clear();
        int[] cellIds = getCellIds(obj);
        int i = 0;
        int cellId = -1;
        while(i <= 3 && (cellId = cellIds[i++]) != -1) {
            int len = dynamicCells[cellId].size();
            for(int j = 0; j < len; j++) {
                GameObject collider = dynamicCells[cellId].get(j);
                if(!foundObjects.contains(collider))
                    foundObjects.add(collider);
            }
            
            len = staticCells[cellId].size();
            for(int j = 0; j < len; j++) {
                GameObject collider = staticCells[cellId].get(j);
                if(!foundObjects.contains(collider))
                    foundObjects.add(collider);
            }
        }
        return foundObjects;
    }
    
    public int[] getCellIds(GameObject obj) {
        int x1 = (int)FloatMath.floor(obj.bounds.lowerLeft.x / cellSize);
        int y1 = (int)FloatMath.floor(obj.bounds.lowerLeft.y / cellSize);
        int x2 = (int)FloatMath.floor((obj.bounds.lowerLeft.x + obj.bounds.width) / cellSize);
        int y2 = (int)FloatMath.floor((obj.bounds.lowerLeft.y + obj.bounds.height) / cellSize);
        
        if(x1 == x2 && y1 == y2) {
            if(x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol)
                cellIds[0] = x1 + y1 * cellsPerRow;
            else
                cellIds[0] = -1;
            cellIds[1] = -1;
            cellIds[2] = -1;
            cellIds[3] = -1;
        }
        else if(x1 == x2) {
            int i = 0;
            if(x1 >= 0 && x1 < cellsPerRow) {
                if(y1 >= 0 && y1 < cellsPerCol)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if(y2 >= 0 && y2 < cellsPerCol)
                    cellIds[i++] = x1 + y2 * cellsPerRow;
            }
            while(i <= 3) cellIds[i++] = -1;
        }
        else if(y1 == y2) {
            int i = 0;
            if(y1 >= 0 && y1 < cellsPerCol) {
                if(x1 >= 0 && x1 < cellsPerRow)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if(x2 >= 0 && x2 < cellsPerRow)
                    cellIds[i++] = x2 + y1 * cellsPerRow;
            }
            while(i <= 3) cellIds[i++] = -1;                       
        }
        else {
            int i = 0;
            int y1CellsPerRow = y1 * cellsPerRow;
            int y2CellsPerRow = y2 * cellsPerRow;
            if(x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol)
                cellIds[i++] = x1 + y1CellsPerRow;
            if(x2 >= 0 && x2 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol)
                cellIds[i++] = x2 + y1CellsPerRow;
            if(x2 >= 0 && x2 < cellsPerRow && y2 >= 0 && y2 < cellsPerCol)
                cellIds[i++] = x2 + y2CellsPerRow;
            if(x1 >= 0 && x1 < cellsPerRow && y2 >= 0 && y2 < cellsPerCol)
                cellIds[i++] = x1 + y2CellsPerRow;
            while(i <= 3) cellIds[i++] = -1;
        }
        return cellIds;
    }
}