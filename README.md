Tempest is a desktop app designed to track weather patterns. It includes a lightweight custom graphing class, a querry page with several parameters that generates a page displaying the querried data in tabular format, and a driver page featuring a robust set of input validation measures to enter weather data and ensure that this data is "clean".
Data is stored in .txt files. 

The Tempest app was developed in Java, using the Swing library for all GUI components. 

Future updates: 
  * automatic data procurement from a weather database rather than manual entry. Setup would ask for locations of interest, and data would be pulled automatically at a specified
    time each day via a scheduled OS task (runs the driver program at a certain time each day)
  * enhancements to the graphing class to allow for larger datasets (not plotting every point, but implementing more comprehensive scaling algorithms)


Launch Page: 

<img width="899" height="483" alt="Screenshot 2025-12-11 at 2 16 19 PM" src="https://github.com/user-attachments/assets/4c0aeaf0-cd87-4e77-a4ac-98adfe50248c" />


Entry Page (allows user to enter daily weather data): 

<img width="943" height="414" alt="Screenshot 2025-12-11 at 4 11 05 PM" src="https://github.com/user-attachments/assets/2402c4dd-19de-4b89-a2c7-f051562656b2" />


Analytics Page (allows user to define parameters for analysis): 

<img width="544" height="592" alt="Screenshot 2025-12-11 at 4 06 42 PM" src="https://github.com/user-attachments/assets/3b3452ba-ff4c-4d61-9a1b-ccdc73dd2ec1" />


Table Output from Analytics Page:

<img width="1030" height="300" alt="Screenshot 2025-12-11 at 4 06 29 PM" src="https://github.com/user-attachments/assets/822bc3b8-fccd-4e0c-a792-f2884efb489b" />


Graph Page (custom graphing class): 

<img width="1102" height="457" alt="Screenshot 2025-12-11 at 2 14 54 PM" src="https://github.com/user-attachments/assets/e1983188-d4d6-4239-8d26-2cf607849e70" />
