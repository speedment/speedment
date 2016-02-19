/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.ui.controller;

import com.speedment.code.StandardTranslatorKey;
import com.speedment.code.TranslatorKey;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.component.EventComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.config.TableProperty;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import static java.util.stream.Collectors.toList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.beans.binding.StringBinding;
import static javafx.application.Platform.runLater;
import javafx.beans.value.ChangeListener;
import org.w3c.dom.Element;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class PreviewController implements Initializable {
    
    private final static double ZOOM_FACTOR = 0.9;
    
    private final UISession session;
    private final Queue<Runnable> onLoad;

    private @FXML TitledPane workspace;
    private @FXML VBox nodes;
    private @FXML TextField filename;
//    private @FXML ChoiceBox<TranslatorKey<DocumentProperty>> target;
    private @FXML WebView web;
    
    private PreviewController(UISession session) {
        this.session = requireNonNull(session);
        this.onLoad  = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        final UserInterfaceComponent ui   = session.getSpeedment().getUserInterfaceComponent();
//        final EventComponent events       = session.getSpeedment().getEventComponent();
//        final CodeGenerationComponent cgc = session.getSpeedment().getCodeGenerationComponent();
//        
//        web.setZoom(ZOOM_FACTOR);
//
//        // When the user selects a new node, reconfigure the preview component
//        ui.getSelectedTreeItems()
//            .addListener((ListChangeListener.Change<? extends TreeItem<DocumentProperty>> change) -> {
//                target.setItems(FXCollections.observableArrayList());
//                
//                if (!change.getList().isEmpty()) {
//                    final TreeItem<DocumentProperty> treeItem = change.getList().get(0);
//                    
//                    if (treeItem != null) {
//                        final DocumentProperty document = treeItem.getValue();
//                        
//                        if (document instanceof Project) {
//                            target.setItems(FXCollections.observableArrayList(
//                                StandardTranslatorKey.projectTranslatorKeys()
//                                    .map(PreviewController::castTranslatorKey)
//                                    .collect(toList())
//                            ));
//                        } else if (document instanceof Table) {
//                            target.setItems(FXCollections.observableArrayList(
//                                StandardTranslatorKey.tableTranslatorKeys()
//                                    .map(PreviewController::castTranslatorKey)
//                                    .collect(toList())
//                            ));
//                        }
//                        
//                        target.getSelectionModel().select(0);
////                        events.notify(new PreviewEvent(document, nodes));
//                    }
//                }
//            });
//
//        // When the preview component is repainted, bind its content to the 
//        // correct document
////        events.on(PreviewEvent.class, ev -> {
////            generatePreview(ev.getDocument(), target.getSelectionModel().selectedItemProperty().get());
////        });
//        
//        // React on changes in the dropdown list
//        target.getSelectionModel().selectedItemProperty().addListener((ob, o, selected) -> {
//            if (!ui.getSelectedTreeItems().isEmpty()) {
//                final TreeItem<DocumentProperty> treeItem = ui.getSelectedTreeItems().get(0);
//                if (treeItem != null) {
//                    generatePreview(treeItem.getValue(), selected);
//                }
//            }
//        });
//        
//        // Create a listener to work off the load queue
//        final WebEngine engine = web.getEngine();
//        engine.getLoadWorker().stateProperty().addListener((ov, old, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                Runnable task;
//                while ((task = onLoad.poll()) != null) {
//                    task.run();
//                }
//            }
//        });
    }
    
//    private <T extends HasMainInterface> void generatePreview(DocumentProperty document, TranslatorKey<DocumentProperty> selection) {
//        if (selection != null) {
//            final CodeGenerationComponent cgc = session.getSpeedment().getCodeGenerationComponent();
//            
//            filename.textProperty().bind(Bindings.createStringBinding(() -> {
//                return cgc.findTranslator(document, selection).get().getName();
//            }, document));
//
//            if (document instanceof TableProperty) {
//                final TableProperty table = (TableProperty) document;
//
//                loadPreview(Bindings.createStringBinding(() -> {
//                        if (table.enabledProperty().get()) {
//                            return cgc.findTranslator(document, selection).toCode();
//                        } else {
//                            return "(No code is generated for disabled nodes.)";
//                        }
//                    },
//                    table
//                ));
//            } else if (document instanceof ProjectProperty) {
//                final ProjectProperty project = (ProjectProperty) document;
//
//                loadPreview(Bindings.createStringBinding(
//                    () -> {
//                        if (project.enabledProperty().get()) {
//                            return cgc.findTranslator(document, selection).toCode();
//                        } else {
//                            return "(No code is generated for disabled nodes.)";
//                        }
//                    },
//                    project
//                ));
//            } else {
//                filename.setText("");
//            }
//        }
//    }
//    
//    private static <T extends HasMainInterface> TranslatorKey<DocumentProperty> castTranslatorKey(TranslatorKey<T> key) {
//        @SuppressWarnings("unchecked")
//        final TranslatorKey<DocumentProperty> casted = (TranslatorKey<DocumentProperty>) key;
//        return casted;
//    }
    
    public static Node create(UISession session) {
        return Loader.create(session, "Preview", PreviewController::new);
	}
    
    private void loadPreview(StringBinding... preview) {
        final WebEngine engine = web.getEngine();
        
        onLoad.add(() -> {
            for (int i = 0; i < preview.length; i++) {
                final int j = i;
                
                final ChangeListener<String> change = (ob, o, newText) -> {
                    updateElement(j, newText);
                };
                
                preview[j].addListener(change);
                updateElement(j, preview[j].get());
                
                runLater(() -> onLoad.add(() -> preview[j].removeListener(change)));
            }
        });
        
        final StringBuilder html = new StringBuilder(HTML_PREFIX);
        for (int i = 0; i < preview.length; i++) {
            html.append(ELEMENT_PREFIX).append(i).append(ELEMENT_SUFFIX);
        }
        
        html.append(HTML_SUFFIX);
        engine.loadContent(html.toString());
    }

    private void updateElement(int index, String newText) {
        final WebEngine engine = web.getEngine();
        final Element element = engine.getDocument().getElementById("code_" + index);
        if (element != null) {
            element.setTextContent(newText);
            engine.executeScript("Prism.highlightAll()");
        }
    }

    private final static String
        ELEMENT_PREFIX = "<pre class=\"line-numbers\"><code id=\"code_",
        ELEMENT_SUFFIX = "\" class=\"language-java\"></code></pre>",
        PRISM_JS = "var _self=\"undefined\"!=typeof window?window:\"undefined\"!=typeof WorkerGlobalScope&&self instanceof WorkerGlobalScope?self:{},Prism=function(){var e=/\\blang(?:uage)?-(?!\\*)(\\w+)\\b/i,t=_self.Prism={util:{encode:function(e){return e instanceof n?new n(e.type,t.util.encode(e.content),e.alias):\"Array\"===t.util.type(e)?e.map(t.util.encode):e.replace(/&/g,\"&amp;\").replace(/</g,\"&lt;\").replace(/\\u00a0/g,\" \")},type:function(e){return Object.prototype.toString.call(e).match(/\\[object (\\w+)\\]/)[1]},clone:function(e){var n=t.util.type(e);switch(n){case\"Object\":var a={};for(var r in e)e.hasOwnProperty(r)&&(a[r]=t.util.clone(e[r]));return a;case\"Array\":return e.map&&e.map(function(e){return t.util.clone(e)})}return e}},languages:{extend:function(e,n){var a=t.util.clone(t.languages[e]);for(var r in n)a[r]=n[r];return a},insertBefore:function(e,n,a,r){r=r||t.languages;var l=r[e];if(2==arguments.length){a=arguments[1];for(var i in a)a.hasOwnProperty(i)&&(l[i]=a[i]);return l}var o={};for(var s in l)if(l.hasOwnProperty(s)){if(s==n)for(var i in a)a.hasOwnProperty(i)&&(o[i]=a[i]);o[s]=l[s]}return t.languages.DFS(t.languages,function(t,n){n===r[e]&&t!=e&&(this[t]=o)}),r[e]=o},DFS:function(e,n,a,r){r=r||{};for(var l in e)e.hasOwnProperty(l)&&(n.call(e,l,e[l],a||l),\"Object\"!==t.util.type(e[l])||r[e[l]]?\"Array\"!==t.util.type(e[l])||r[e[l]]||(r[e[l]]=!0,t.languages.DFS(e[l],n,l,r)):(r[e[l]]=!0,t.languages.DFS(e[l],n,null,r)))}},plugins:{},highlightAll:function(e,n){for(var a,r=document.querySelectorAll('code[class*=\"language-\"], [class*=\"language-\"] code, code[class*=\"lang-\"], [class*=\"lang-\"] code'),l=0;a=r[l++];)t.highlightElement(a,e===!0,n)},highlightElement:function(n,a,r){for(var l,i,o=n;o&&!e.test(o.className);)o=o.parentNode;o&&(l=(o.className.match(e)||[,\"\"])[1],i=t.languages[l]),n.className=n.className.replace(e,\"\").replace(/\\s+/g,\" \")+\" language-\"+l,o=n.parentNode,/pre/i.test(o.nodeName)&&(o.className=o.className.replace(e,\"\").replace(/\\s+/g,\" \")+\" language-\"+l);var s=n.textContent,u={element:n,language:l,grammar:i,code:s};if(!s||!i)return t.hooks.run(\"complete\",u),void 0;if(t.hooks.run(\"before-highlight\",u),a&&_self.Worker){var g=new Worker(t.filename);g.onmessage=function(e){u.highlightedCode=e.data,t.hooks.run(\"before-insert\",u),u.element.innerHTML=u.highlightedCode,r&&r.call(u.element),t.hooks.run(\"after-highlight\",u),t.hooks.run(\"complete\",u)},g.postMessage(JSON.stringify({language:u.language,code:u.code,immediateClose:!0}))}else u.highlightedCode=t.highlight(u.code,u.grammar,u.language),t.hooks.run(\"before-insert\",u),u.element.innerHTML=u.highlightedCode,r&&r.call(n),t.hooks.run(\"after-highlight\",u),t.hooks.run(\"complete\",u)},highlight:function(e,a,r){var l=t.tokenize(e,a);return n.stringify(t.util.encode(l),r)},tokenize:function(e,n){var a=t.Token,r=[e],l=n.rest;if(l){for(var i in l)n[i]=l[i];delete n.rest}e:for(var i in n)if(n.hasOwnProperty(i)&&n[i]){var o=n[i];o=\"Array\"===t.util.type(o)?o:[o];for(var s=0;s<o.length;++s){var u=o[s],g=u.inside,c=!!u.lookbehind,f=0,h=u.alias;u=u.pattern||u;for(var p=0;p<r.length;p++){var d=r[p];if(r.length>e.length)break e;if(!(d instanceof a)){u.lastIndex=0;var m=u.exec(d);if(m){c&&(f=m[1].length);var y=m.index-1+f,m=m[0].slice(f),v=m.length,k=y+v,b=d.slice(0,y+1),w=d.slice(k+1),P=[p,1];b&&P.push(b);var A=new a(i,g?t.tokenize(m,g):m,h);P.push(A),w&&P.push(w),Array.prototype.splice.apply(r,P)}}}}}return r},hooks:{all:{},add:function(e,n){var a=t.hooks.all;a[e]=a[e]||[],a[e].push(n)},run:function(e,n){var a=t.hooks.all[e];if(a&&a.length)for(var r,l=0;r=a[l++];)r(n)}}},n=t.Token=function(e,t,n){this.type=e,this.content=t,this.alias=n};if(n.stringify=function(e,a,r){if(\"string\"==typeof e)return e;if(\"Array\"===t.util.type(e))return e.map(function(t){return n.stringify(t,a,e)}).join(\"\");var l={type:e.type,content:n.stringify(e.content,a,r),tag:\"span\",classes:[\"token\",e.type],attributes:{},language:a,parent:r};if(\"comment\"==l.type&&(l.attributes.spellcheck=\"true\"),e.alias){var i=\"Array\"===t.util.type(e.alias)?e.alias:[e.alias];Array.prototype.push.apply(l.classes,i)}t.hooks.run(\"wrap\",l);var o=\"\";for(var s in l.attributes)o+=(o?\" \":\"\")+s+'=\"'+(l.attributes[s]||\"\")+'\"';return\"<\"+l.tag+' class=\"'+l.classes.join(\" \")+'\" '+o+\">\"+l.content+\"</\"+l.tag+\">\"},!_self.document)return _self.addEventListener?(_self.addEventListener(\"message\",function(e){var n=JSON.parse(e.data),a=n.language,r=n.code,l=n.immediateClose;_self.postMessage(t.highlight(r,t.languages[a],a)),l&&_self.close()},!1),_self.Prism):_self.Prism;var a=document.getElementsByTagName(\"script\");return a=a[a.length-1],a&&(t.filename=a.src,document.addEventListener&&!a.hasAttribute(\"data-manual\")&&document.addEventListener(\"DOMContentLoaded\",t.highlightAll)),_self.Prism}();\"undefined\"!=typeof module&&module.exports&&(module.exports=Prism),\"undefined\"!=typeof global&&(global.Prism=Prism);\n" +
                    "Prism.languages.clike={comment:[{pattern:/(^|[^\\\\])\\/\\*[\\w\\W]*?\\*\\//,lookbehind:!0},{pattern:/(^|[^\\\\:])\\/\\/.*/,lookbehind:!0}],string:/([\"'])(\\\\(?:\\r\\n|[\\s\\S])|(?!\\1)[^\\\\\\r\\n])*\\1/,\"class-name\":{pattern:/((?:\\b(?:class|interface|extends|implements|trait|instanceof|new)\\s+)|(?:catch\\s+\\())[a-z0-9_\\.\\\\]+/i,lookbehind:!0,inside:{punctuation:/(\\.|\\\\)/}},keyword:/\\b(if|else|while|do|for|return|in|instanceof|function|new|try|throw|catch|finally|null|break|continue)\\b/,\"boolean\":/\\b(true|false)\\b/,\"function\":/[a-z0-9_]+(?=\\()/i,number:/\\b-?(?:0x[\\da-f]+|\\d*\\.?\\d+(?:e[+-]?\\d+)?)\\b/i,operator:/--?|\\+\\+?|!=?=?|<=?|>=?|==?=?|&&?|\\|\\|?|\\?|\\*|\\/|~|\\^|%/,punctuation:/[{}[\\];(),.:]/};\n" +
                    "Prism.languages.java=Prism.languages.extend(\"clike\",{keyword:/\\b(abstract|continue|for|new|switch|assert|default|goto|package|synchronized|boolean|do|if|private|this|break|double|implements|protected|throw|byte|else|import|public|throws|case|enum|instanceof|return|transient|catch|extends|int|short|try|char|final|interface|static|void|class|finally|long|strictfp|volatile|const|float|native|super|while)\\b/,number:/\\b0b[01]+\\b|\\b0x[\\da-f]*\\.?[\\da-fp\\-]+\\b|\\b\\d*\\.?\\d+(?:e[+-]?\\d+)?[df]?\\b/i,operator:{pattern:/(^|[^.])(?:\\+[+=]?|-[-=]?|!=?|<<?=?|>>?>?=?|==?|&[&=]?|\\|[|=]?|\\*=?|\\/=?|%=?|\\^=?|[?:~])/m,lookbehind:!0}});\n" +
                    "!function(){\"undefined\"!=typeof self&&self.Prism&&self.document&&Prism.hooks.add(\"complete\",function(e){if(e.code){var t=e.element.parentNode,s=/\\s*\\bline-numbers\\b\\s*/;if(t&&/pre/i.test(t.nodeName)&&(s.test(t.className)||s.test(e.element.className))&&!e.element.querySelector(\".line-numbers-rows\")){s.test(e.element.className)&&(e.element.className=e.element.className.replace(s,\"\")),s.test(t.className)||(t.className+=\" line-numbers\");var n,a=e.code.match(/\\n(?!$)/g),l=a?a.length+1:1,m=new Array(l+1);m=m.join(\"<span></span>\"),n=document.createElement(\"span\"),n.className=\"line-numbers-rows\",n.innerHTML=m,t.hasAttribute(\"data-start\")&&(t.style.counterReset=\"linenumber \"+(parseInt(t.getAttribute(\"data-start\"),10)-1)),e.element.appendChild(n)}}})}();",
        PRISM_CSS = "code[class*=\"language-\"],\n" +
                "pre[class*=\"language-\"] {\n" +
                "	color: black;\n" +
                "	text-shadow: 0 1px white;\n" +
                "	font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;\n" +
                "	direction: ltr;\n" +
                "	text-align: left;\n" +
                "	white-space: pre;\n" +
                "	word-spacing: normal;\n" +
                "	word-break: normal;\n" +
                "	word-wrap: normal;\n" +
                "	line-height: 1.5;\n" +
                "\n" +
                "	-moz-tab-size: 4;\n" +
                "	-o-tab-size: 4;\n" +
                "	tab-size: 4;\n" +
                "\n" +
                "	-webkit-hyphens: none;\n" +
                "	-moz-hyphens: none;\n" +
                "	-ms-hyphens: none;\n" +
                "	hyphens: none;\n" +
                "}\n" +
                "\n" +
                "pre[class*=\"language-\"]::-moz-selection, pre[class*=\"language-\"] ::-moz-selection,\n" +
                "code[class*=\"language-\"]::-moz-selection, code[class*=\"language-\"] ::-moz-selection {\n" +
                "	text-shadow: none;\n" +
                "	background: #b3d4fc;\n" +
                "}\n" +
                "\n" +
                "pre[class*=\"language-\"]::selection, pre[class*=\"language-\"] ::selection,\n" +
                "code[class*=\"language-\"]::selection, code[class*=\"language-\"] ::selection {\n" +
                "	text-shadow: none;\n" +
                "	background: #b3d4fc;\n" +
                "}\n" +
                "\n" +
                "@media print {\n" +
                "	code[class*=\"language-\"],\n" +
                "	pre[class*=\"language-\"] {\n" +
                "		text-shadow: none;\n" +
                "	}\n" +
                "}\n" +
                "\n" +
                "/* Code blocks */\n" +
                "pre[class*=\"language-\"] {\n" +
                "	padding: 1em;\n" +
                "	margin: .5em 0;\n" +
                "	overflow: auto;\n" +
                "}\n" +
                "\n" +
                ":not(pre) > code[class*=\"language-\"],\n" +
                "pre[class*=\"language-\"] {\n" +
                //"	background: #f5f2f0;\n" +
                "	background: white;\n" +
                "}\n" +
                "\n" +
                "/* Inline code */\n" +
                ":not(pre) > code[class*=\"language-\"] {\n" +
                "	padding: .1em;\n" +
                "	border-radius: .3em;\n" +
                "	white-space: normal;\n" +
                "}\n" +
                "\n" +
                ".token.comment,\n" +
                ".token.prolog,\n" +
                ".token.doctype,\n" +
                ".token.cdata {\n" +
                "	color: slategray;\n" +
                "}\n" +
                "\n" +
                ".token.punctuation {\n" +
                "	color: #999;\n" +
                "}\n" +
                "\n" +
                ".namespace {\n" +
                "	opacity: .7;\n" +
                "}\n" +
                "\n" +
                ".token.property,\n" +
                ".token.tag,\n" +
                ".token.boolean,\n" +
                ".token.number,\n" +
                ".token.constant,\n" +
                ".token.symbol,\n" +
                ".token.deleted {\n" +
                "	color: #905;\n" +
                "}\n" +
                "\n" +
                ".token.selector,\n" +
                ".token.attr-name,\n" +
                ".token.string,\n" +
                ".token.char,\n" +
                ".token.builtin,\n" +
                ".token.inserted {\n" +
                "	color: #690;\n" +
                "}\n" +
                "\n" +
                ".token.operator,\n" +
                ".token.entity,\n" +
                ".token.url,\n" +
                ".language-css .token.string,\n" +
                ".style .token.string {\n" +
                "	color: #a67f59;\n" +
                "	background: hsla(0, 0%, 100%, .5);\n" +
                "}\n" +
                "\n" +
                ".token.atrule,\n" +
                ".token.attr-value,\n" +
                ".token.keyword {\n" +
                "	color: #07a;\n" +
                "}\n" +
                "\n" +
                ".token.function {\n" +
                "	color: #DD4A68;\n" +
                "}\n" +
                "\n" +
                ".token.regex,\n" +
                ".token.important,\n" +
                ".token.variable {\n" +
                "	color: #e90;\n" +
                "}\n" +
                "\n" +
                ".token.important,\n" +
                ".token.bold {\n" +
                "	font-weight: bold;\n" +
                "}\n" +
                ".token.italic {\n" +
                "	font-style: italic;\n" +
                "}\n" +
                "\n" +
                ".token.entity {\n" +
                "	cursor: help;\n" +
                "}\n" +
                "\n" +
                "pre.line-numbers {\n" +
                "	position: relative;\n" +
                "	padding-left: 3.8em;\n" +
                "	counter-reset: linenumber;\n" +
                "}\n" +
                "\n" +
                "pre.line-numbers > code {\n" +
                "	position: relative;\n" +
                "}\n" +
                "\n" +
                ".line-numbers .line-numbers-rows {\n" +
                "	position: absolute;\n" +
                "	pointer-events: none;\n" +
                "	top: 0;\n" +
                "	font-size: 100%;\n" +
                "	left: -3.8em;\n" +
                "	width: 3em; /* works for line-numbers below 1000 lines */\n" +
                "	letter-spacing: -1px;\n" +
                "	border-right: 1px solid #999;\n" +
                "\n" +
                "	-webkit-user-select: none;\n" +
                "	-moz-user-select: none;\n" +
                "	-ms-user-select: none;\n" +
                "	user-select: none;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "	.line-numbers-rows > span {\n" +
                "		pointer-events: none;\n" +
                "		display: block;\n" +
                "		counter-increment: linenumber;\n" +
                "	}\n" +
                "\n" +
                "		.line-numbers-rows > span:before {\n" +
                "			content: counter(linenumber);\n" +
                "			color: #999;\n" +
                "			display: block;\n" +
                "			padding-right: 0.8em;\n" +
                "			text-align: right;\n" +
                "		}" +
                "html,body{background:#f4f4f4;margin:0em;padding:0em;}",
        HTML_PREFIX = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><script>function update(str){return document.getElementById('code').innerHTML=str;}</script><style>" + PRISM_CSS + "</style></head><body>",
        HTML_SUFFIX = "<script>" + PRISM_JS + "</script></body></html>";
}