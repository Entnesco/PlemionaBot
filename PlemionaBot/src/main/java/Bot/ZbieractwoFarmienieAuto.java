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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ZbieractwoFarmienieAuto {

	public static String browser = "Chrome"; // External configuration
	public static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {

		List<String> cords = readFile(); //odczyt pliku z cordami
		int cordsSize = cords.size();
		int cordite = 0;
		
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
			
			//
			//farmienie automatyczne
			//
			
			// przjescie do przegladu
			przeglad = driver.findElement(By.xpath("//*[@id=\"menu_row\"]/td[2]/a"));
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
			placPrzeglad = driver.findElement(By.xpath("//*[@id=\"l_place\"]/td/a"));
			placPrzeglad.click();
			
			try {
				WebElement poleKordy = null;
				WebElement poleLK = null;
				WebElement btnNapad = null;		
				WebElement btnWysliNapad = null;
				WebElement celNazwa = null;
				String strCelNazwa;
				
				//szczytanie ilosci LK
				WebElement LKIlosc = driver.findElement(By.xpath("//*[@id=\"units_entry_all_light\"]"));			
				String LKCountStr = LKIlosc.getAttribute("innerText");
				int LKCount = Integer.parseInt(LKCountStr.substring(1, LKCountStr.length() - 1));
				
				while(LKCount > 1) {
					
					//aktualizacja WebElementow
					poleLK = driver.findElement(By.xpath("//*[@id=\"unit_input_light\"]"));
					btnNapad = driver.findElement(By.xpath("//*[@id=\"target_attack\"]"));
					poleKordy = driver.findElement(By.xpath("//*[@id=\"place_target\"]/input"));
					
					//wybranie 2 LK
					poleLK.sendKeys("2");
					
					//wprowadzanie kordow
					poleKordy.sendKeys(cords.get(cordite));
					
					//iteracja listy kordow oraz sprawdzenie czy lista sie nie skonczyla
					cordite++;
					if(cordite > cordsSize - 1) cordite = 0;			
					
					//Klikniecie przycisku Napad
					btnNapad.click();
					Thread.sleep(1000);	
					
					// sprawdzanie czy ktos nie przejal farmionej wioski
					celNazwa = driver.findElement(By.xpath("//*[@id=\"command-data-form\"]/div[1]/table/tbody/tr[2]/td[2]/span/a[1]"));
					strCelNazwa = celNazwa.getAttribute("innerText");	
					System.out.println(strCelNazwa);
					if(!(strCelNazwa.contains("Wioska") || strCelNazwa.contains("Osada"))) {
						System.out.println("To juz nie wioska barbarzynska: " + cords.get(cordite-1));
						cords.remove(cordite-1);
						cordite--;						
					}
					
					//Wysylanie napadu
					btnWysliNapad = driver.findElement(By.xpath("//*[@id=\"troop_confirm_submit\"]"));
					btnWysliNapad.click();					
					Thread.sleep(1000);
					
					//aktualizacja ilosci LK
					LKIlosc = driver.findElement(By.xpath("//*[@id=\"units_entry_all_light\"]"));
					LKCountStr = LKIlosc.getAttribute("innerText");
					LKCount = Integer.parseInt(LKCountStr.substring(1, LKCountStr.length() - 1));
				}
				
				System.out.println("Za malo LK");
			
			} 
			catch (NoSuchElementException e) {
				System.out.println("Brak ktoregos z elementow w sekcji automatycznego farmienia");
			}
	
			System.out.println("Czekanie 100s");	
			Thread.sleep(100000); //czekanie 100s
			System.out.println("Lecimy dalej!");
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
	
	public static List<String> readFile() {
		//odczyt pliku
        String sciezkaDoPliku = ".\\src\\main\\resources\\kordynaty.txt";
        List<String> cords = new ArrayList<>();	
        
        try {
            FileReader fileReader = new FileReader(new File(sciezkaDoPliku));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linia;
            // Odczyt linia po linii z pliku
            while ((linia = bufferedReader.readLine()) != null) {
                String[] splittedCords = linia.split(" ");
                cords.addAll(Arrays.asList(splittedCords));
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return cords;
	}

}
