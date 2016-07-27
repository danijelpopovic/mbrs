package myplugin.generator.fmmodel;



public class FMProperty extends FMElement  {
	//Property type
	private String type;
	// Property visibility (public, private, protected, package)
	private String visibility;
	//Multiplicity (lower value)
	private Integer lower;
	//Multiplicity (upper value) 
	private Integer upper;
	private boolean referenced = false;
	private String relationshipAnnotation;
	private String joinColumnAnnotation;
	
	/** @ToDo: Add length, precision, unique... whatever is needed for ejb class generation
	 * Also, provide these meta-attributes or tags in the modeling languange metaclass or 
	 * stereotype */

	//Added 
	protected Integer length;
	protected Integer precision;
	protected ComponentKind componentKind;
	protected String toolTip;
	//private String migLayout;
	//private String migLabel;
	protected Boolean shown = true;
	protected Boolean tableColumn = true;
	protected Boolean unique = false;
	protected Boolean nullable = true;
	protected Boolean required = false;
	protected Boolean isEnumerated = false;
	
	protected String columnName;
	protected String mappedBy;
	
	//
	
	public FMProperty(String name, String type, String visibility, int lower, int upper) {
		super(name);
		this.type = type;
		this.visibility = visibility;
		
		this.lower = lower;
		this.upper = upper;		
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public ComponentKind getComponentKind() {
		return componentKind;
	}

	public void setComponentKind(ComponentKind componentKind) {
		this.componentKind = componentKind;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
/*
	public String getMigLayout() {
		return migLayout;
	}

	public void setMigLayout(String migLayout) {
		this.migLayout = migLayout;
	}

	public String getMigLabel() {
		return migLabel;
	}

	public void setMigLabel(String migLabel) {
		this.migLabel = migLabel;
	}
*/
	public Boolean getShown() {
		return shown;
	}

	public void setShown(Boolean shown) {
		this.shown = shown;
	}

	public Boolean getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(Boolean tableColumn) {
		this.tableColumn = tableColumn;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced = referenced;
	}

	public String getRelationshipAnnotation() {
		return relationshipAnnotation;
	}

	public void setRelationshipAnnotation(String annotation) {
		this.relationshipAnnotation = annotation;
	}

	public String getJoinColumnAnnotation() {
		return joinColumnAnnotation;
	}

	public void setJoinColumnAnnotation(String joinColumnAnnotation) {
		this.joinColumnAnnotation = joinColumnAnnotation;
	}
	
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public Boolean getIsEnumerated() {
		return isEnumerated;
	}

	public void setIsEnumerated(Boolean isEnumerated) {
		this.isEnumerated = isEnumerated;
	}

	

}
