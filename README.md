# Customer Statement Processing Application

This java app is capable of process CSV file in batch processing, validate fields based on business criteria and able to create reconciliation reports for failed rows. 


# Version

Java : 1.8  
Spring-boot: 2.x  
Spring-batch: 4.x  

Please refer to pom.xml for all dependencies 

# Input

Input file is already placed into input folder, format is like :

Reference,AccountNumber,Description,Start Balance,Mutation,End Balance

194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23

112806,NL27SNSB0917829871,Clothes for Willem Dekker,91.23,+15.57,106.8

183049,NL69ABNA0433647324,Clothes for Jan King,86.66,+44.5,131.16

183356,NL74ABNA0248990274,Subscription for Peter de Vries,92.98,-46.65,46.33

112806,NL69ABNA0433647324,Clothes for Richard de Vries,90.83,-10.91,79.92

112806,NL93ABNA0585619023,Tickets from Richard Bakker,102.12,+45.87,147.99

139524,NL43AEGO0773393871,Flowers from Jan Bakker,99.44,+41.23,140.67

179430,NL93ABNA0585619023,Clothes for Vincent Bakker,23.96,-27.43,-3.47

141223,NL93ABNA0585619023,Clothes from Erik Bakker,94.25,+41.6,100.00

195446,NL74ABNA0248990274,Flowers for Willem Dekker,26.32,+48.98,75.3

# Run
Multiple ways you may run this app :

using command line : mvn spring-boot:run

or using main class : BatchProcessStartup

# Output
Reconciliation report as form of csv is generated after processing and validating of given input csv.

112806,Clothes for Richard de Vries

112806,Tickets from Richard Bakker

# Execute scanner for Maven
mvn sonar:sonar

Dashboard: https://sonarcloud.io/dashboard?id=Kingthebestin_Customer-Statement-processing