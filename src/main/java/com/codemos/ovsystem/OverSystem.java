package com.codemos.ovsystem;

import com.codemos.ovsystem.domain.dto.DataDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OverSystem {
	static ChromeDriver driver;

	static final String MAIN_URL = "https://oversystem.co.kr";
	
	public List<DataDto> get(String keyword) {
		WebDriverManager.chromedriver().setup();
		
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver = new ChromeDriver(options);
		
		driver.get(MAIN_URL + "/stuff/stuff_search_list" + "?keyword=" + keyword);
		waitForPageLoad();
		
		String html = (String)executeScript("return document.documentElement.outerHTML");
		
		Document document = Jsoup.parse(html);
		
		Elements mainElement = document.select("div.SearchStuffInfor.row");
		
		List<DataDto> itemList = new ArrayList<>();
		
		for (int i = 0; i < mainElement.size(); i++) {
			Element element = mainElement.get(i);
			
			String name = element.select("div.col > div > h4.StuffName > a").text();
			String url = element.select("div.col > div > h4.StuffName > a").attr("href");
			String imageUrl = element.select("div.col-3.col-md-1 > a > div.media-object").attr("style").replace("background-image: url('", "").replace("');", "");
			
			boolean isRecommended = element.select("div.col > div.LabelArea.pb-2 > span.badge.bg-info").text().contains("강추");
			boolean isSoldOut = element.select("div.col > div.LabelArea.pb-2 > span.badge.bg-danger").text().contains("품절");
			
			itemList.add(
				new DataDto(
					i,
					name,
					MAIN_URL + url,
					imageUrl,
					isRecommended,
					isSoldOut
				)
			);
		}
		
		// 웹 드라이버 종료
		driver.quit();
		
		return itemList;
	}
	
	private static Object executeScript(String script) {
		JavascriptExecutor jsExecutor = driver;
		return jsExecutor.executeScript(script);
	}
	
	private static void waitForPageLoad() {
		JavascriptExecutor jsExecutor = driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, TimeUnit.SECONDS.toChronoUnit()));
		wait.until(webDriver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
	}
}
