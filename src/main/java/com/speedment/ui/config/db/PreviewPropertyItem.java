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
package com.speedment.ui.config.db;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import org.controlsfx.property.editor.PropertyEditor;

/**
 *
 * @author Emil Forslund
 */
public final class PreviewPropertyItem extends AbstractPropertyItem<String, StringProperty> {
    
    private final ObservableStringValue preview;
    
    public PreviewPropertyItem(ObservableStringValue preview, String name, String description) {
        super(wrap(preview), name, description, AbstractPropertyItem.DEFAULT_DECORATOR);
        this.preview = preview;
    }
    
    public PreviewPropertyItem(ObservableStringValue preview, String name, String description, Consumer<PropertyEditor<?>> decorator) {
        super(wrap(preview), name, description, decorator);
        this.preview = preview;
    }
    
    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public void setValue(Object obj) {
        // Do nothing. A preview can't change value.
    }

    @Override
    protected PropertyEditor<?> createUndecoratedEditor() {
        return new PreviewPropertyEditor(this);
    }
    
    private static StringProperty wrap(ObservableStringValue value) {
        final StringProperty property = new SimpleStringProperty();
        property.bind(value);
        return property;
    }
    
    private final static class PreviewPropertyEditor extends AbstractPropertyEditor<String, PreviewNode> {

        private PreviewPropertyEditor(PreviewPropertyItem item) {
            super(item, new PreviewNode(item.preview));
        }
        
        @Override
        protected ObservableValue<String> getObservableValue() {
            return new SimpleStringProperty();
        }

        @Override
        public void setValue(String value) {
            // Do nothing. A preview can't change value.
        }
    }
    
    private final static class PreviewNode extends HBox {
        
        private final WebView web;
        private final WebEngine engine;
        
        public PreviewNode(ObservableStringValue preview) {
            web    = new WebView();
            engine = web.getEngine();
            
            preview.addListener((ob, o, newText) -> {
                updateContent(newText);
            });
            
            engine.loadContent(HTML_PREFIX + HTML_SUFFIX);
            
            final AtomicBoolean first = new AtomicBoolean(true);
            engine.getLoadWorker().stateProperty().addListener((ov, old, newState) -> {
                if (first.getAndSet(false) && newState == Worker.State.SUCCEEDED) {
                    updateContent(preview.get());
                }
            });
            
            super.getChildren().add(web);
            setHgrow(web, Priority.ALWAYS);
            web.setPrefHeight(200);
        }
        
        private void updateContent(String newText) {
            engine.getDocument().getElementById("code").setTextContent(newText);
            engine.executeScript("Prism.highlightAll()");
        }
        
        private final static String
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
                    "	background: #f5f2f0;\n" +
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
                    "		}",
            HTML_PREFIX = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><script>function update(str){return document.getElementById('code').innerHTML=str;}</script><style>" + PRISM_CSS + "</style></head><body><pre><code id=\"code\" class=\"language-java\">",
            HTML_SUFFIX = "</code></pre><script>" + PRISM_JS + "</script></body></html>";
    }
}