package myplugin.generator.fmmodel.gui;

import myplugin.generator.fmmodel.FMProperty;

public class FMUIProperty extends FMProperty{

	
	private Boolean foreignKey = false;
	private Boolean isBoolean = false;
	private Boolean isEnumeration = false;
	private Integer textWidth;
	private Boolean isTextField = true;
	private Boolean isDate = false; 
	private Boolean isTextArea = false;
	private String  packagePath;
	private Boolean isInteger = false;
	private Boolean isReal = false;
	protected String lblName;
	public FMUIProperty(String name, String type, String visibility, int lower,
			int upper) {
		super(name, type, visibility, lower, upper);
	}

	

	public Boolean getForeignKey() {
	
		return foreignKey;
	}

	public void setForeignKey(Boolean foreignKey) {
		this.foreignKey = foreignKey;
	}

	public Boolean getIsBoolean() {
		return isBoolean;
	}

	public void setIsBoolean(Boolean isBoolean) {
		this.isBoolean = isBoolean;
	}

	public Boolean getIsEnumeration() {
		return isEnumeration;
	}

	public void setIsEnumeration(Boolean isEnumeration) {
		this.isEnumeration = isEnumeration;
	}



	public Integer getTextWidth() {
		return textWidth;
	}



	public void setTextWidth(Integer textWidth) {
		this.textWidth = textWidth;
	}



	public Boolean getIsTextField() {
		return isTextField;
	}



	public void setIsTextField(Boolean isTextField) {
		this.isTextField = isTextField;
	}



	public Boolean getIsDate() {
		return isDate;
	}



	public void setIsDate(Boolean isDate) {
		this.isDate = isDate;
	}



	public Boolean getIsTextArea() {
		return isTextArea;
	}



	public void setIsTextArea(Boolean isTextArea) {
		this.isTextArea = isTextArea;
	}



	public String getPackagePath() {
		return packagePath;
	}



	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}



	public Boolean getIsInteger() {
		return isInteger;
	}



	public void setIsInteger(Boolean isInteger) {
		this.isInteger = isInteger;
	}



	public Boolean getIsReal() {
		return isReal;
	}



	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}


	public String getLblName() {
		return lblName;
	}

	public void setLblName(String lblName) {
		this.lblName = lblName;
	}

	

}
