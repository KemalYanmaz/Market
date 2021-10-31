package main.places;

import main.persons.Person;
import main.persons.SpaceOccupying;
import main.utils.CapacityChecker;

public class Place{

    public CapacityChecker<Person> capacityChecker;

    public Place(int capacity){
        capacityChecker = new CapacityChecker<>(capacity);
    }

    public void addOccupier(SpaceOccupying spaceOccupying){
        capacityChecker.append(spaceOccupying);
    }

    public void removeOccupier(SpaceOccupying spaceOccupying){
        capacityChecker.remove(spaceOccupying);
    }


}
