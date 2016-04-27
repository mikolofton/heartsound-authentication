# Heart Sound Biometrics for Continual User Authentication
Authors: Brian Haggarty, Melissa Lofton, Javin Na Javeed, Alexa Piccoli, and Leigh Anne Clevenger

Pace University / IT 691 / Dr. Charles D. Tappert

##Synopsis
An experiment designed to emulate and test user authentication using heart sound recordings taken from a stethoscope as a biometric.

##Abstract
Biometrics is a developing form of user authentication that is increasing in utilization. Biometrics is a great tool for authentication because it is difficult to steal and cannot be lost or forgotten. This study explores the possibility of using heart sound for continual user authentication, as opposed to the more common methods, like fingerprint or iris authentication. Heart sounds are an appealing method for authentication because they are unique to each person and easily collected.  This study will examine the different parts of an individual’s heartbeat to determine whether or not this is a unique qualifier for correctly authenticating users. Data will be collected from the HSCT-11 database, and the authentication system developed in this study will collect recordings of the heart sounds of unique individuals. This study will then use Audacity’s FFT feature to convert the sound wave into a digital output that will be further processed to generate a unique key (UKI) for database and authentication. To generate the key, the study developed an HS_KeyGen class in Java that calculates the mean, root mean square, and standard deviation of the FFT frequency data and concatenates these values. Once the key generation process is complete, the system will test its ability to classify authentication key to the corresponding database key.  An open source machine learning software known as WEKA will be used to test the accuracy of the results using four different classification algorithms. This study determined that IBk classifier produces the best results out of the tested algorithms, but did not achieve a high enough accuracy to perform successfully as user authentication system. Additional features and functionality is needed to improve the exclusivity of this study’s heart sound authentication system.


##User Guide

####Dependencies

Please make sure you download and install the following:

1. [Java 1.7 or higher](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. [WEKA](http://www.cs.waikato.ac.nz/ml/weka/downloading.html)
3. [Audacity](http://www.audacityteam.org/download/) 

####Obtaining Heart Sound Files

1. Download heart sound files from the following link: [HSCT-11](http://www.diit.unict.it/hsct11/)
2. Extract the zip file and save the heart sound .wav files to your computer.

####Folder Setup Before Using Audacity

1. Create an experiment folder for the project on your computer.
2. Within the experiment folder, create two additional folders labeled auth and database.
3. Download class files within repository that is able to convert the authentication and database files within the root of the experiment folder into a numerical key that is unique for each sample. These database and authentication files will be generated using Audacity in the steps listed below. 

####Creating Samples

**Database Samples**

1. Open Audacity.
2. Go to File > Import > Audio and select the .wav file from the heart sound files downloaded earlier in step 1.
3. Once the file has been opened, look towards the bottom of the screen to find two fields labeled selection start and selection end/length. Make sure end is selected from the selection end/length field and make sure the time format of hh:mm:ss is selected for both fields.
4. Set the selection start time to 00h 00m 06s.
5. Set the selection end to 10h 00m 00s. 
6. Edit > Delete
7. Edit > Select > Select All
8. Analyze > Plot Spectrum
9. Make sure size is set to 128
10. Click on export and save the file to the database folder created earlier with the ID number as the name (example: 001.txt)
11. Click on the “x” button to close out of the plot spectrum window. Do not select the close option!

**Auth Samples**

1. Edit > Undo to reset the .wav file
2. Delete previous sample by setting the selection start field to 00h 00m 00s and setting the selection end field to 00h 00m 06s and then choosing Edit > Delete.
3. Create new sample by setting selection start to 00h 00m 06s and selection end to 10h 00m 00s.
4. Edit > Delete
5. Edit > Select > Select All
6. Analyze > Plot Spectrum
7. Make sure size is set to 128. 
8. Click on export and save the file to the auth folder created earlier with the ID number as the name (example: 001.txt).
9. Click on the “x” button to close out of the plot spectrum window. **Do not select the close option**.
10. Go to File > Close. There is no need to save the wav file.
11. Go to File > New.
12. Repeat steps 1-8 of section IV and steps 1-11 of section V with each .wav file until all sample files are created.

####Key Generation

1. Open cmd if using Windows or terminal if using Mac/Linux.
2. Type `cd `.
3. In Windows Explorer or Finder navigate to your experiment folder and drag the experiment folder into the terminal/cmd window. This should autocomplete the path for you. Hit enter. Your working directory should now be the experiment folder where the HS_KeyGen.class file is located. 
4. In your cmd/terminal window type `java HS_KeyGen auth`. You should see the 'Successfully created' output and an auth.txt file should now be in your experiments folder. This file should have ids and keys for every one of your sample files. You should only have to run this once! 
5. In your cmd/terminal window type `java HS_KeyGen database`. You should see the 'Successfully created' output and an database.txt file should now be in your experiments folder. This file should have ids and keys for every one of your sample files. You should only have to run this once! 

####Using WEKA

1. Manually convert the auth.txt file and the database.txt file into auth.arff and database.arff
2. After downloading WEKA 3.6, open it with console. 
3. Use WEKA in explorer mode.
4. Under the Preprocess tab, choose Open file. 
5. Open the file named hsauth_auth.arff under the Preprocess tab.
6. Navigate to the Classify tab.
7. Under the Classify tab, choose the radio button named Supplied test set under Test options. 
8. Once you have chosen the Supplied test set radio button, the Set button to the right of it will be available. 
9. Click the set button and then click open file. 
10. Choose the hsauth_database.arff file, then hit close.
11. There will be an attribute drop with (Num) key highlighted. 
12. Click the drop down menu and select (Nom) uid.
13. You are now ready to begin classifying data in this environment.
14. To choose different classifiers to run the data through, select the choose button (currently set at ZeroR).
15. The classifiers used for this study were the IBk, J48 KStar and NaiveBayes classifiers. 
16. For IBk, click Choose -> expand lazy -> click IBk
17. For J48, click Choose -> expand trees-> click J48
18. For KStar, click Choose -> expand lazy -> click KStar
19. For NaiveBayes, click Choose -> expand bayes -> click NaiveBayes
20. Once you have the appropriate classifier chosen, click Start.
21. Your data will be classified and the results will be shown. 
