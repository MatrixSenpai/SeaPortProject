# SeaPortsProject
This project creates a basic world with the following hierarchy 
* Main Interface
  * World Tree
  * Jobs Pane
  * Search
  * Descriptive Area
* World
  * Port
    * Dock
      * Ship
        * Job(s)
        * Passengers/Cargo
    * Port Queue
    * Assigned Personnel
    
 All code is licensed under the WTFPL.  
 A copy of this license is available with this software or at http://wtfpl.net/
 
## Design Methodology
* Interface Design  
The interface is designed to be as clear and concise as possible.  
The left panel is a JTree showing the world as loaded by a data file.  
The right panel contains a (?) showing the resource pool available for a port.  
The bottom panel contains a table showing the jobs assigned to docked ships
and their progress.  
The center panel contains a descriptive text area that is changed based on a tree selection
or when the file is loaded

* Code Design  
SeaPortsProject is designed with a delegation methodology in mind. Each object is
responsible for displaying its own properties and completing tasks that are specific
to that object, but it *delegates* tasks to a lower level that concern its children.  
For example, looking at the printing method, a BaseObject is responsible for printing
out its own properties, but requests its child's printing method, and passes that along.  
This concept allows for more minimalistic code and faster processing time.