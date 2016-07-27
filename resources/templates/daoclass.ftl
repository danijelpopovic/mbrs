package ${class.typePackage};
import framework.GenericDao;
<#list importedPackages as package>
import ${package}.*;
</#list>

${class.visibility} interface ${class.name}Dao extends GenericDao<${class.name}> {  

}
