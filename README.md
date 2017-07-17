Cornipickle: a runtime monitor for layout constraints
=====================================================
The Cornipickle testing tool can automatically detect and report violations of 
Android user interface guidelines. Users write statements in a high level language,
called Cornichon, and Cornipickle can automatically check during the execution of 
the application that these statements are respected at all times.
The [client code](#) is a probe  that send all information about 
the current state of the window activity and relaying it to 
[the server](https://github.com/liflab/cornipickle) for further processing.

For more information about UI guidelines, please visit
[https://material.io/guidelines/](https://material.io/guidelines/).

Table of Contents                                                    {#toc}
-----------------

- [Compiling and installing the Cornipickle probe](#install)
    - [Server](https://github.com/liflab/cornipickle)
    - [Client (Probe)](#probe).
- [About the author](#about)

Compiling and Installing Cornipickle                             {#install}
------------------------------------

Server: 

- For install and run server ,you can find more information
  [here](https://github.com/liflab/cornipickle) 

Probe:                                                             {#probe}
   
Download or clone the source for Cornipickle  using Git:

    git@https://bitbucket.org/chafdev/cornipickle-sonde-mobile

### Building

First navigate to root sonde folder and run the following command:

     gradlew assembleDebug or gradlew assembleRelease (For mode release)

Run on the emulator:

1. Create an Android Virtual Device (AVD) 
2. Run `emulator -avd` 

### Built-in Examples

Cornipickle contains a few examples for testing ui guidelines. You can
try these examples by starting the server and launching the app but you must 
set the address of the server in `values.xml`.

- Click on key (B) for displaying Bug
- Click on key (N) for returning to normal


About the author                                                   {#about}
----------------
The Cornipickle Android Probe was written by Chafik Meniar,
then a Masters' Student at at Université du Québec À Chicoutimi, Canada.

Cornipickle was written by Sylvain Hallé, associate professor at Université
du Québec À Chicoutimi, Canada.