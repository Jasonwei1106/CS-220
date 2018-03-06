package PJ3;

import java.util.*;
import java.io.*;

// You may add new functions or data in this class 
// You may modify any functions or data members here
// You must use Car, GasPump and GasStation classes
// to implement your simulator
class GasStationSimulation {

    // input parameters
    private int numGasPumps, carQSizeLimit;
    private int simulationTime, dataSource;
    private int chancesOfArrival, maxDuration;

    // statistical data
    private int numGoAway, numServed, totalWaitingTime;

    // internal data
    private int carIdCounter;	    // car ID counter
    private GasStation gasStationObj; // Gas station object
    private Scanner dataFile;	    // get car data from file
    private Random dataRandom;	    // get car data using random function

    // most recent car arrival info, see getCarData()
    private boolean anyNewArrival;
    private int serviceDuration;

    // initialize data fields
    private GasStationSimulation() {
        numGasPumps = 0;
        carQSizeLimit = 0;
        simulationTime = 0;
        dataSource = 0;
        chancesOfArrival = 0;
        maxDuration = 0;
        numGoAway = 0;
        numServed = 0;
        totalWaitingTime = 0;
        carIdCounter = 0;
        gasStationObj = null;
        dataFile = null;
        dataRandom = null;
        anyNewArrival = false;
        serviceDuration = 0;
        // add statements
    }

    private void getUserParameters() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter simulation time: ");
        do {
            simulationTime = input.nextInt();
        } while (simulationTime < 1 || simulationTime > 10000);
        System.out.println("Enter the number of gas pumps: ");
        do {
            numGasPumps = input.nextInt();
        } while (numGasPumps < 1 || numGasPumps > 10);
        System.out.println("Enter maximum service time of cars: ");
        do {
            maxDuration = input.nextInt();
        } while (maxDuration < 1 || maxDuration > 500);
        System.out.println("Enter chances (0% < & <= 100%) of new car: ");
        do {
            chancesOfArrival = input.nextInt();
        } while (chancesOfArrival < 1 || chancesOfArrival > 100);

        System.out.println("Enter car queue size limit: ");
        do {
            carQSizeLimit = input.nextInt();
        } while (carQSizeLimit < 1 || carQSizeLimit > 50);
        System.out.println("Enter 1/0 to get data from file/rand(): ");
        do {
            dataSource = input.nextInt();
        } while (dataSource < 0 || dataSource > 1);

        if (dataSource == 1) {
            input.nextLine();
          System.out.print("Enter filename : ");
          String fileName = input.nextLine();
          try {
              File file = new File(fileName);
              dataFile = new Scanner(file);
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
        // read input parameters
        // setup dataFile or dataRandom
        // add statements
    }
    }
    // Refer to step 1 in doSimulation
    // this method is called for each unit simulation time
    private void getCarData() {
        
        if (dataSource == 0) {
            
            dataRandom = new Random();
            anyNewArrival = (((dataRandom.nextInt(100) + 1)) <= chancesOfArrival);
            serviceDuration = dataRandom.nextInt(maxDuration) + 1;
        } else if(dataSource==1){
            int data1 = dataFile.nextInt();
            int data2 = dataFile.nextInt();
            anyNewArrival = (((data1 % 100) + 1) <= chancesOfArrival);
            serviceDuration = (data2 % maxDuration) + 1;
            
        }
        // get next car data : from file or random number generator
        // set anyNewArrival and serviceDuration
        // add statements
    }

