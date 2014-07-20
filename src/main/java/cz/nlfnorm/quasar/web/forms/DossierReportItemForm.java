package cz.nlfnorm.quasar.web.forms;

import javax.validation.Valid;

import cz.nlfnorm.quasar.entities.Company;
import cz.nlfnorm.quasar.entities.DossierReport;
import cz.nlfnorm.quasar.entities.DossierReportItem;

public class DossierReportItemForm extends AbstractLogItemForm 
	implements LogItemForm, CompanyForm{

	@Valid
	private DossierReportItem item;
	
	public DossierReportItemForm(){}
	
	public DossierReportItemForm(final DossierReport dossierReport){
		item = new DossierReportItem(dossierReport);
		setLogId(dossierReport.getId());
	}
	
	public DossierReportItemForm(final DossierReport dossierReport, DossierReportItem item){
		this.item = item;
		setLogId(dossierReport.getId());
	}
	
	@Override
	public void setCompany(final Company company) {
		item.setCompany(company);
	}

	@Override
	public Company getCompany() {
		return item.getCompany();
	}

	@Override
	public String getStringNandoCodes() {
		return getNandoCodes();
	}

	@Override
	public String getStringEacCodes() {
		throw new UnsupportedOperationException();
	}

	public DossierReportItem getItem() {
		return item;
	}

	public void setItem(DossierReportItem item) {
		this.item = item;
	}

	
}
