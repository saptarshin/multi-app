## Sample Suspect Case Detection Services
The concerned project is a brief demonstration on how to detect suspect transactions and report them.


##### Use Case
Here, we are using example of stock trading transactions. 
While processing the records, if any particular trader is identified as making a more-than-specified number of purchases/sales on an individual stock, that particular trader is flagged and details about the concerned trades are sent to regulators.


##### The project has been built using
- _Java_: 11
- _Spring boot_: 2.7.10
- _Maven_
- _Active MQ_ 


##### Input
The application receives input as batch files in a folder. Each batch file contains a finite number of trade-transaction records.



##### Breakdown
This is a combination of 3 separate appications - 
- _fraud-detection_
- _regulatory-clients_
- _jms-service_



##### How to build and run
All the 3 applications are normal Java Spring boot projects using maven as build tool. 
1. Clone/Download the repository. For all below instructions, substitute `<repo-root>` as the folder where you have cloned the repository.
2. Change the value of property `read.folder` to `<repo-root>/input/` in `<repo-root>/fraud-detection/src/main/resources/application.properties` file.
3. Build the 3 services
```sh
cd <repo-root>/fraud-detection
mvnw clean install
cd <repo-root>/regulatory-clients
mvnw clean install
cd <repo-root>/jms-service
mvnw clean install
```
4. Start ActiveMQ server. Login as admin and create 2 queues - `first_queue` and `second_queue`.
5. Open 3 command prompts and run the below snippets separately to run the packages
```sh
cd <repo-root>/fraud-detection/target
java -jar fraud-detection-1.0.jar
```
```sh
cd <repo-root>/regulatory-clients/target
java -jar regulatory-clients-1.0.jar
```
```sh
cd <repo-root>/jms-service/target
java -jar jms-service-1.0.jar
```
6. Put the input batch file(s) in `<repo-root>/input/` folder.

