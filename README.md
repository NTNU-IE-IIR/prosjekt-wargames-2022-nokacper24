*This README file is simultaneously the project report.*
# Wargames
Programming project in [IDATA2001](https://www.ntnu.no/studier/emner/IDATA2001#tab=omEmnet) at NTNU.  
Author: [Kacper Lukasz Nowicki](https://github.com/nokacper24)

## Table of Contents
- [Introduction](#Introduction)
- [Functional requirements](#Functional-requirements)
	- [Description of the diagram](#Description-of-the-diagram)
- [Design](#Design)
	- [Data package](#Data-package)
	- [Logic package](#Logic-package)
	- [UI package](#UI-package)
- [Implementation](#Implementation)
- [Process](#Process)
- [Reflection](#Reflection)
- [Conclusion](#Conclusion)

## Introduction
War Games is a simple battle simulator. It is an assignment in IDATX2001 at NTNU. The application has a rather basic user interface. The main focus was not a pretty GUI, but well written software along with usable and robust GUI.  
![main window](/images/mainwindow.png)  

## Functional requirements
The application offers a graphical user interface, showing the user details of both armies, before and after a battle was simulated. It allows the user to set up both armies and choose terrain for the battle.  
All functionality is depicted in the use case diagram below.  
![usecase diagram](/images/usecasewargames.png)  
### Description of the diagram
As shown, there are 4 main use cases. The user can:
- Start a simulation
	- one extension (optional):
		- if battle cannot be simulated, displays a message to the user
- Choose terrain
- Reset armies (to the state they were in before battle was simulated)
- Set up armies (army1 or army2)
	- This use case has 7 extend relationships (optional use cases):
		- Rename the army
		- Add a unit
		- Add multiple units
		- Edit a specific unit
		- Remove a specific unit
		- Load army from file
		- Save army to file

*The description does not cover all use cases, more details on the diagram itself*  

## Design
Classes in the program are built with maintainability, readability and future development in mind. They are divided into 3 main packages:
- [Data](#Data-package)
- [Logic](#Logic-package)
- [UI](#UI-package)

#### Data package
This package has all classes responsible for storing data and all logic closely related to the said data. It has the abstract `Unit` class, all unit subclasses, `Army` class and `UnitFactory` class.  
Below, you can see the data package class diagram with inheritance.  
![datapackageinheritance.png](/images/datapackageinheritance.png)  
`Unit` is an abstract class (meaning no objects of this class can be created, only of its subclasses), and all unit types inherit from it. It has an inner `UnitType` class which is an enum. The decision to make this enum an inner class is motivated by the fact it's very closely connected to `Unit`.  
Army class represents a collection of units and an army name.  
`Unit Factory` is a factory design pattern. It is used to simplify creation of units, allows creation of multiple units at the same time.  

#### Logic package
This package has the following classes: `Battle`, `ArmyFileHandler`, `TerrainType`, `BattleStillInProgressException`, `IllegalUnitsFileException`.  
`TerrainType` is an enum, and both exception classes are just self-defined exceptions.  
`Battle` takes the responsibility of simulating a battle between two armies.  
At the moment the simulation is rather simple: 
- gets a random unit from army1  
- gets a random unit from army1  
- the unit from army1 attacks the unit from army2  
- the unit from army2 attacks the unit from army1  
- if any of the unit's health is 0 or less, remove the unit from its army  
- repeat  

`ArmyFileHandler` is responsible for loading and saving army files, it throws `IllegalUnitsFileException` when trying to load a file that has either incorrect format or is corrupt in some way. Exception messages are detailed enough to pinpoint the error.  

#### UI package
This package has the `Controller`, `WarGames` and `WarGaemsApplication`.  
`WarGames` is the starting point for JavaFX application, it creates the main window.  
`Controller` is responsible for displaying data in the tables and text fields, as well as handling button inputs.  
In order to separate the responsibility, `Controller` only deals with inputs and updating the GUI, and `WarGaemsApplication` class takes care of the logic.

UI package has a sub-package **dialogs**. This package has all custom dialogs: `ArmySetupDialog`, `UnitDialog`, `MultipleUnitsDialog` and it has `DialogFactory`.  
`DialogFactory` is used to create all types of dialogs used in the application. This is a second use of a factory design pattern in the application. The purpose is to simplify creation of dialogs, and standardization.  
For instance, the same looking error dialogs can be created from different parts of the GUI by simply calling create methods in the `DialogFactory`, avoiding code duplication.

## Implementation
The graphical user interface for WarGames is built on [JavaFX](https://openjfx.io/) framework The main stage (primary stage) is loaded from a `fxml` file at the start of the program, and the `Controller` class mentioned earlier connects it to the backend (underlying logic), `WarGaemsApplication`, thus dividing responsibility.

Another example of responsibility driven design can be found in the `Unit` class's, `attack(Unit unit)` method.  
When a unit attacks another unit, instead of calculating final health for the opponent and setting it, it calculates how much damage it can deal (taking the attack bonus to the account), and calls `takeDamage(int attackDamage)` on the opponent. The opponent then calculates how much damage it should receive, taking the armor and defense bonus in consideration, and sets its own health accordingly.  
This way, the attacker only focuses on dealing damage, and the opponent only on defending itself.  

Due to the fact that  `Unit` is an abstract class, no instances can be constructed. For the purpose of implementing unit tests for this class, `UnitDummy` subclass was implemented (it's in the test folder of the project).  
Generally, we could get an instinct to use a subclass that already exists, such as `RangedUnit` for purposes of testing `Unit` class. But if `RangedUnit` was either removed or edited in the future, the tests could fail, even though `Unit` class itself was not changed.

Since there was high focus on customization of the armies, the application must get and handle user inputs. The problem with allowing the user to type whatever they want is that the user may type invalid inputs, such as negative health, or empty name for the units. This was addressed by disabling the OK button and displaying detailed information to the user when for instance the `name` field is emptied. This effectively forces the user to type in the expected values.  
![unit creation](/images/unitcreationemptyname.png)  
In addition to that, the application gives reasonable feedback to the user when any errors occur. For instance, when a "corrupt" file is being loaded, it displays a detailed error dialog.  
![file error dialog](/images/fileerrordialog.png)![corrupt file](/images/corruptfile.png)  

## Process
The work on the project was carried out in the spring semester of 2022, with a varying intensity. Work was directed and motivated mainly by mandatory assignments in the course. In addition, own notes and to-do lists were made along the way.  

Version control was kept with [Git](https://git-scm.com/) from the begging. In addition, later in the semester in a different project we have started using conventional commit messages. They were later used in this project as well, as a form of a good practice. To make generation of conventional commits easier, [Commitizen](https://commitizen-tools.github.io/commitizen/). Additionally, Commitizen enforces descriptive commit messages, making review of commits easier.  

As mentioned before, the graphical user interface is [JavaFX](https://openjfx.io/) framework, and the application loads the GUI from a `fxml` file. Said file was created with [Scene Builder](https://gluonhq.com/products/scene-builder/).  

Under the development, [Checkstyle](https://checkstyle.sourceforge.io/) with Google checks was used to ensure standard style and readability of code. Additionally, [SonarLint](https://www.sonarlint.org/) was used to ensure quality and security.

## Reflection
In general the project went well, there were no major issues on the way. Something that definitely could use some improvement was regularity of work. Due to other projects with closer deadlines, as well as some personal matters, the project was completed in a number of "sprints".  

The code itself shows a rather high degree of cohesion and low coupling. All classes have well-defined tasks and responsibilities.  
Although, there is an example of high coupling to be found. Namely, if we were to add a new unit type, we would need to also:  
- add new unit type to `UnitType` enum  
- add a creator method in the `UnitFactory`  
- update GUI to also show the number of units of that new type  

The first two points are inevitable, but the last one could be addressed. For instance, instead of displaying all different counts in separate text fields, display one table view, with rows for each unit type and its count. This way, the GUI would not need to know about all different unit types.

Lastly, the user interface surely does not look the most appealing, as it does not use any CSS stylesheets. The focus was to make it user-friendly and robust, and it is that. It is realitevly easy to use, and it gives sensible error messages in response to unexpected behavior.  

Even though, the `Battle` class simulates the battle in a rather primitive way now, this could quite easily be changed in the future. Due to loose coupling and high cohesion in the code, `Battle` class could be replaced with for instance a battlefield, with a grid and different terrain types on different parts of the map. Making such changes would not need to effect other parts of the code, unless we would like to display the grid to the user, of course.

## Conclusion
In conclusion, this project can be considered a success; all requirements from the given assignments were met, and the application is rather robust. The code was written with good design practices in mind, which would surely make future development easier.  
A lot was learned on the way, especially about designing user interfaces and using different software to make the development more effective.