package myplugin.analyzer;

import java.util.Iterator;

import myplugin.analyzer.BaseAnalyzer.AnalyzerTypeEnum;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMModel;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

public class DaoModelAnalyzer extends BaseAnalyzer{
	
		
	public DaoModelAnalyzer(Package root, String filePackage) {
		super(root, filePackage);	
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
						FMClass fmClass = getClassData(cl, packageName, AnalyzerTypeEnum.DAO);
						fmClass.addImportedPackage("ejb"+ getImportedPackage("", pack, AnalyzerTypeEnum.DAO) + "." + pack.getName());
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
