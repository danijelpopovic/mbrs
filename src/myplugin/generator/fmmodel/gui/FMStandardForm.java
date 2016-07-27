package myplugin.generator.fmmodel.gui;

import java.util.ArrayList;
import java.util.List;

import myplugin.generator.fmmodel.FMClass;


public class FMStandardForm extends FMClass{

	private List<FMUIProperty> FMUIProperties = new ArrayList<FMUIProperty>();
	public FMStandardForm(String name, String classPackage, String visibility) {
		super(name, classPackage, visibility);
		// TODO Auto-generated constructor stub
	
	}
	public List<FMUIProperty> getFMUIProperties() {
		return FMUIProperties;
	}
	public void setFMUIProperties(List<FMUIProperty> fMUIProperties) {
		FMUIProperties = fMUIProperties;
	}

	
}
