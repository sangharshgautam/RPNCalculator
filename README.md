# RPNCalculator
Reverse Polish Notation Calculator


**Build the Application:**

Run the Maven clean install command on the root directory (RPNCalculator) of the project

There are two jar files in the target/ directory  
	•	rpn-calculator-1.0.0.jar
	•	rpn-calculator-1.0.0-jar-with-dependencies.jar
The jar-with-dependencies file is the self-contained executable jar that can be used to run the application.

**Run the Application:**

The input file is located at src/test/resources/test_input.txt.

The standalone application can be run by running the below command, by providing the path to the input test file.

java -jar target/rpn-calculator-1.0.0-jar-with-dependencies.jar src/test/resources/test_input.txt
