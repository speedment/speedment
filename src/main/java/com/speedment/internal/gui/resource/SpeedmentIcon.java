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
package com.speedment.internal.gui.resource;

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
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

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
	COLUMN (SilkIcon.SCRIPT.getFileName()),
	DBMS (SilkIcon.BUILDING_KEY.getFileName()),
	FOREIGN_KEY (SilkIcon.TABLE_LINK.getFileName()),
	FOREIGN_KEY_COLUMN (SilkIcon.SCRIPT_LINK.getFileName()),
	INDEX (SilkIcon.TABLE_LIGHTNING.getFileName()),
	INDEX_COLUMN (SilkIcon.SCRIPT_LIGHTNING.getFileName()),
	PRIMARY_KEY (SilkIcon.TABLE_KEY.getFileName()),
	PRIMARY_KEY_COLUMN (SilkIcon.SCRIPT_KEY.getFileName()),
	PROJECT (SilkIcon.APPLICATION_SIDE_LIST.getFileName()),
	SCHEMA (SilkIcon.DATABASE.getFileName()),
	TABLE (SilkIcon.TABLE_RELATIONSHIP.getFileName()),
    PLUGIN_DATA (SilkIcon.PLUGIN.getFileName()),
    
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

	private final String filename;
	
    private static final Logger LOGGER = LoggerManager.getLogger(SpeedmentIcon.class);
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
//		NODE_ICONS.put(ProjectManager.class, PROJECT_MANAGER);
        NODE_ICONS.put(PluginData.class, PLUGIN_DATA);
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
	
	public static ImageView forNode(Node node) {
        requireNonNull(node);
        
        final Optional<String> path = node.getIconPath();
        if (path.isPresent()) {
            final InputStream stream = SpeedmentIcon.class.getResourceAsStream(path.get());
            if (stream != null) {
                return new ImageView(new Image(stream));
            } else {
                LOGGER.error(
                    "Config node '" + node.getClass().getSimpleName() + 
                    "' specified a custom icon '" + path.get() + "' that could not be loaded."
                );
            }
        }
        
        final SpeedmentIcon icon = NODE_ICONS.get(node.getInterfaceMainClass());
        if (icon != null) {
            return icon.view();
        } else {
            LOGGER.error("Found no predefined icon for node type '" + node.getClass().getSimpleName() + "'.");
        }
        
		return SilkIcon.HELP.view();
	}
    
    private SpeedmentIcon(String filename) {
        requireNonNull(filename);
		this.filename = filename;
	}
    
    private InputStream getFileInputStream() {
        return getFileInputStream(null);
    }

    private InputStream getFileInputStream(Node node) {
		final InputStream stream = getClass().getResourceAsStream(filename);
		
		if (stream == null) {
			throw new RuntimeException("Could not find icon: '" + filename + "'.");
		}
		
		return stream;
    }
}
