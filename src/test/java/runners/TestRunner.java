package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps"}, // Simplified glue to match your package structure
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public void executeJMeterCLI() {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Windows-safe paths using File.separator
            String projectPath = System.getProperty("user.dir");
            String jmxPath = projectPath + "\\src\\test\\resources\\jmeter\\LoadTest.jmx";
            String jtlPath = projectPath + "\\target\\jmeter_results_" + timestamp + ".jtl";
            String htmlReportDir = projectPath + "\\target\\HTML_Report_" + timestamp;

            // Construct Windows Command
            String command = String.format("jmeter -n -t \"%s\" -l \"%s\" -e -o \"%s\"", jmxPath, jtlPath, htmlReportDir);

            System.out.println("Executing JMeter on Windows: " + command);

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("JMeter execution successful. Report: " + htmlReportDir);
            } else {
                System.err.println("JMeter exited with error code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}