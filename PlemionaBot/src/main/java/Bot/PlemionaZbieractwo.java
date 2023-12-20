package Bot;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PlemionaZbieractwo {

	public static String browser = "Chrome"; // External configuration
	public static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {

		if (browser.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		String login = "login";
		String haslo = "haslo";

//		driver.get("https://saucedemo.com/");
//		driver.findElement(By.id("user-name")).sendKeys("standard_user");
//		driver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
//		driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
//		driver.findElement(By.tagName("input")).sendKeys("standard_user");
//		driver.get("https://www.selenium.dev/documentation/webdriver/getting_started/");
//		driver.findElement(By.linkText("Install Library")).click();
//		driver.findElement(By.partialLinkText("Install")).click();
//		WebElement password = driver.findElement(By.id("password"));
//		driver.findElement(RelativeLocator.with(By.tagName("input")).above(password)).sendKeys("standa");	

		driver.get("https://www.plemiona.pl/");
		driver.manage().window().maximize();

		// logowanie
		driver.findElement(By.xpath("//*[@id=\"user\"]")).sendKeys(login);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(haslo);
		driver.findElement(By.xpath("//*[@id=\"login_form\"]/div/div/a")).click();
//		WebElement swiat195 = null;

		// czekanie na pojawienie sie widoku przegladu
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"l_main\"]/td/a")));

		while (true) {

			// przjescie do przegladu
			WebElement przeglad = driver.findElement(By.xpath("//*[@id=\"menu_row\"]/td[2]/a"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", przeglad);
			przeglad.click();	
//			try {
//				swiat195 = driver.findElement(By.xpath("\"//*[@id=\\\"home\\\"]/div[3]/div[4]/div[10]/div[3]/div[2]/div[1]/a[1]/span\""));
//			}
//			catch(Exception e){
//				System.out.println(e);				
//			}
//			if(swiat195.isDisplayed()) {
//				swiat195.click();
//				przeglad.click();	
//			}
		
			

			// pezejscie do placu z przegladu
			WebElement placPrzeglad = driver.findElement(By.xpath("//*[@id=\"l_place\"]/td/a"));
			placPrzeglad.click();
			// przejscie do zbieractwa
			WebElement zbieractwo = driver.findElement(By.xpath("//*[@id=\"content_value\"]/table[2]/tbody/tr/td[3]/a"));
			zbieractwo.click();

			getSendCount(5,8);

			// przewiniecie do przycisku
			WebElement btn1Zbieractwo = driver.findElement(By.cssSelector("#scavenge_screen > div > div.options-container > div:nth-child(1) > div.status-specific > div > div.action-container > a.btn.btn-default.free_send_button"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn1Zbieractwo);
			btn1Zbieractwo.click();

			Thread.sleep(1000);

			// 2

			getSendCount(2,3);

			WebElement btn2Zbieractwo = driver.findElement(By.cssSelector("#scavenge_screen > div > div.options-container > div:nth-child(2) > div.status-specific > div > div.action-container > a.btn.btn-default.free_send_button"));
			btn2Zbieractwo.click();

			Thread.sleep(1000);

			// 3

			getSendCount(1,1);

			WebElement btn3Zbieractwo = driver.findElement(By.cssSelector("#scavenge_screen > div > div.options-container > div:nth-child(3) > div.status-specific > div > div.action-container > a.btn.btn-default.free_send_button"));
			btn3Zbieractwo.click();

			// czekanie 2 godz. az pojawi sie przycisk start w zbieractwie
			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(7200));
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#scavenge_screen > div > div.options-container > div:nth-child(3) > div.status-specific > div > div.action-container > a.btn.btn-default.free_send_button")));

			Thread.sleep(60000); //czekanie minute
		}

	}

	// metoda do uzupelniania ilosci jednostek w zaleznosci od wspolczynnika
	public static void getSendCount(int mult, int div) {

		WebElement pikiIlosc = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[1]/table/tbody/tr[2]/td[1]/a"));
		WebElement mieczIlosc = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[1]/table/tbody/tr[2]/td[2]/a"));
		String pikiCountStr = pikiIlosc.getAttribute("innerText");
		String mieczCountStr = mieczIlosc.getAttribute("innerText");

		int pikiCount = Integer.parseInt(pikiCountStr.substring(1, pikiCountStr.length() - 1));
		int mieczCount = Integer.parseInt(mieczCountStr.substring(1, mieczCountStr.length() - 1));

		String pikiCountToSend = String.valueOf(pikiCount * mult / div);
		String mieczCountToSend = String.valueOf(mieczCount * mult /div);

		WebElement pikiPole = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[1]/table/tbody/tr[2]/td[1]/input"));
		WebElement mieczPole = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[1]/table/tbody/tr[2]/td[2]/input"));
		pikiPole.sendKeys(pikiCountToSend);
		mieczPole.sendKeys(mieczCountToSend);

	}

}
