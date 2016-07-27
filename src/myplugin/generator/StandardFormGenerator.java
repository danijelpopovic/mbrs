package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.fmmodel.gui.FMStandardForm;
import myplugin.generator.options.GeneratorOptions;
import freemarker.template.TemplateException;

public class StandardFormGenerator extends BasicGenerator{	
	
	public StandardFormGenerator(GeneratorOptions generatorOptions) {			
		super(generatorOptions);			
	}

	public void generate() {
		
		try {
			super.generate();
		} catch (IOException e) {		
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		List<FMStandardForm> classes = FMModel.getInstance().getClassesForm();
		for (int i = 0; i < classes.size(); i++) {
			FMStandardForm cl = classes.get(i);			
				Writer out;
				Map<String, Object> context = new HashMap<String, Object>();
				try {
					out = getWriter(cl.getName(), cl.getTypePackage());
					if (out != null) {
						context.clear();
						context.put("class", cl);
						context.put("properties", cl.getFMUIProperties());					
						context.put("importedPackages", cl.getImportedPackages());					
						getTemplate().process(context, out);
						out.flush();
					}
				} catch (TemplateException e) {	
					JOptionPane.showMessageDialog(null, e.getMessage());
				}	
				catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}	
			}			
		}
	}
