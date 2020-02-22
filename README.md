# Traffic light control simulation for testing a state machine

This is a custom component for the [Digital](https://github.com/hneemann/Digital/) simulator.
It adds a graphical traffic light control simulation for testing state machines which are also designed in [Digital](https://github.com/hneemann/Digital/).

## Its features

In the graphical simulation is a crossing with a traffic light on each side of the crossing and one pedestrian light. Cars are driving and pedestrians walking in the crossing if their traffic light is green. 

The traffic lights are inputs from the component and should be outputs of a generated state machine. The outputs of the component are two sensors which detect vertical driving cars and pedestrains, reset and clock. Those should be the inputs of the generated state machine.

Up to three errors in the generated state machine are shown. Additionally if the cars crash they get red.

![animation](https://github.com/dload22/TrafficLightControl/tree/master/distribution/animation.png)

You can download the jar [here](https://github.com/dload22/TrafficLightControl/tree/master/distribution/TrafficLightControlSimulationPlugin-1.0.jar).
Put the file where you want and add the path to the file in the settings of digital under "Java library".

Included in the test folder are test circuits and state machines which works and some which creates errors for testing the component.

![testCircuit](https://github.com/dload22/TrafficLightControl/tree/master/distribution/testCircuit.png)

## How do I get set up?

The easiest way to build the necessary Jar is to use [maven](https://maven.apache.org/).

* JDK 1.8 is needed (either the Oracle JDK 1.8 or OpenJDK 1.8)  
* Clone the repository.
* Replace the `Digital.jar` which is included in this repo with the version you want to use.
* After that run `mvn install` to create the library jar file

## Credits

Thanks to Tobias Danzig who made the predecessor of this simulation plugin. You can see it [here](https://github.com/T0dan/DigitalCarWashSimulation).
