package cz.nlfnorm.quasar.dto;

public class DossierReportCodeSumDto {
	private int tfReviews = 0;
	private int ddReviews = 0;
	
	public void incrementTfReviews(){
		tfReviews++;
	}
	public void incrementDdReviews(){
		ddReviews++;
	}
	
	public int getTfReviews() {
		return tfReviews;
	}
	public void setTfReviews(int tfReviews) {
		this.tfReviews = tfReviews;
	}
	public int getDdReviews() {
		return ddReviews;
	}
	public void setDdReviews(int ddReviews) {
		this.ddReviews = ddReviews;
	}	
}
