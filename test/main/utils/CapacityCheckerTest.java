package main.utils;

import main.persons.Person;
import main.persons.SpaceOccupying;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CapacityCheckerTest {

    SpaceOccupying occupier1 = new Person("Occupier1");
    SpaceOccupying occupier2 = new Person("Occupier2");

    @Test
    void append() {

        CapacityChecker<Person> capacityChecker = new CapacityChecker<>(1);
        capacityChecker.append(occupier1);
        capacityChecker.append(occupier2);

        Assertions.assertTrue(capacityChecker.isInside(occupier1));
        Assertions.assertFalse(capacityChecker.isInQueue(occupier1));

        Assertions.assertTrue(capacityChecker.isInQueue(occupier2));
        Assertions.assertFalse(capacityChecker.isInside(occupier2));

        Assertions.assertTrue(capacityChecker.isAtSpace(occupier1));
        Assertions.assertTrue(capacityChecker.isAtSpace(occupier2));

    }

    @Test
    void remove() {

        CapacityChecker<Person> capacityChecker = new CapacityChecker<>(1);
        capacityChecker.append(occupier1);
        capacityChecker.append(occupier2);

        capacityChecker.remove(occupier1);

        Assertions.assertFalse(checkIfInside(capacityChecker,occupier1));

        Assertions.assertFalse(capacityChecker.isInQueue(occupier2));
        Assertions.assertTrue(capacityChecker.isAtSpace(occupier2));
        Assertions.assertTrue(capacityChecker.isInside(occupier2));

        capacityChecker.remove(occupier2);

        Assertions.assertFalse(checkIfInside(capacityChecker,occupier2));

    }


    private boolean checkIfInside(CapacityChecker capacityChecker,SpaceOccupying spaceOccupying){
        return capacityChecker.isInside(spaceOccupying) || capacityChecker.isAtSpace(spaceOccupying) || capacityChecker.isInQueue(spaceOccupying);
    }
}