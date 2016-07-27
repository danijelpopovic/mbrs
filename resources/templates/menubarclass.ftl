package gui.menu;

<#list packages as package>
import ${package}.*;
</#list>
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

<#list packagesToMenus as menu>
	<#lt>	private JMenu ${menu};
</#list>
	
	
	public MyMenuBar() {
		super();
		createGUI();
	}
	
	private void createGUI() {
	<#list packagesToMenus as menu>
		${menu} = new Menu${menu}();
		add(${menu});
	</#list>
		
	}
	
	<#list packages as package>
		<#assign x = package?last_index_of(".")>
		<#assign className = package?substring(x+1)>
		class Menu${className} extends JMenu {
			public Menu${className}() {
				setText("${className}");
			<#list classes as class>
				<#if class.typePackage == package>
					add(new JMenuItem(new ${class.name}Action()));	
				</#if>
			</#list>
			}
		}
	</#list>

}