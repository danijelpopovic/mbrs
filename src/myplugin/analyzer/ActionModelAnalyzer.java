package myplugin.analyzer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.resources.Resources;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Association;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;


/** Model Analyzer takes necessary metadata from the MagicDraw model and puts it in 
 * the intermediate data structure (@see myplugin.generator.fmmodel.FMModel) optimized
 * for code generation using freemarker. Model Analyzer now takes metadata only for ejb code 
 * generation

 * @ToDo: Enhance (or completely rewrite) myplugin.generator.fmmodel classes and  
 * Model Analyzer methods in order to support GUI generation. */ 


public class ActionModelAnalyzer {	
	//root model package
	private Package root;
	
	//java root package for generated code
	private String filePackage;
	
	public ActionModelAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}
	
	public void prepareModel() throws AnalyzeException {
		FMModel.getInstance().getClasses().clear();
		FMModel.getInstance().getEnumerations().clear();
		processPackage(root, filePackage);
	}
	
	private void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		//Recursive procedure that extracts data from package elements and stores it in the 
		// intermediate data structure
		if (pack.getName() == null) throw  
			new AnalyzeException("Packages must have names!");
		
		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}
			
		if (pack.hasOwnedElement()) {			
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Class) {
					Class cl = (Class)ownedElement;
					if(StereotypesHelper.getAppliedStereotypeByString(cl, "StandardForm") != null) {
						FMClass fmClass = getClassData(cl, packageName);
						fmClass.addImportedPackage(Resources.IMPORT_EJB_PREFIX + getImportedPackage("", pack) + "." + pack.getName());
						fmClass.addImportedPackage(Resources.IMPORT_DAO_PREFIX + getImportedPackage("", pack) + "." + pack.getName());
						fmClass.addImportedPackage(Resources.IMPORT_STANDARD_FORM_PREFIX + getImportedPackage("", pack) + "." + pack.getName());
						
						String s = getTagValue(cl,StereotypesHelper.getAppliedStereotypeByString(cl, "StandardForm"),"tooltip");
						if(s != null){
							fmClass.setTooltip(s);
						}
						
						FMModel.getInstance().getClasses().add(fmClass);	
					}
				}
			}
			
			
			
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Package) {					
					Package ownedPackage = (Package)ownedElement;
					if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "BusinessApp") != null){
						//only packages with stereotype BusinessApp are candidates for metadata extraction and code generation:
						processPackage(ownedPackage, packageName);
					}
				}
				
			}
			
			
			/** @ToDo:
			  * Process other package elements, as needed */ 
		}
	}
	
	private String getTagValue(Element el, Stereotype s, String tagName) {
		List value = StereotypesHelper.getStereotypePropertyValueAsString (
	           el, s, tagName);
		if (value == null) 
			return null;
	 	if (value.size() == 0)
			return null;
		return (String) value.get(0);
	}
	
	private FMClass getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null) 
			throw new AnalyzeException("Classes must have names!");
		
		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());
		
		
		/** @ToDo:
		 * Add import declarations etc. */		
		return fmClass;
	}
	
	/**
	 * Metoda rekurzivno prolazi kroz pakete kako bi na kraju dobili punu putanju do trazenog paketa
	 * @param name Naziv paketa koji se formira rekurzivnim prolaskom
	 * @param p Prosledjeni paket
	 * @return
	 */
	private String getImportedPackage(String name, Package p) {
		if(p.getOwningPackage() != null) {
			if(name.endsWith("."))
				name += p.getOwningPackage().getName();
			else
				name += "." + p.getOwningPackage().getName();
			getImportedPackage(name, p.getOwningPackage());
		}
		return name;
	}
	
}
