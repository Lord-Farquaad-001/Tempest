Tempest is a desktop app designed to track weather patterns. It includes a lightweight custom graphing class that is build using graphics alone by drawing points and lines based on arrays of data, a querry page with several parameters that generates a page displaying the querried data in tabular format, and a driver page featuring a robust set of input validation measures to enter weather data and ensure that this data is "clean".
Data is stored in .txt files. 

The Tempest app was developed in Java, using the Swing library for all GUI components. 

Future updates: 
  * automatic data procurement from a weather database rather than manual entry. Setup would ask for locations of interest, and data would be pulled automatically at a specified
    time each day via a scheduled OS task (runs the driver program at a certain time each day).
  * enhancements to the graphing class to allow for larger datasets (not plotting every point, but implementing more comprehensive scaling algorithms)


Desktop Icon: 

<img width="91" height="93" alt="Screenshot 2026-01-01 at 10 36 05 PM" src="https://github.com/user-attachments/assets/3efedd72-7ace-4389-af21-7a3b7a09eee2" />


Launch Page: 

<img width="899" height="483" alt="Screenshot 2025-12-11 at 2 16 19 PM" src="https://github.com/user-attachments/assets/4c0aeaf0-cd87-4e77-a4ac-98adfe50248c" />


Entry Page (allows user to enter daily weather data): 
* Note that pressing the "?" button in the lower left corner will guide the user through the proper procedure for data entry

<img width="943" height="414" alt="Screenshot 2025-12-11 at 4 11 05 PM" src="https://github.com/user-attachments/assets/2402c4dd-19de-4b89-a2c7-f051562656b2" />


Example of Handling of Invalid Input: 

<img width="893" height="409" alt="Screenshot 2026-01-01 at 10 38 58 PM" src="https://github.com/user-attachments/assets/751f2eba-cddd-45b3-843b-e7f45b53ab1b" />


Analytics Page (allows user to define parameters for analysis): 

<img width="544" height="592" alt="Screenshot 2025-12-11 at 4 06 42 PM" src="https://github.com/user-attachments/assets/3b3452ba-ff4c-4d61-9a1b-ccdc73dd2ec1" />


Tabular Output from Analytics Page:

<img width="1030" height="300" alt="Screenshot 2025-12-11 at 4 06 29 PM" src="https://github.com/user-attachments/assets/822bc3b8-fccd-4e0c-a792-f2884efb489b" />


Graph Page (custom graphing class): 

<img width="1102" height="457" alt="Screenshot 2025-12-11 at 2 14 54 PM" src="https://github.com/user-attachments/assets/e1983188-d4d6-4239-8d26-2cf607849e70" />
