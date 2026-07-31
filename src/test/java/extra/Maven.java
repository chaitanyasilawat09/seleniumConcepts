package extra;

public class Maven {

    // TODO
    /* The Surefire Plugin in Maven is used to run unit tests and
     generate reports during the test phase of the build lifecycle.
     It is the standard test runner for Maven-based Java projects,
     and it's most commonly used with JUnit or TestNG.

     The Maven Surefire Plugin is used to run test cases automatically during the build process
     Runs Test Cases Automatically
            No need to run tests manually
            Executes all test classes during build

<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.0.0</version>
    </plugin>
  </plugins>
</build>



<configuration>
  <suiteXmlFiles>
    <suiteXmlFile>testng.xml</suiteXmlFile>
  </suiteXmlFiles>
</configuration>




<configuration>
  <parallel>methods</parallel>
  <threadCount>4</threadCount>
</configuration>

    */

//    TODO
//     <configuration>
//  <includes>
//    <include>**/*Test.java</include>
//  </includes>
//</configuration>


// TODO run specific file from cmd :
//  mvn -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml test
//  mvn -DsuiteXmlFile=src/test/resources/regression.xml -Dgroups="smoke,regression" -Dbrowser=edge -Denv=staging -Dtag="critical" test

//<properties>
//    <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
//    <browser>chrome</browser>
//    <env>qa</env>
//</properties>
//
//<build>
//    <plugins>
//        <plugin>
//            <groupId>org.apache.maven.plugins</groupId>
//            <artifactId>maven-surefire-plugin</artifactId>
//            <version>3.2.5</version>
//  <configuration>
//                <suiteXmlFiles>
//                      <suiteXmlFile>${suiteXmlFile}</suiteXmlFile>
//                </suiteXmlFiles>
//
//                <!-- group non-empty tab run hoga -->
//            <groups>${groups}</groups>
//
//                <!-- system properties test code mein use karne ke liye -->
//                <systemPropertyVariables>
//                  <browser>${browser}</browser>
//                  <env>${env}</env>
//                  <tag>${tag}</tag>
//                </systemPropertyVariables>
//            </configuration>
//        </plugin>
//    </plugins>
//</build>
}
