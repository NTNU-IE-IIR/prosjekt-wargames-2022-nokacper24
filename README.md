# Wargames
Programming project in [IDATA2001](https://www.ntnu.no/studier/emner/IDATA2001#tab=omEmnet) at NTNU.  
Author: [Kacper Łukasz Nowicki](https://github.com/nokacper24)

## Table of Contents
- [Introduction](#Introduction)
- [Functional requirements](#Functional-requirements)
- [Design](#Design)
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
##### Description of the diagram
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


## Implementation


## Process


## Reflection


## Conclusion


*This README file is simultaneously the project report.*