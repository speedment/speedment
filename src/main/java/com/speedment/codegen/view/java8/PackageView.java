package com.speedment.codegen.view.java8;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.Package_;
import com.speedment.codegen.view.CodeView;
import java.util.Stack;
import java.util.stream.Collectors;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class PackageView extends CodeView<Package_> {
	private final static String PACKAGE_STRING = "package ";
	
	@Override
	public CharSequence render(CodeGenerator renderer, Package_ model) {
		final Stack<CharSequence> packages = new Stack<>();

		Package_ p = model;
		do { packages.add(p.getName_()); } 
		while ((p = p.getPackage()) != null);
		
		return packages.stream().collect(Collectors.joining(DOT, PACKAGE_STRING, SC));
	}
}
