package ${class.typePackage};
<#list class.importedPackages as package>
import ${package};
</#list>
import framework.*;

import java.text.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "${class.name}")
${class.visibility} class ${class.name} extends AbstractEntity implements Serializable {  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
<#list properties as property>
	<#if property.upper == 1 >
		<#if property.isEnumerated>
		@Enumerated
		</#if>
	<#if property.referenced>
		${property.relationshipAnnotation}
		<#if property.joinColumnAnnotation??>
		@JoinColumn(name="${property.name?lower_case}", referencedColumnName="id", nullable = ${property.nullable?string("true", "false")})
		</#if>
		${property.visibility} ${property.type} ${property.name};
	<#else>
		@Column(name="${property.name}", unique = ${property.unique?string("true", "false")}, nullable = ${property.nullable?string("true", "false")})
		${property.visibility} ${property.type} ${property.name};
	</#if>
    <#elseif property.upper == -1 > 
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "${property.mappedBy}")
  		${property.visibility} Set<${property.type}> ${property.name} = new HashSet<${property.type}>();
    <#else>   
    	<#list 1..property.upper as i>
      ${property.visibility} ${property.type} ${property.name}${i};
		</#list>  
    </#if>
         
</#list>

	public ${class.name}(){
	}

<#list properties as property>
	<#if property.upper == 1 >   
    <#elseif property.upper == -1 >
    	public void add${property.name?cap_first}(${property.type} entity) {
    		if(entity != null) {
    			if(!${property.name}.contains(entity)) {
    				entity.set${class.name?cap_first}(this);
    				${property.name}.add(entity);
    			}
    		}
    	}
    	
    	public void remove${property.name?cap_first}(${property.type} entity) {
    		if(entity != null) {
    			if(${property.name}.contains(entity)) {
					entity.set${class.name?cap_first}(null);
    				${property.name}.remove(entity);
    			}
    		}
    	}
    <#else>
    	<#list 1..property.upper as i>
		</#list>
    </#if>   
</#list>

<#list properties as property>
	<#if property.upper == 1 >   
      public ${property.type} get${property.name?cap_first}(){
           return ${property.name};
      }
      
      public void set${property.name?cap_first}(${property.type} ${property.name}){
           this.${property.name} = ${property.name};
      }
      
    <#elseif property.upper == -1 >
      public Set<${property.type}> get${property.name?cap_first}(){
           return ${property.name};
      }
      
      public void set${property.name?cap_first}( Set<${property.type}> ${property.name}){
           this.${property.name} = ${property.name};
      }
      
    <#else>   
    	<#list 1..property.upper as i>
      public ${property.type} get${property.name?cap_first}${i}(){
           return ${property.name}${i};
      }
      
      public void set${property.name?cap_first}${i}(${property.type} ${property.name}${i}){
           this.${property.name}${i} = ${property.name}${i};
      }
            
		</#list>  
    </#if>     
</#list>

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Object[] getValues() {	
		List<Object> list = new ArrayList<Object>();
		
		list.add(id);		
		 <#list properties as property>
		 	<#if property.upper == 1 >
		 		<#if property.referenced>
		if(${property.name} != null){
			list.add(${property.name}.toString());
		}else{
			list.add("");
		}
			 	<#else>
					<#if property.type == "float">
		list.add(${property.name});
					<#elseif property.type == "double">
		list.add(${property.name});		
					<#elseif property.type == "int">
		list.add(${property.name});	
					<#elseif property.type == "byte">
		list.add(${property.name});
					<#elseif property.type == "short">
		list.add(${property.name});	
					<#elseif property.type == "long">
		list.add(${property.name});	
					<#elseif property.type == "char">
		list.add(${property.name});		
					<#elseif property.type == "boolean">
		list.add(${property.name});	
				<#elseif property.type == "Date">
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String formatedDate = dateFormat.format(${property.name});
		list.add(formatedDate);			
					<#else>
		list.add(${property.name}.toString());
					</#if>
				 </#if>
			 </#if>
		 </#list>
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		<#list properties as property>	
		<#if property.upper == 1 >
			<#if !property.referenced>
				<#if property.shown == true>
		result.append(${property.name} + " ");
				</#if>	
			</#if>	
		</#if>	
		</#list>	
		
		return result.toString();
	}

}
