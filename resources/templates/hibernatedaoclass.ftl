package ${class.typePackage};
import framework.AbstractHibernateDao;
<#list importedPackages as package>
import ${package}.*;
</#list>

${class.visibility} class ${class.name}HibernateDao extends AbstractHibernateDao<${class.name}> implements ${class.name}Dao{  

}
