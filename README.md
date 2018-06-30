# notes-plugin
An Intellij plugin for recording and managing file related notes

## Prerequisites
A copy of Intellij Idea: [download](https://www.jetbrains.com/idea/download/#section=mac) 

## Installation 
Install via one of the following:
* Directly from the plugin repository for the [documented workflow](https://www.jetbrains.com/help/idea/installing-updating-and-uninstalling-repository-plugins.html)
* From disk
    1. Obtain a copy of the plugin .jar file via one of the following
       * Download from the [Intellij Plugin Repository](https://plugins.jetbrains.com/plugin/10895-notes-plugin)
       * Clone this repository and build the .jar via Gradle. The .jar file will be located in build/libs/notes-plugin-x.y.z-SNAPSHOT.jar
    2. Choose "Install from Disk" from the plugins section of the preferences window
    
## Usage
* Record a note about a file via context menu:
![Record a note about a file via context menu](readme/addNoteToFile.png "Record a note about a file via context menu")
* Edit a note about a file:
![Edit a note about a file](readme/fileNoteWindow.png "Edit a note about a file")
* View a note about a file via context menu:
![View a note about a file via context menu](readme/viewFileNote.png "View a note about a file via context menu")

* Show annotations for notes about lines in a file via context menu:
![Show annotations for notes about lines in a file via context menu](readme/showNoteAnnotations.png "Show annotations for notes about lines in a file via context menu")
* Edit a note about a file:
![Edit a note about a file](readme/lineNoteWindow.png "Edit a note about a file")
* Annotations automatically updated:
![Annotations automatically updated](readme/updatedNoteAnnotations.png "Annotations automatically updated")

* View a list of all notes via context menu:
![View a list of all notes via context menu](readme/showNoteList.png "View a list of all notes via context menu")
* Select a note from the list:
![Select a note from the list](readme/noteList.png "Select a note from the list")


    
## Running Tests
TODO: write tests

## License
This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/albertpatterson/notes-plugin/blob/master/LICENSE) file for details
