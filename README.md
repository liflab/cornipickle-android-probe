Cornipickle: a runtime monitor for layout constraints
=====================================================

[![Travis](https://img.shields.io/travis/liflab/cornipickle.svg?style=flat-square)]()
[![SonarQube Coverage](https://img.shields.io/sonar/http/sonarqube.com/liflab:cornipickle/coverage.svg?style=flat-square)]()

Cornichon is a declarative language that can express desirable properties of
a web application  and mobile app as a set of human-readable assertions .
Cornipickle, an automated testing tool that can verify
Cornichon properties on-the-fly as a user interacts with an application.

Table of Contents                                                    {#toc}
-----------------

- [Compiling and installing Cornipickle](#install)
    - [Server](https://github.com/liflab/cornipickle)
    - [Client (Sonde)](#sonde).
- [Command-line usage](#cli)
- [About the author](#about)

Compiling and Installing Cornipickle                             {#install}
------------------------------------
-Server : 
   For install and run server ,you can more information
   [here](https://github.com/liflab/cornipickle) 

-Sonde 
   
   Download the sources for Cornipickle from or clone the
   repository using Git:

    git@https://bitbucket.org/chafdev/cornipickle-sonde-mobile

### Installing dependencies

Building 

First Navigate to Root Folder Sonde and run the following command:

     gradlew assembleDebug or gradlew assembleRelease (For mode release)

Run on the emulator :

     1. create an Android Virtual Device (AVD) 
     2. emulator -avd 

### Built-in Examples

Cornipickle contains a few examples.  you can
try these examples by starting the server and launching the app but you must 
setup the adress of the server in  [values.xml](https://bitbucket.org/chafdev/cornipickle-sonde-mobile/src/fff2094c47e07d12bce9069bf10eeabb7fe7f37e/Sonde/app/src/main/res/values/strings.xml?at=master&fileviewer=file-view-default).
For example :    <string name="sonde_server">http://192.168.109.1:10101</string>
  
For displaying Bug ,click on key (B)
For returning to normal, click on key (N).




About the author                                                   {#about}
----------------

Cornipickle was written by Sylvain Hall�, associate professor at Universit�
du Qu�bec � Chicoutimi, Canada.
