# Wargames
Programming project in [IDATA2001](https://www.ntnu.no/studier/emner/IDATA2001#tab=omEmnet) at NTNU.  
Author: [Kacper Łukasz Nowicki](https://github.com/nokacper24)

## Introduction
War Games is a simple battle simulator. It is a portfolio project in IDATX2001 at NTNU. The application is meant to simulate a battle between two armies.  
![main window](/images/mainwindow.png)

## Functional requirements
The application offers a graphical user interface, showing the user details of both armies, before and after a battle was simulated. All functionality is depicted in the use case diagram below.  
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

The application gives reasonable feedback to the user when errors occur. For instance, when a "corrupt" file is being loaded, it displays a detailed error dialog.  
![corrupt file](/images/corruptfile.png)  
![file error dialog](/images/fileerrordialog.png)  

## Design


## Implementation


## Process


## Reflection


## Conclusion


*This README file is simultaneously the project report.*