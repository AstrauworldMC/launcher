package fr.timeto.astrauworld.launcher;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import fr.theshark34.swinger.Swinger;

public class NewsArticles {
	static BufferedImage getResourceCustom(String path) {
		return Swinger.getResource("/news/" + path + ".png");
	}
	
	public static Article presentationLauncher = new Article("Présentation du launcher", "presentationLauncher", 1);
	public static Article staffV1 = new Article("Le staff", "staffV1", 1);
	public static Article defaultArticle = new Article("Défault", "default", 1);
	
	public static List<Article> articlesList = new ArrayList<Article>();
	public static int nombreArticles = 2;
	
	public static void setArticlesList() {
		articlesList.add(0, presentationLauncher);
		articlesList.add(1, staffV1);
		articlesList.add(2, defaultArticle);
		articlesList.add(3, defaultArticle);
	}
	
	public static String getNameOfArticle(Article article) {
		return article.name;
	}
    
	public static BufferedImage getArticleHeader(Article article) {
		return article.headerImage;
	}
	
	public static BufferedImage getArticleHover(Article article) {
		return article.headerHoverImage;
	}
	
	public static int getNumberOfPage(Article article) {
    	return article.numberOfPage;
	}
    
	public static BufferedImage getPageArticle(Article article, int number) {
    	BufferedImage page = article.noPages; 
    	
    	if(number == 1) {
    		page = article.pageOne;
    	}else if(number == 2) {
    		page = article.pageTwo;
    	}else if(number == 3) {
    		page = article.pageThree;
    	}else if(number == 4) {
    		page = article.pageFour;
    	}else if(number == 5) {
    		page = article.pageFive;
    	}else if(number == 6) {
    		page = article.pageSix;
    	}else if(number == 7) {
    		page = article.pageSeven;
    	}else if(number == 8) {
    		page = article.pageEight;
    	}else if(number == 9) {
    		page = article.pageNine;
    	}
		return page;
    	
	}

}

class Article {
	String name; 
	
	BufferedImage headerImage;
	BufferedImage headerHoverImage;
	
	int numberOfPage;
	
	BufferedImage noPages;
	BufferedImage pageOne;
	BufferedImage pageTwo;
	BufferedImage pageThree;
	BufferedImage pageFour;
	BufferedImage pageFive;
	BufferedImage pageSix;
	BufferedImage pageSeven;
	BufferedImage pageEight;
	BufferedImage pageNine;
    
    Article(String name, String filesName, int numberOfPage) {
		this.name = name;
		headerImage = NewsArticles.getResourceCustom(filesName + "-Header");
		headerHoverImage = NewsArticles.getResourceCustom(filesName + "-Hover");
		this.numberOfPage = numberOfPage;
		noPages = NewsArticles.getResourceCustom("default-Page1");
		if(numberOfPage == 1) {
		    pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		} else if(numberOfPage == 2) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		} else if(numberOfPage == 3) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		} else if(numberOfPage == 4) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		    pageFour = NewsArticles.getResourceCustom(filesName + "-Page4");
		} else if(numberOfPage == 5) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		    pageFour = NewsArticles.getResourceCustom(filesName + "-Page4");
		    pageFive = NewsArticles.getResourceCustom(filesName + "-Page5");
		} else if(numberOfPage == 6) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		    pageFour = NewsArticles.getResourceCustom(filesName + "-Page4");
		    pageFive = NewsArticles.getResourceCustom(filesName + "-Page5");
		    pageSix = NewsArticles.getResourceCustom(filesName + "-Page6");
		} else if(numberOfPage == 7) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		    pageFour = NewsArticles.getResourceCustom(filesName + "-Page4");
		    pageFive = NewsArticles.getResourceCustom(filesName + "-Page5");
		    pageSix = NewsArticles.getResourceCustom(filesName + "-Page6");
		    pageSeven = NewsArticles.getResourceCustom(filesName + "-Page7");
		} else if(numberOfPage == 8) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		    pageFour = NewsArticles.getResourceCustom(filesName + "-Page4");
		    pageFive = NewsArticles.getResourceCustom(filesName + "-Page5");
		    pageSix = NewsArticles.getResourceCustom(filesName + "-Page6");
		    pageSeven = NewsArticles.getResourceCustom(filesName + "-Page7");
		    pageEight = NewsArticles.getResourceCustom(filesName + "-Page8");
		} else if(numberOfPage == 9) {
			pageOne = NewsArticles.getResourceCustom(filesName + "-Page1");
		    pageTwo = NewsArticles.getResourceCustom(filesName + "-Page2");
		    pageThree = NewsArticles.getResourceCustom(filesName + "-Page3");
		    pageFour = NewsArticles.getResourceCustom(filesName + "-Page4");
		    pageFive = NewsArticles.getResourceCustom(filesName + "-Page5");
		    pageSix = NewsArticles.getResourceCustom(filesName + "-Page6");
		    pageSeven = NewsArticles.getResourceCustom(filesName + "-Page7");
		    pageEight = NewsArticles.getResourceCustom(filesName + "-Page8");
		    pageNine = NewsArticles.getResourceCustom(filesName + "-Page9");
		}
	}
	
}
