Welcome to the source code for Pro Android 3!!

You can also access these instructions by following links at

http://androidbook.com/proandroid4/projects
(If there are any changes to this document, those will be documented at this URL)

This README file will help you get setup with the source of every example in the book. 

*********************************************************
You can download Projects for each chapter separately
*********************************************************
You do not need to download and use every file from the www.androidbook.com website; you can download code just from the chapters you're interested in. Each chapter has its own zip file (in some cases more than one), and the filename matches the chapter. 

Note that there are no projects for chapters 11 (Debugging), 12 (configuration changes), and 31 (Android Market).

*********************************************************
Or You can download one large zip file from apress site
*********************************************************
Or, you may have downloaded the one big zipfile from the apress.com website. This single large zip file is a collection of individual (per chapter) zip files.

*********************************************************
Make sure you installed Eclipse and Android
*********************************************************
Before you import any of the projects from the zip files, your development environment is setup and ready to receive Android projects. Please refer to chapter 2 for information on installing Java, Eclipse, Android SDKs, and the Eclipse ADT.


*********************************************************
You may need the following Android SDKs
*********************************************************
The other thing you should do before loading our sample projects is make sure that you have the following Android platforms loaded. 

The platforms we have most used are: 
	Android 2.2 (8)
	Android 2.3.3 (10)
	Android 3.0 (11)
	Android 4.0 (14)

If you are in doubt you can just use the latest platform like API 14 or 15 and set that as your minimum and target platform in the android manifest file. 

Because these projects are precompiled you may want to readjust the JDK levels you may have in your eclipse environment and clean/recompile the projects.
	
*********************************************************
Get Google Add-ons for certain projects
*********************************************************
If you'll be working with projects that display maps, make you also need to go into the Third party Add-ons, under Google Inc. add-ons, and get the Google APIs for the respective platforms. This is true with the Contacts API chapter as well. You will need the Google extensions.

*********************************************************
Import individual projects
*********************************************************
You'll need to import the book's projects into your Eclipse workspace. Start with unzipping the individual project zip file. 

If you have downloaded the single zip file from Apress then the individual projects are available as zip files inside that large zip file. So unzip the large zip file first to get the individual zip files.

To load sample projects into Eclipse, use File -> Import... menu option. 

For the import source, choose General -> Existing Projects into Workspace. 

Click Next and choose the directory where the unzipped individual project is located. 

You may also want to choose the option of "copy projects into workspace". This option will leave your original directory of files intact and creates a copy of the files inside your eclipse work space.

*********************************************************
Exceptions
*********************************************************
There is a chance you'll see errors for projects you import. The error message might say "Android requires compiler compliance level 5.0. Please fix project properties." 

To correct this, right click on the project in Eclipse, and choose the menu item Android Tools -> Fix Project Properties. 

To avoid these particular errors in the first place, you can go into Eclipse Preferences, under Java -> Compiler, and make sure that the Compiler compliance level is set to 1.5 (which means Java JDK 5 which corresponds to compliance level 5.0). 

This would of course mean that you're running with an older version of the Java JDK. But you could set this to 1.5 to load the projects, then change the compliance level to another value (for example 1.6) and Eclipse will recompile against that version. To use 1.6 you would need to have JDK 6 installed. 

If you have other errors, it could be due to changes to the Android plugin for Eclipse. For each error, see if you can figure out what needs to be done. You can always send an email to us and we'll help you out. 

It's probably a good idea to clean the projects once loaded. This will rebuild the binaries for you, ensuring the applications work the best in your environment. To clean the projects, go to Eclipse's Project menu and choose Clean.... Select Clean all projects and click OK. 

It may take several seconds to clean all projects. Once the cleaning is complete, assuming you have the Build Automatically option set on the Project menu, Eclipse will regenerate all of the applications in the workspace. If you don't have Build Automatically set, you will need to take care of the build process on your own.

Once the projects are loaded and error-free, you can simply go ahead and run them, either in the emulator, or on a real device. The only exceptions to this are those applications that require map functionality. For those, you will need to edit the appropriate layout file to put your own Map API key in. The Map API key is explained in chapter 22. 

Dave is at davemac327@gmail.com
Satya is at satya.komatineni@gmail.com
