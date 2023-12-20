package Bot;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ZbieractwoAuto {

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

			try {
				WebElement swiat195 = driver.findElement(By.xpath("//*[@id=\"home\"]/div[3]/div[4]/div[10]/div[3]/div[2]/div[1]/a[1]/span"));
				swiat195.click();
			} catch (NoSuchElementException e) {
				WebElement przeglad1 = driver.findElement(By.xpath("//*[@id=\"menu_row\"]/td[2]/a"));
				przeglad1.click();	
			}			

			// pezejscie do placu z przegladu
			WebElement placPrzeglad = driver.findElement(By.xpath("//*[@id=\"l_place\"]/td/a"));
			placPrzeglad.click();
			// przejscie do zbieractwa
			WebElement zbieractwo = driver.findElement(By.xpath("//*[@id=\"content_value\"]/table[2]/tbody/tr/td[3]/a"));
			zbieractwo.click();

			try {
				getSendCount(5,8);
				// przewiniecie do przycisku
				WebElement btn1Zbieractwo = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[2]/div[1]/div[3]/div/div[2]/a[1]"));
				WebElement btn2Zbieractwo = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[2]/div[2]/div[3]/div/div[2]/a[1]"));
				WebElement btn3Zbieractwo = driver.findElement(By.xpath("//*[@id=\"scavenge_screen\"]/div/div[2]/div[3]/div[3]/div/div[2]/a[1]"));
				
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn1Zbieractwo);
				btn1Zbieractwo.click();
	
				Thread.sleep(1000);
				getSendCount(2,3);				
				btn2Zbieractwo.click();
	
				Thread.sleep(1000);
				getSendCount(1,1);				
				btn3Zbieractwo.click();
				
				Thread.sleep(5000);
				
			} catch (NoSuchElementException e) {
				System.out.println("Brak ktregos z przyciskow");
				Thread.sleep(5000);				
			}			
					
			Thread.sleep(60000); //czekanie 30s
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
