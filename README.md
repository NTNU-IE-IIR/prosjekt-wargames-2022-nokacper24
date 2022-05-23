# Wargames
Programming project in [IDATA2001](https://www.ntnu.no/studier/emner/IDATA2001#tab=omEmnet) at NTNU.  
Author: [Kacper Łukasz Nowicki](https://github.com/nokacper24)

## Table of Contents
- [Introduction](#Introduction)
- [Functional requirements](#Functional-requirements)
	- [Description of the diagram](#Description-of-the-diagram)
- [Design](#Design)
	- [Data](#Data)
	- [Logic](#Logic)
	- [UI](#UI)
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

Since there was high focus on customization of the armies, the application must get and handle user inputs. The problem with allowing the user to type whatever they want is that the user may try to type in invalid inputs, such as negative health, or empty name for the units. This was addressed by disabling the OK button and displaying detailed information to the user when for instance the `name` field is emptied.  
![unit creation](/images/unitcreationemptyname.png)  

In addition to that, the application gives reasonable feedback to the user when any errors occur. For instance, when a "corrupt" file is being loaded, it displays a detailed error dialog.  
![file error dialog](/images/fileerrordialog.png) ![corrupt file](/images/corruptfile.png)  

## Design
Classes in the program are built with maintainability, readability and future development in mind. They are divided into 3 main packages: data, logic and UI.  

#### Data
This package has all classes responsible for storing data and all logic closely related to the said data. It has the abstract `Unit` class, all unit subclasses, `Army` class and `UnitFactory` class.  
Below, you can see the data package class diagram with inheritance.  
![datapackageinheritance.png](/images/datapackageinheritance.png)  
`Unit` is an abstract class (meaning no objects of this class can be created, only of its subclasses), and all unit types inherit from it. It has an inner `UnitType` class which is an enum. The decision to make this enum an inner class is motivated by the fact it's very closely connected to `Unit`.  
Army class represents a collection of units and an army name.  
`Unit Factory` is a factory design pattern. It is used to simplify creation of units, allows creation of multiple units at the same time.  

#### Logic
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

#### UI
This package has the `Controller`, `WarGames` and `WarGaemsApplication`.  
`WarGames` is the starting point for JavaFX application, it creates the main window.  
`Controller` is responsible for displaying data in the tables and text fields, as well as handling button inputs.  

UI package has a sub-package **dialogs**. This package has all custom dialogs: `ArmySetupDialog, UnitDialog, MultipleUnitsDialog` and it has `DialogFactory`.  
`DialogFactory` is used to create all types of dialogs used in the application. This is a second use of a factory design pattern in the application. The purpose is to simplify creation of dialogs, and standardization.  
For instance, the same looking error dialogs can be created from different parts of the GUI by simply calling create methods in the `DialogFactory`, avoiding code duplication.

## Implementation


## Process


## Reflection


## Conclusion


*This README file is simultaneously the project report.*