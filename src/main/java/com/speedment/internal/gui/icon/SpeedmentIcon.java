/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.gui.icon;

import com.speedment.config.Column;
import com.speedment.config.Dbms;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.Node;
import com.speedment.config.PluginData;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Project;
import com.speedment.config.ProjectManager;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public enum SpeedmentIcon {
    
    // Big buttons
    BIG_GENERATE ("/images/icon-generate.png"),
    BIG_CONFIGURE ("/images/icon-configure.png"),
    BIG_GENERATE_HOVER ("/images/icon-generate-hover.png"),
    BIG_CONFIGURE_HOVER ("/images/icon-configure-hover.png"),
    
    // Toolbar
	NEW_PROJECT ("/pics/newProject.png"),
	NEW_PROJECT_24 ("/pics/newProject24.png"),
	OPEN_PROJECT ("/pics/openProject.png"),
	OPEN_PROJECT_24 ("/pics/openProject24.png"),
	RUN_PROJECT ("/pics/runProject.png"),
	RUN_PROJECT_24 ("/pics/runProject24.png"),
    
    // Metadata Tree
	COLUMN ("/pics/dbentity/column.png"),
	DBMS ("/pics/dbentity/dbms.png"),
	FOREIGN_KEY ("/pics/dbentity/foreignkey.png"),
	FOREIGN_KEY_COLUMN ("/pics/dbentity/foreignkeycolumn.png"),
	INDEX ("/pics/dbentity/index.png"),
	INDEX_COLUMN ("/pics/dbentity/indexcolumn.png"),
	MANAGER ("/pics/dbentity/manager.png"),
	PRIMARY_KEY ("/pics/dbentity/primarykey.png"),
	PRIMARY_KEY_COLUMN ("/pics/dbentity/primarykeycolumn.png"),
	PROJECT ("/pics/dbentity/project.png"),
	PROJECT_MANAGER ("/pics/dbentity/projectmanager.png"),
	SCHEMA ("/pics/dbentity/schema.png"),
	TABLE ("/pics/dbentity/table.png"),
    PLUGIN_DATA (node -> ((PluginData) node).findPlugin().getIconPath()),
    
    // Menu icons
	ADD_DBMS_TRANS ("/pics/dialog/add_dbms_trans.png"),
	OPEN_FILE ("/pics/dialog/openFile.png"),
	QUESTION ("/pics/dialog/question.png"),
	SPEEDMENT_LOGO ("/pics/dialog/speedment_logo.png"),
	SPEEDMENT_LOGO_100 ("/pics/dialog/speedment_logo100.png"),
	WALKING_MAN ("/pics/dialog/walking_man.gif"),
	WALKING_MAN_SMALL ("/pics/dialog/walking_man_small.png"),
    
    // Logotype
	SPIRE ("/images/logo.png");

	private final Function<Node, String> getIconName;
	
	private static final Map<Class<?>, SpeedmentIcon> NODE_ICONS = new ConcurrentHashMap<>();
	
	static {
		NODE_ICONS.put(Dbms.class, DBMS);
		NODE_ICONS.put(Schema.class, SCHEMA);
		NODE_ICONS.put(Table.class, TABLE);
		NODE_ICONS.put(Column.class, COLUMN);
		NODE_ICONS.put(Index.class, INDEX);
		NODE_ICONS.put(IndexColumn.class, INDEX_COLUMN);
		NODE_ICONS.put(ForeignKey.class, FOREIGN_KEY);
		NODE_ICONS.put(ForeignKeyColumn.class, FOREIGN_KEY_COLUMN);
		NODE_ICONS.put(PrimaryKeyColumn.class, PRIMARY_KEY_COLUMN);
		NODE_ICONS.put(Project.class, PROJECT);
		NODE_ICONS.put(ProjectManager.class, PROJECT_MANAGER);
	}
    
    public Image load() {
		return new Image(getFileInputStream());
	}

    public Image load(Node node) {
		return new Image(getFileInputStream(node));
	}
    
    public ImageView view() {
		return new ImageView(load());
	}
	
	public ImageView view(Node node) {
		return new ImageView(load(node));
	}
	
	public static SpeedmentIcon forNode(Node node) {
        requireNonNull(node);
		return NODE_ICONS.get(node.getInterfaceMainClass());
	}
    
    private SpeedmentIcon(String icon) {
        requireNonNull(icon);
		getIconName = node -> icon;
	}
    
    private SpeedmentIcon(Function<Node, String> icon) {
		getIconName = requireNonNull(icon);
	}
    
    private InputStream getFileInputStream() {
        return getFileInputStream(null);
    }

    private InputStream getFileInputStream(Node node) {
        final String iconName = getIconName.apply(node);
		final InputStream stream = getClass().getResourceAsStream(iconName);
		
		if (stream == null) {
			throw new RuntimeException("Could not find icon: '" + iconName + "'.");
		}
		
		return stream;
    }
}
