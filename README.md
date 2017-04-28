Cornipickle: a runtime monitor for layout constraints
=====================================================
The Cornipickle testing tool can automatically detect and report violations of 
Android user interface guidelines. Users write statements in a high level language,
called Cornichon, and Cornipickle can automatically check during the execution of 
the application that these statements are respected at all times.
Client code is a probe that send all information about 
the current state of the window activity and relaying it to the server
for further processing.

For more informations about UI guidelines, please visit [https://material.io/guidelines/](https://material.io/guidelines/) 

Table of Contents                                                    {#toc}
-----------------

- [Compiling and installing Cornipickle](#install)
    - [Server](https://github.com/liflab/cornipickle)
    - [Client (Sonde)](#sonde).
- [About the author](#about)

Compiling and Installing Cornipickle                             {#install}
------------------------------------
-Server : 

   For install and run server ,you can find more informations
   [here](https://github.com/liflab/cornipickle) 

-Sonde                                                             {#sonde}
   
   Download or clone the source for Cornipickle  using Git:

    git@https://bitbucket.org/chafdev/cornipickle-sonde-mobile

### Building


First navigate to root sonde folder and run the following command:

     gradlew assembleDebug or gradlew assembleRelease (For mode release)

Run on the emulator :

     1. create an Android Virtual Device (AVD) 
     2. emulator -avd 

### Built-in Examples

Cornipickle contains a few examples. You can
try these examples by starting the server and launching the app but you must 
set the address of the server in  [values.xml](https://bitbucket.org/chafdev/cornipickle-sonde-mobile/src/fff2094c47e07d12bce9069bf10eeabb7fe7f37e/Sonde/app/src/main/res/values/strings.xml?at=master&fileviewer=file-view-default).

 Click on key (B) for displaying Bug
 
 Click on key (N) for returning to normal




About the author                                                   {#about}
----------------
Cornipickle was written by Sylvain Hallé, associate professor at Université
du Québec À Chicoutimi, Canada.