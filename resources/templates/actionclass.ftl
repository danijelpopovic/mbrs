package ${class.typePackage};

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import framework.GenericStandardForm;
import framework.MainFrame;
import framework.ReportUtil;

<#list class.importedPackages as package>
import ${package}.*;
</#list>

${class.visibility} class ${class.name}Action extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ${class.name}Action() {
		putValue(NAME, "${class.name}");
		//putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_MASK)); To be decided
		<#if class.tooltip??>
		putValue(SHORT_DESCRIPTION, "${class.tooltip}");
		</#if>
		//putValue(SMALL_ICON, new ImageIcon("images/ukidanje_racuna.jpg"));	To be decided
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		${class.name} ${class.name?lower_case} = new ${class.name}();
		${class.name}HibernateDao dao = new ${class.name}HibernateDao();
		${class.name}Panel panel = new ${class.name}Panel();
		String reportPath = ReportUtil.getReportFilePath("${class.name}");
		
		GenericStandardForm form = new GenericStandardForm(${class.name?lower_case}, dao , panel, reportPath);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}