    private void doSimulation() {

        // add statements
        gasStationObj = new GasStation(numGasPumps, carQSizeLimit);
        // Initialize GasStation

        // Time driver simulation loop
        for (int currentTime = 0; currentTime < simulationTime; currentTime++) {

            // Step 1: any new car enters the gas station?
            getCarData();

            if (anyNewArrival) {
                carIdCounter++;
                Car newcar = new Car(carIdCounter, serviceDuration, currentTime);
                if (gasStationObj.isCarQTooLong()) {
                    System.out.println("the line is to long, Car#" + carIdCounter + "leave the line");
                    numGoAway++;
                } else {
                    gasStationObj.insertCarQ(newcar);
                    System.out.println("\tCar #" + carIdCounter + " arrives with duration " + newcar.getServiceDuration() + " units.");
                }

                // Step 1.1: setup car data
                // Step 1.2: check car waiting queue too long?
                //           if it is too long, update numGoaway
                //           else enter car queue
            } else {
                System.out.println("\tNo new car!");
            }

            // Step 2: free busy pumps that are done at currentTime, add to free pumpQ
            for (int i = 0; i < gasStationObj.numBusyGasPumps(); i++) {
				GasPump newGasPump = gasStationObj.getFrontBusyGasPumpQ();
				if (newGasPump.getEndIntervalTime() <= currentTime) {
					newGasPump = gasStationObj.removeBusyGasPumpQ();
					Car newCar = newGasPump.switchBusyToFree();
					System.out.println("\tCar # " + newCar.getCarId()
							+ " is done.");
					gasStationObj.insertFreeGasPumpQ(newGasPump);
					System.out.println("\tPump #" + newGasPump.getPumpId()
							+ " is free.");
                                }
            }
            // Step 3: get free pumps to serve waiting cars 
            for (int i = 0; i < gasStationObj.numFreeGasPumps(); i++) {
                if (gasStationObj.numWaitingCars() != 0) {
                    Car newCar = gasStationObj.removeCarQ();
                    GasPump newGasPump = gasStationObj.removeFreeGasPumpQ();
                    newGasPump.switchFreeToBusy(newCar, currentTime);
                    gasStationObj.insertBusyGasPumpQ(newGasPump);
                    System.out.println("\tCar # " + newCar.getCarId()+ " gets a pump.");
                            
                    System.out
                            .println("\tPump # " + newGasPump.getPumpId()+ " starts serving car # "+ newCar.getCarId() + " for "+ newCar.getServiceDuration() + " units.");
                                    
                    System.out.println("\tCar # "+ newGasPump.getCurrentCar().getCarId() + ", currently served, is set to now busy pump # "+ newGasPump.getPumpId());
                       
                    numServed++;
                    totalWaitingTime = totalWaitingTime
                            + (currentTime - newCar.getArrivalTime());
                }
            }
        } // end simulation loop

        // clean-up - close scanner
    }

    private void printStatistics() {
        System.out.println("End of simulation report.");
		System.out.println("# total arrival cars: " + carIdCounter);
		System.out.println("# cars gone away: " + numGoAway);
		System.out.println("# cars served: " + numServed);
		System.out.println("*** Current Pump Info. ***");
		System.out.println("# waiting cars: " + gasStationObj.numWaitingCars());
		System.out.println("# busy pumps: " + gasStationObj.numBusyGasPumps());
		System.out.println("# free pumps: " + gasStationObj.numFreeGasPumps());
		System.out.println("Total waiting line: " + totalWaitingTime);
                if(numServed!=0){
                System.out.println("Average waiting time:" + (double)((totalWaitingTime*1.0)/numServed));
                } else{
                    System.out.println("There is no car coming");
                }
                
		System.out.println(" Busy Pump info. ");
		while (gasStationObj.numBusyGasPumps() > 0) {
			GasPump gasPump = gasStationObj.removeBusyGasPumpQ();
			
			gasPump.printStatistics();
		}
		System.out.println("Free Pump info. ");
		//System.out.println(gasStationObj.numFreeGasPumps());
		while (gasStationObj.numFreeGasPumps() > 0) {
			GasPump gasPump = gasStationObj.removeFreeGasPumpQ();
			
			gasPump.printStatistics();
		}
	
        // add statements into this method!
        // print out simulation results
        // see the given example in project statement
        // you need to display all free and busy gas pumps

        // need to free up all cars in Car queue to get extra waiting time.
        // need to free up all gas pumps in free/busy queues to get extra free & busy time
    }

    // *** main method to run simulation ****
    public static void main(String[] args) {
        GasStationSimulation gas_station_simulator = new GasStationSimulation();
        gas_station_simulator.getUserParameters();
        gas_station_simulator.doSimulation();
        gas_station_simulator.printStatistics();
    }

}
