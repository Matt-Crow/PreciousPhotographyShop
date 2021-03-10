HOW TO RUN:
1. Run from your IDE, or via the command line with "gradle bootRun"
chances are good, you will experience a problem...
https://stackoverflow.com/questions/11345193/gradle-does-not-find-tools-jar
2. In your web browser, go to localhost:8080
3. When you are done testing, or need to restart to apply changes, cancel the program:
    * through your IDE (probably a big red button)
    * through bootRun by pressing CTRL-C a couple times, then enter Y
    
if run from the JAR file, Spring will run in the background.
you will need to kill the java subprocess of the terminal to shut Spring down
(or maybe just close the terminal)
