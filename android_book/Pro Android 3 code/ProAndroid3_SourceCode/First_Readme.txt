Welcome to the source code for Pro Android 3!!

This is the README file that will help you get the most from the book by 
helping you get setup with the source of every example in the book. You 
do not need to download and use every file from the www.androidbook.com 
website; you can download code just from the chapters you're interested 
in. Each chapter has its own file (in some cases more than one), and the 
filename matches the chapter. Or, you may have downloaded the one big 
zipfile from the apress.com website. These instructions work for either 
situation, with the differences explained below when importing the 
projects into Eclipse. 

Before you attempt to import any of the projects from the zip files, you
need to ensure that your workstation environment is setup and ready to
receive Android projects. Please refer to chapter 2 for information on
installing Java, Eclipse and the Android SDK.

The other thing you should do before loading our sample projects is make 
sure that you have Android platforms loaded, via the Android SDK and AVD 
Manager (see Figure 2-2 in the book). One way to get to this is to 
locate the android script under the tools directory of the Android SDK, 
and launch it. The other way is to use the Android SDK and AVD Manager 
menu item from the Eclipse Window menu. Either way, make sure you have 
the following platforms loaded: 

	Android 1.6 (4)
	Android 2.1 (7)
	Android 2.2 (8)
	Android 2.3.3 (10)
	Android 3.0 (11)

If you'll be working with projects that display maps, make sure you also
go into the Third party Add-ons, under Google Inc. add-ons, and get the
Google APIs for the platforms in the list above, i.e., 4, 7, 8, 10 and
11.

You'll need to import the book's projects into an Eclipse workspace. 
Decide which workspace you want to load these projects into, or create a 
new one. To get a new workspace in Eclipse, you can either specify a new 
workspace when you launch Eclipse (and it asks for the workspace), or once 
you're in Eclipse, you can go to the File -> Switch Workspace -> 
Other... menu item. This will bring up a chooser like before and you can 
simply give it a new workspace location and Eclipse will create it for 
you. 

Whether you just created a new workspace or you're in an existing
workspace, you should ensure that the Android Preferences of Eclipse
have been configured with the SDK Location, as shown in Figure 2-4 from
the book.

To load sample projects into Eclipse, use the File -> Import... menu 
option. For the import source, choose General -> Existing Projects into 
Workspace. Click Next. If you did not unpack the zip file that you 
downloaded, choose "Select archive file", then click on the Browse... 
button next to the entry field. Navigate to the desired zip file, choose 
it and click Open. You should now see all projects from the zip file 
displayed in the Projects window, and all projects should have a 
checkmark next to their name. Using the buttons and/or checkmarks, 
select which projects you want to import, then click on the Finish 
button. This works well for the zip files that are specific to a book 
chapter, but if you've downloaded the single, complete zip file from the 
apress.com site, this method doesn't work as well. The list of projects 
will be all of the projects in the zip file, so for the one zip file 
from apress.com, you'll get all projects in the book. A better option in 
this case is to unzip the big zip file first. When you're doing the File 
-> Import... you will choose "Select root directory..." instead, and you 
will Browse to the directory for the chapter you want projects from. By 
opening a chapter directory, the list of projects will be just those 
projects from that chapter. Much more manageable. 

There is a chance you'll see errors for projects you import. The error 
message might say "Android requires compiler compliance level 5.0. 
Please fix project properties." To correct this, right click on the 
project in Eclipse, and choose the menu item Android Tools -> Fix 
Project Properties. To avoid these particular errors in the first place, 
you can go into Eclipse Preferences, under Java -> Compiler, and make 
sure that the Compiler compliance level is set to 1.5 (which means Java 
JDK 5 which corresponds to compliance level 5.0). This would of course 
mean that you're running with an older version of the Java JDK. But you 
could set this to 1.5 to load the projects, then change the compliance 
level to another value (for example 1.6) and Eclipse will recompile 
against that version. To use 1.6 you would need to have JDK 6 installed. 

If you have other errors, it could be due to changes to the Android
plugin for Eclipse. For each error, see if you can figure out what needs
to be done. You can always send an email to us and we'll help you out.

It's probably a good idea to clean the projects once loaded. This will
rebuild the binaries for you, ensuring the applications work the best in
your environment. To clean the projects, go to Eclipse's Project menu and
choose Clean.... Select Clean all projects and click OK. It may take
several seconds to clean all projects. Once the cleaning is complete,
assuming you have the Build Automatically option set on the Project menu,
Eclipse will regenerate all of the applications in the workspace. If you
don't have Build Automatically set, you will need to take care of the
build process on your own.

Once the projects are loaded and error-free, you can simply go ahead and
run them, either in the emulator, or on a real device. The only
exceptions to this are those applications that require map functionality.
For those, you will need to edit the appropriate layout file to put your
own Map API key in. The Map API key is explained in chapter 17.

Dave is at davemac327@gmail.com
Satya is at satya.komatineni@gmail.com
