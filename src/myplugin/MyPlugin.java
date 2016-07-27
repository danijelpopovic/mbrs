package myplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

/** MagicDraw plugin that performes code generation */
public class MyPlugin extends com.nomagic.magicdraw.plugins.Plugin {
	
	String pluginDir = null; 
	
	public void init() {
		
		
		pluginDir = getDescriptor().getPluginDirectory().getPath();
		
		// Creating submenu in the MagicDraw main menu 	
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();		
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getSubmenuActions()));
		
		/** @Todo: load project options (@see myplugin.generator.options.ProjectOptions) from 
		 * ProjectOptions.xml and take ejb generator options */
		
		//for test purpose only:

		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			input = new FileInputStream("generate_path.properties");
			prop.load(input);
			path = prop.getProperty("path");
				
			GeneratorOptions ejbOptions = new GeneratorOptions(path, "ejbclass2", "templates", "{0}.java", true, "ejb");
			//GeneratorOptions formOptions = new GeneratorOptions(path, "standardform", "templates", "{0}.java", true, "gui"); 	
			GeneratorOptions standardFormOption = new GeneratorOptions(path, "panel", "templates", "{0}Panel.java", true, "standardForm");
			GeneratorOptions daoOptions = new GeneratorOptions(path, "daoclass", "templates", "{0}Dao.java", true, "dao");
			GeneratorOptions hibernateDaoOptions = new GeneratorOptions(path, "hibernatedaoclass", "templates", "{0}HibernateDao.java", true, "dao");
			GeneratorOptions enumerationOptions = new GeneratorOptions(path, "enumeration", "templates", "{0}.java", true, "enumerations");
			GeneratorOptions actionOptions = new GeneratorOptions(path, "actionclass", "templates", "{0}Action.java", true, "gui.actions");
			GeneratorOptions menuOptions = new GeneratorOptions(path, "menubarclass", "templates", "{0}.java", true, "gui.actions");
			GeneratorOptions hibernateCfgOptions = new GeneratorOptions(path, "hibernate", "templates", "{0}.cfg.xml", true, "a");
			
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("EJBGenerator", ejbOptions);
			//ProjectOptions.getProjectOptions().getGeneratorOptions().put("FormGenerator", formOptions);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("StandardFormGenerator", standardFormOption);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("DaoGenerator", daoOptions);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("HibernateDaoGenerator", hibernateDaoOptions);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("EnumerationGenerator", enumerationOptions);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("ActionGenerator", actionOptions);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("MenuGenerator", menuOptions);
			ProjectOptions.getProjectOptions().getGeneratorOptions().put("HibernateGenerator", hibernateCfgOptions);
			
			ejbOptions.setTemplateDir(pluginDir + File.separator + ejbOptions.getTemplateDir()); //apsolutna putanja
			//formOptions.setTemplateDir(pluginDir + File.separator + formOptions.getTemplateDir()); //apsolutna putanja
			standardFormOption.setTemplateDir(pluginDir + File.separator + standardFormOption.getTemplateDir());
			daoOptions.setTemplateDir(pluginDir + File.separator + daoOptions.getTemplateDir());
			hibernateDaoOptions.setTemplateDir(pluginDir + File.separator + hibernateDaoOptions.getTemplateDir());
			enumerationOptions.setTemplateDir(pluginDir + File.separator + enumerationOptions.getTemplateDir());
			actionOptions.setTemplateDir(pluginDir + File.separator + actionOptions.getTemplateDir());
			menuOptions.setTemplateDir(pluginDir + File.separator + menuOptions.getTemplateDir());
			hibernateCfgOptions.setTemplateDir(pluginDir + File.separator + hibernateCfgOptions.getTemplateDir());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private NMAction[] getSubmenuActions()
	{
	   return new NMAction[]{
			new GenerateAction("Generate"),
	   };
	}
	
	public boolean close() {
		return true;
	}
	
	public boolean isSupported() {				
		return true;
	}
}


