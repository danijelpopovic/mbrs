package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;

import myplugin.generator.fmmodel.gui.FMStandardForm;

/** FMModel: Singleton class. This is intermediate data structure that keeps metadata
 * extracted from MagicDraw model. Data structure should be optimized for code generation
 * using freemarker
 */

public class FMModel {
	
	private List<FMClass> classes = new ArrayList<FMClass>();
	private List<FMStandardForm> classesForm = new ArrayList<FMStandardForm>();
	private List<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();
	
	//....
	/** @ToDo: Add lists of other elements, if needed */
	private FMModel() {
		
	}
	
	private static FMModel model;
	
	public static FMModel getInstance() {
		if (model == null) {
			model = new FMModel();			
		}
		return model;
	}
	
	public List<FMClass> getClasses() {
		return classes;
	}
	public void setClasses(List<FMClass> classes) {
		this.classes = classes;
	}
	public List<FMEnumeration> getEnumerations() {
		return enumerations;
	}
	public void setEnumerations(List<FMEnumeration> enumerations) {
		this.enumerations = enumerations;
	}

	public List<FMStandardForm> getClassesForm() {
		return classesForm;
	}

	public void setClassesForm(List<FMStandardForm> classesForm) {
		this.classesForm = classesForm;
	}



}
