/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.internal.rule;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.tool.component.IssueComponent;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.rule.Issue;
import com.speedment.tool.rule.Rule;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Simon
 */
public class ProtectedNameRule implements Rule{
    private final static String[] PROTECTED_NAMES = {
        "int", "long", "float", "double", "byte", "short", "string", "char", "boolean",
        "class", "enum"
    };
    
    private final Pattern pattern;
    
    private @Inject ProjectComponent projectComponent;
    private @Inject IssueComponent issues;
    
    public ProtectedNameRule(){
        String regex = Arrays.stream(PROTECTED_NAMES).collect(Collectors.joining("|"));
        this.pattern = Pattern.compile( regex , Pattern.CASE_INSENSITIVE );
    }

    @Override
    public CompletableFuture<Void> verify() {
        return CompletableFuture.runAsync( () -> checkRule() );
    }
    
    private void checkRule(){
        Project project = projectComponent.getProject();        
        project.children().forEach( this::checkRuleRecursive );    
        check( project );
    }
    
    private void checkRuleRecursive(Document document){
        document.children().forEach( this::checkRuleRecursive );
        check( document );
    }
    
    private void check(Document document){
        final HasAlias alias = HasAlias.of(document);
        final String docName = alias.getJavaName();
        if( pattern.matcher( docName ).find() ){
            issues.post( new Issue() {
                @Override
                public String getTitle() {
                    return "Protected Name: " + docName;
                }

                @Override
                public String getDescription() {
                    return "The document " + docName +" is protected. If this name"
                         + " is used during code generation, there might be errors"
                         + " in the generated code.";
                }

                @Override
                public Issue.Level getLevel() {
                    return Level.WARNING;
                }
            } );
        }
    }
}
