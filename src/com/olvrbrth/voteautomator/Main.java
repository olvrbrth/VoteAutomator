package com.olvrbrth.voteautomator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final String DRIVER_PATH = "drivers/chromedriver.exe";
    private static final String DRIVER_PATH_MAC = "drivers/chromedriver";
    private static final String BASE_URL = "https://lt1-ooe-liga-frauen-ooe.fan.at/news/61e69fb2f775cc103236bd0c";
    private static final int MIN_SLEEP = 7;
    private static final int MAX_SLEEP = 15;

    public static void main(String[] args) {

        System.out.println("\n\n*** VoteAutomator ***\n\n");
        WebDriver driver = null;

        try {
            String driverPath = Util.getOS() == Util.OS.WINDOWS ? DRIVER_PATH : DRIVER_PATH_MAC;
            System.setProperty("webdriver.chrome.driver", driverPath);

            while (true) {
                driver = new ChromeDriver();
                driver.get(BASE_URL);

                Thread.sleep(3000);

                WebElement cookieButton = driver.findElement(By.xpath("//div[normalize-space()='Alles akzeptieren']"));
                cookieButton.click();

                Thread.sleep(3000);

                WebElement button = driver.findElement(By.xpath("//button[normalize-space()='Abstimmen']"));

                Actions actions = new Actions(driver);
                actions.moveToElement(button);
                actions.perform();

                Thread.sleep(1000);

                WebElement element = driver.findElement(By.xpath("//span[text()='Lisa Feilmayr']"));
                element.click();

                Thread.sleep(1000);

                button.click();

                Thread.sleep(3000);

                driver.close();
                driver.quit();

                System.out.println("Successfully voted");

                int randomSleep = ThreadLocalRandom.current().nextInt(MIN_SLEEP, MAX_SLEEP + 1);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, randomSleep);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                System.out.println("Next vote in " + randomSleep + " minutes at " + df.format(cal.getTime()));

                long sleepInMinutes = randomSleep * 60L * 1000L;
                Thread.sleep(sleepInMinutes);
            }
        } catch (Exception e) {
            if (driver != null) {
                driver.close();
                driver.quit();
            }

            e.printStackTrace();
            System.out.println("\n\nThere went something wrong, application has stopped...");

            System.console().readLine();
            System.exit(1);
        }
    }
}
