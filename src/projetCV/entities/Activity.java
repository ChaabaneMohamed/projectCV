package projetCV.entities;

public class Activity {

	private int year;
	
	private String nature;
	
	private String title;
	
	private String description;
	
	private String webSite ;
	
	public Activity(int year, String nature, String title, String description, String webSite) {
		this.year = year;
		this.nature = nature;
		this.title = title;
		this.description = description;
		this.webSite = webSite;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
}
