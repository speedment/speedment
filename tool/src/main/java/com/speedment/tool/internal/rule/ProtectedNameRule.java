package com.speedment.tool.internal.rule;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.tool.component.IssueComponent;
import com.speedment.tool.rule.Issue;
import com.speedment.tool.rule.Rule;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A rule which checks the future Java names of generated entities.
 * <p>
 * If a user would want an entity with the same name as a class used internally by the
 * the generated code, this might cause conflicts during runtime. Imagine for example
 * that the user would call his entity 'Integer'. Thus, we list all types
 * that normally show up during code generation. 
 * <P>
 * This rule is not perfect. The TypeMapper types are not included, and might cause
 * problems.
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class ProtectedNameRule implements Rule{
    private final static String[] PROTECTED_NAMES = {
        "AbstractEntity", "AbstractApplicationMetadata", "AbstractSqlManager",
        "Boolean",
        "Char", "Class", "ComparableField", "ComparableFieldImpl",
        "Double",
        "Field", "FieldIdentifier", "Float",
        "Integer", "Identifier", "Injector", 
        "Long",
        "Optional", "Object", "OptionalUtil",
        "ProjectComponent",
        "ResultSet",
        "String", "StringField", "StringFieldImpl", "SqlManager", "Stream", "StringBuilder", "SQLException", "SpeedmentException", "StringJoiner", "Speedment",
        "Table", "TypeMapper", "Tuple", "Tuples"           
    };
    
    private final Pattern pattern;
    private final AtomicBoolean noIssues;
    
    private @Inject ProjectComponent projectComponent;
    private @Inject IssueComponent issues;
    
    public ProtectedNameRule(){
        String regex = Arrays.stream(PROTECTED_NAMES).collect(Collectors.joining("|"));
        this.pattern = Pattern.compile( regex , Pattern.CASE_INSENSITIVE );
        this.noIssues = new AtomicBoolean(true);
    }

    @Override
    public CompletableFuture<Boolean> verify() {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        ForkJoinPool.commonPool().execute( () -> checkRule(future) );
        return future;
    }
    
    private void checkRule(CompletableFuture<Boolean> future){
        Project project = projectComponent.getProject();        
        project.children().forEach( this::checkRuleRecursive );    
        check( project );
        future.complete( noIssues.get() );
    }
    
    private void checkRuleRecursive(Document document){
        document.children().forEach( this::checkRuleRecursive );
        check( document );
    }
    
    private void check(Document document){
        final HasAlias alias = HasAlias.of(document);
        final String docName = alias.getJavaName();
        if( pattern.matcher( docName ).matches() ){
            noIssues.set(false);            
            issues.post( new Issue() {
                @Override
                public String getTitle() {
                    return "Protected Name: " + docName;
                }

                @Override
                public String getDescription() {
                    return "The name " + docName +" is protected. If this name"
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
