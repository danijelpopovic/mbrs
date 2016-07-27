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


public class ModelAnalyzer {	
	//root model package
	private Package root;
	
	//java root package for generated code
	private String filePackage;
	
	public ModelAnalyzer(Package root, String filePackage) {
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
						FMModel.getInstance().getClasses().add(fmClass);
					}	
				}
				
				if (ownedElement instanceof Enumeration) {
					Enumeration en = (Enumeration)ownedElement;
					FMEnumeration fmEnumeration = getEnumerationData(en, packageName);
					FMModel.getInstance().getEnumerations().add(fmEnumeration);
					
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
	
	private FMClass getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null) 
			throw new AnalyzeException("Classes must have names!");
		
		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());
		Iterator<Property> it = ModelHelper.attributes(cl);
		while (it.hasNext()) {
			Property p = it.next();
			FMProperty prop = getPropertyData(p, cl);
			
			//Dodavanje importa za atribute klase koji su enumeracije
			if(p.getType() instanceof Enumeration) {
				if(!fmClass.getImportedPackages().contains(Resources.IMPORT_ENUM_PREFIX + "." + p.getType().getName())) {
					fmClass.getImportedPackages().add(Resources.IMPORT_ENUM_PREFIX + "." + p.getType().getName());
				}
			}
			
			//za sada ovako ako se koristi klasa date koja je u modelu, da se ne pise java.util.Date
			if(p.getType().getName().equals("Date")) {
				fmClass.getImportedPackages().add("java.util.Date");
			}
			
			//Ako su veze OneToOne i ManyToMany prop ce biti null
			if (prop!=null){
				Property oppositeProperty = p.getOpposite();
				if(oppositeProperty != null) {
					Element e = oppositeProperty.getOwner();
					if(e instanceof Class){
						Class oppositePropertyClass = (Class) e;
						String tempPackageName = "";
						if(filePackage != null) {
							tempPackageName += filePackage + ".";
						}
						//tempPackageName += oppositePropertyClass.getPackage().getOwningPackage().getName() + "." + oppositePropertyClass.getPackage().getName();
						tempPackageName = getImportedPackage(tempPackageName, oppositePropertyClass.getPackage()) + "." + oppositePropertyClass.getPackage().getName() + "." + oppositePropertyClass.getName();
						if(!fmClass.getImportedPackages().contains(tempPackageName) && !tempPackageName.equals(packageName)) {
							fmClass.addImportedPackage(tempPackageName);
						}
						
					}
				}
				fmClass.addProperty(prop);	
			}
		}	
		
		/** @ToDo:
		 * Add import declarations etc. */		
		return fmClass;
	}
	
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
	
	
	private FMProperty getPropertyData(Property p, Class cl) throws AnalyzeException {
		String attName = p.getName();
		if (attName == null) 
			throw new AnalyzeException("Properties of the class: " + cl.getName() +
					" must have names!");
		Type attType = p.getType();
		if (attType == null)
			throw new AnalyzeException("Property " + cl.getName() + "." +
			p.getName() + " must have type!");
		
		String typeName = attType.getName();
		if (typeName == null)
			throw new AnalyzeException("Type ot the property " + cl.getName() + "." +
			p.getName() + " must have name!");		
			
		int lower = p.getLower();
		int upper = p.getUpper();
		
		FMProperty prop = new FMProperty(attName, typeName, p.getVisibility().toString(), 
				lower, upper);
		
		
		
		if(attType.has_associationOfEndType()){
			prop.setReferenced(true);
			Property oppositeProperty = p.getOpposite();
			if(oppositeProperty != null ) {
				int opossitePropertyUpper = oppositeProperty.getUpper();
				if(p.getUpper() == 1) {
					if(opossitePropertyUpper == 1) {
						//prop.setRelationshipAnnotation("@OneToOne");
						//Posto ne sme biti veza OneToOne
						return null;
					}else if( opossitePropertyUpper == -1) {
						prop.setRelationshipAnnotation("@ManyToOne");
						//prop.setMappedBy(attName);
						prop.setJoinColumnAnnotation("true");
						
					}
				}else if(p.getUpper() == -1) {
					if(opossitePropertyUpper == 1) {
						prop.setRelationshipAnnotation("@OneToMany");
						prop.setMappedBy(oppositeProperty.getName());
					}else if(opossitePropertyUpper == -1) {
						//prop.setRelationshipAnnotation("@ManyToMany");
						//Posto ne sme biti veza ManyToMany
						return null;
					}
				}
				
				//System.out.println("Naziv prop " + attName + ", a njegov opossite je " + oppositeProperty.getName());
			}
			
			

			
		}

		/**
		 * Obrada stored property stereotipa
		 */
		if(StereotypesHelper.getAppliedStereotypeByString(p, Resources.STORED_PROPERTY) != null) {
			Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.STORED_PROPERTY);
			
			prop.setUnique(new Boolean(getTagValue(p,propStereotype,"unique")));
			prop.setNullable(new Boolean(getTagValue(p,propStereotype,"nullable")));
			prop.setIsEnumerated(new Boolean(getTagValue(p,propStereotype,"isEnumeration")));
			
			//svi atributi stereotipa 
			List<Property> attributes = propStereotype.getOwnedAttribute();
			//svi nasledjeni atributi
			List<NamedElement> inheritedAttributes = (List<NamedElement>) propStereotype.getInheritedMember();
			
			//prolazak kroz sve atribute
			for (int i = 0; i < attributes.size(); ++i) {
				Property tagDef = attributes.get(i);
				List value = StereotypesHelper.getStereotypePropertyValue(p, propStereotype, tagDef.getName());
				for(int j = 0; j < value.size(); ++j) {
					Object tagValue = (Object)value.get(j);
					prop.setColumnName(tagValue.toString());
				}
			}
			
			//prolazak kroz sve nasledjene atribute
			for (int i = 0; i < inheritedAttributes.size(); ++i) {
				Property tagDef = (Property) inheritedAttributes.get(i);
				List value = StereotypesHelper.getStereotypePropertyValue(p, propStereotype, tagDef.getName());
				for(int j = 0; j < value.size(); ++j) {
					Object tagValue = (Object)value.get(j);
					//System.out.println(tagValue.toString());
					if(tagValue instanceof EnumerationLiteral) {
						EnumerationLiteral literal = (EnumerationLiteral)tagValue;
						//System.out.println(literal.getName());
					}
				}
			}	
			
			
			String shownString = getTagValue(p,propStereotype,"shown");
			if (shownString == null)
				prop.setShown(true);
			else{
				if(shownString.equals("true")){
					prop.setShown(true);
				}else if(shownString.equals("false")){
					prop.setShown(false);
				}
			}
			
		}
		
		
		
		
		
		return prop;		
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

	private FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size(); i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)  
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() +
				" must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}	
	
	
}
