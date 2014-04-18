package sk.peterjurkovic.cpr.test;

import java.util.ArrayList;
import java.util.List;

import sk.peterjurkovic.cpr.entities.Webpage;

public class WebpageDateSet {
	
	protected List<Webpage> list = new ArrayList<Webpage>();
	
	public void fillData(){
		
		Webpage w1 = new Webpage();
		w1.setCode("w1");
		w1.getDefaultWebpageContent().setName("w1");
		Webpage w2 = new Webpage();
		w2.setCode("w2");
		w2.getDefaultWebpageContent().setName("w2");
		Webpage w3 = new Webpage();
		w3.setCode("w3");
		w3.getDefaultWebpageContent().setName("w3");
		Webpage w4 = new Webpage();
		w4.setCode("w4");
		w4.getDefaultWebpageContent().setName("w4");
		
		Webpage w11 = new Webpage();
		w11.getDefaultWebpageContent().setName("w11");
		w11.setCode("w11");
		w11.setParent(w1);
		Webpage w12 = new Webpage();
		w12.getDefaultWebpageContent().setName("w12");
		w12.setParent(w1);
		w12.setCode("w12");
		Webpage w13 = new Webpage();
		w13.getDefaultWebpageContent().setName("w13");
		w13.setCode("w13");
		w13.setParent(w1);
		
		Webpage w21 = new Webpage();
		w21.getDefaultWebpageContent().setName("w21");
		w21.setParent(w2);
		w21.setCode("w21");
		Webpage w22 = new Webpage();
		w22.getDefaultWebpageContent().setName("w22");
		w22.setParent(w2);
		w22.setCode("w22");
		Webpage w23 = new Webpage();
		w23.getDefaultWebpageContent().setName("w23");
		w23.setParent(w2);
		w23.setCode("w23");
		
		list.add(w1);
		list.add(w2);
		list.add(w3);
		list.add(w4);
		list.add(w11);
		list.add(w12);
		list.add(w13);
		
		list.add(w21);
		list.add(w22);
		list.add(w23);
	}
	
	public Webpage getByCode(String code){
		for(Webpage w : list){
			if(w.getCode().endsWith(code)){
				return w;
			}
		}
		return null;
	}
	
}	
