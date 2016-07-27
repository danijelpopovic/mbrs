package myplugin.analyzer;


import java.util.Iterator;

import myplugin.analyzer.BaseAnalyzer.AnalyzerTypeEnum;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMModel;
import myplugin.resources.Resources;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;



/** Model Analyzer takes necessary metadata from the MagicDraw model and puts it in 
 * the intermediate data structure (@see myplugin.generator.fmmodel.FMModel) optimized
 * for code generation using freemarker. Model Analyzer now takes metadata only for ejb code 
 * generation

 * @ToDo: Enhance (or completely rewrite) myplugin.generator.fmmodel classes and  
 * Model Analyzer methods in order to support GUI generation. */ 


public class ActionModelAnalyzer extends BaseAnalyzer{	
	
	public ActionModelAnalyzer(Package root, String filePackage) {
		super(root, filePackage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processPackage(Package pack, String packageOwner) throws AnalyzeException {
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
						FMClass fmClass = getClassData(cl, packageName, AnalyzerTypeEnum.ACTION);
						fmClass.addImportedPackage(Resources.IMPORT_EJB_PREFIX + getImportedPackage("", pack, AnalyzerTypeEnum.ACTION) + "." + pack.getName());
						fmClass.addImportedPackage(Resources.IMPORT_DAO_PREFIX + getImportedPackage("", pack, AnalyzerTypeEnum.ACTION) + "." + pack.getName());
						fmClass.addImportedPackage(Resources.IMPORT_STANDARD_FORM_PREFIX + getImportedPackage("", pack, AnalyzerTypeEnum.ACTION) + "." + pack.getName());
						
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
		}
	}
}
