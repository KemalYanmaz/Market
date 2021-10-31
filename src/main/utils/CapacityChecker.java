package main.utils;

import main.persons.SpaceOccupying;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that helps to control capacity of a Space
 *
 */
public class CapacityChecker <T extends SpaceOccupying>{

    private SpaceOccupying[] spaceOccupyings;
    private int amountOfInSpace;
    private final int capacity;
    private final Queue<SpaceOccupying> spaceOccupyingsInQueue;
    private final Lock lock = new ReentrantLock();
    /**
     * Class constructor specifying capacity of object check.
     *
     * @param capacity  the capacity that will checked for
     */
    public CapacityChecker(int capacity){
        this.spaceOccupyings = new SpaceOccupying[capacity];
        this.capacity = capacity;
        this.spaceOccupyingsInQueue = new ConcurrentLinkedDeque<>();
        this.amountOfInSpace = 0;
    }

    /**
     * A method to append SpaceOccupier to capacity
     *
     * @param spaceOccupying new SpaceOccupier to join capacity
     */
    public void append(SpaceOccupying spaceOccupying){
        if(isAtSpace(spaceOccupying))return;
        if(!isCapacityFull()){
            addOccupierToInside(spaceOccupying);
        }else{
            appendOccupierToQueue(spaceOccupying);
        }
    }
    /**
     * A method to remove SpaceOccupier from capacity
     *
     * @param spaceOccupying SpaceOccupier to remove from capacity
     */
    public void remove(SpaceOccupying spaceOccupying){
        removeOccupierFromSpace(spaceOccupying);
        addOccupierFromQueueToInside();
    }

    /**
     * A method to check whether given SpaceOccupier is belongs
     *
     * @param spaceOccupying a SpaceOccupier to check
     */
    public boolean isInside(SpaceOccupying spaceOccupying){
        for (int i = 0; i < amountOfInSpace; i++) {
            if (spaceOccupyings[i].equals(spaceOccupying)) {
                return true;
            }
        }
        return false;
    }

    public List<SpaceOccupying> getOccupiers(){
        return Arrays.stream(spaceOccupyings).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public boolean isInQueue(SpaceOccupying spaceOccupying){
        return spaceOccupyingsInQueue.contains(spaceOccupying);
    }
    public boolean isAtSpace(SpaceOccupying spaceOccupying){
        return isInQueue(spaceOccupying) || isInside(spaceOccupying);
    }
    private void addOccupierToInside(SpaceOccupying occupier){
        if(!isCapacityFull()){
            spaceOccupyings[amountOfInSpace]=occupier;
            amountOfInSpace+=1;
        }
    }
    private void removeOccupierFromSpace(SpaceOccupying occupier){
        for(int i=0;i<amountOfInSpace;i++){
            if(spaceOccupyings[i].equals(occupier)){
                if(i==(amountOfInSpace-1)){
                    spaceOccupyings[i]=null;
                }else{
                    spaceOccupyings[i] = spaceOccupyings[amountOfInSpace-1];
                    spaceOccupyings[amountOfInSpace-1]=null;
                }
                amountOfInSpace-=1;
                break;
            }
        }
    }
    private void appendOccupierToQueue(SpaceOccupying occupying){
        spaceOccupyingsInQueue.add(occupying);
    }
    private void addOccupierFromQueueToInside(){
        if(!isQueueEmpty()){
            addOccupierToInside(getOccupierFromQueue());
        }
    }
    private SpaceOccupying getOccupierFromQueue(){
        return spaceOccupyingsInQueue.poll();
    }
    private boolean isQueueEmpty(){
        return spaceOccupyingsInQueue.isEmpty();
    }

    private boolean isCapacityFull(){
        return amountOfInSpace==capacity;
    }

    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append("İçerdekiler: \n");

        IntStream.range(0,amountOfInSpace).forEach(i->{
            s.append(i + 1).append("-)").append(spaceOccupyings[i]).append("\n");
        });

        s.append("\nSıradakiler: \n");

        IntStream.range(0,spaceOccupyingsInQueue.size()).forEach(i->{
                s.append(spaceOccupyingsInQueue.poll()).append("\n");
        });
        return s.toString();
    }
}

