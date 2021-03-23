Pro-tip: put testing code in testsAndExamples/TestController::main
    view in localhost:8080/main

/newPhoto
/allPhotos

Might be able to use without the database by commenting out the "@Service" in 
RealDatabaseInterface, and uncommenting "@Service" in BadExampleDatabase

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

# Configuring the Database
Before running the project, you'll need to set up a database on your computer.

## Using mySQL as a Database
1. First, create the database in the mySQL console using `create database [DB_NAME];`
   where [DB_NAME] is the name you want the database to have.
2. Create the Spring user with `create user '[NAME]'@'%' identified by '[PASSWORD]';`
   where [NAME] is the name you want for Spring's user, 
   and [PASSWORD] is a password you want for them
3. Give Spring permissions using `grant all on [DB_NAME].* to '[NAME]'@'%';`
4. Create a file, `application.properties` under `src/main/resources`
5. Copy and paste the template I've created under `templatesForUs`
6. For now, I'm not sure if we should include the properties file in the Git repo,
   so I've added it to .gitignore. Just be sure you don't accidentally include it
   in your commits. NO COMMIT DATABASE CREDENTIALS! AAAAHHHHHHHHHHHHHHHHHH!


# Getting Ready for Distribution
In mySQL, do the following...
```
revoke all on [DB_NAME].* from '[NAME]'@'%';
grant select, insert, delete, update on [DB_NAME].* to '[NAME]'@'%';
```
Commit the application.properties file, with 
`spring.jpa.hibernate.ddl-auto=update`