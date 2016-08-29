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
package com.speedment.tool.resource;

import com.speedment.internal.common.logger.Logger;
import com.speedment.internal.common.logger.LoggerManager;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.tool.config.trait.HasIconPath;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

/**
 * An enumeration of all the icons available in the Speedment Icon Package.
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version="3.0")
public enum SpeedmentIcon {

    // Big buttons
    BIG_GENERATE("/images/icon-generate.png"),
    BIG_CONFIGURE("/images/icon-configure.png"),
    BIG_GENERATE_HOVER("/images/icon-generate-hover.png"),
    BIG_CONFIGURE_HOVER("/images/icon-configure-hover.png"),
    // Toolbar
    NEW_PROJECT("/pics/vectors_rendered/newProject.png"),
    NEW_PROJECT_24("/pics/vectors_rendered/newProject24.png"),
    OPEN_PROJECT("/pics/vectors_rendered/openProject.png"),
    OPEN_PROJECT_24("/pics/vectors_rendered/openProject24.png"),
    RUN_PROJECT("/pics/vectors_rendered/runProject.png"),
    RUN_PROJECT_24("/pics/vectors_rendered/runProject24.png"),
    // Metadata Tree
    COLUMN("/pics/vectors_rendered/column.png"),
    DBMS("/pics/vectors_rendered/buildingKey.png"),
    FOREIGN_KEY("/pics/vectors_rendered/foreignKey.png"),
    FOREIGN_KEY_COLUMN("/pics/vectors_rendered/foreignKeyColumn.png"),
    INDEX("/pics/vectors_rendered/index.png"),
    INDEX_COLUMN("/pics/vectors_rendered/indexColumn.png"),
    PRIMARY_KEY("/pics/vectors_rendered/tableKey.png"),
    PRIMARY_KEY_COLUMN("/pics/vectors_rendered/primaryKeyColumn.png"),
    PROJECT("/pics/vectors_rendered/project.png"),
    SCHEMA("/pics/vectors_rendered/schema.png"),
    TABLE("/pics/vectors_rendered/table.png"),
    PLUGIN_DATA("/pics/vectors_rendered/plugin.png"),
    // Menu icons
    ADD_DBMS_TRANS("/pics/dialog/add_dbms_trans.png"),
    OPEN_FILE("/pics/dialog/openFile.png"),
    QUESTION("/pics/dialog/question.png"),
    SPEEDMENT_LOGO("/pics/dialog/speedment_logo.png"),
    SPEEDMENT_LOGO_100("/pics/dialog/speedment_logo100.png"),
    WALKING_MAN("/pics/dialog/walking_man.gif"),
    WALKING_MAN_SMALL("/pics/dialog/walking_man_small.png"),
    // Logotype
    SPIRE("/images/logo.png"),
    //Components controller
 SITEMAP_COLOR("pics/vectors_rendered/sitemapColor.png"),
 BOX("pics/vectors_rendered/box.png"),
 BRICKS("pics/vectors_rendered/bricks.png"),
 BOOK_OPEN("pics/vectors_rendered/BookOpen.png"),
 BOOK_NEXT("pics/vectors_rendered/bookNext.png"),
 DATABASE_CONNECT("pics/vectors_rendered/databaseConnect.png"),
 CONNECT("pics/vectors_rendered/connect.png"),
 PAGE_WHITE_CUP("pics/vectors_rendered/pageWhiteCup.png"),
 CUP_LINK("pics/vectors_rendered/cupLink"),
 TEXT_SIGNATURE("pics/vectors_rendered/textSignature.png"),
 BOOK_LINK("pics/vectors_rendered/bookLink.png"),
 //MenuController
 DISK("pics/vectors_rendered/disk.png"),
 DISK_MULTIPLE("pics/vectors_rendered/diskMultiple.png"),
 DOOR_IN("pics/vectors_rendered/doorIn.png"),
 APPLICATION_SIDE_TREE("pics/vectors_rendered/applicationSideTree.png"),
 APPLICATION_FORM("pics/vectors_rendered/applicationForm.png"),
 APPLICATION_XP_TERMINAL("pics/vectors_rendered/applicationXpTerminal.png"),
 USER_COMMENT("pics/vectors_rendered/userComment.png"),
 INFORMATION("pics/vectors_rendered/information.png"),
 HELP ("pics/vectors_rendered/help.png")
 
    ;

    private final String filename;

    private static final Logger LOGGER = LoggerManager.getLogger(SpeedmentIcon.class);
    private static final Map<Class<? extends Document>, SpeedmentIcon> NODE_ICONS = new ConcurrentHashMap<>();

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
    }

    public Image load() {
        return new Image(getFileInputStream());
    }

    public Image load(Document node) {
        return new Image(getFileInputStream(node));
    }

    public ImageView view() {
        return new ImageView(load());
    }

    public ImageView view(Document node) {
        return new ImageView(load(node));
    }

    public static ImageView forNode(Document node) {
        requireNonNull(node);

        final Optional<String> path = Optional.of(node)
                .filter(HasIconPath.class::isInstance)
                .map(HasIconPath.class::cast)
                .map(HasIconPath::getIconPath);

        if (path.isPresent()) {
            final InputStream stream = SpeedmentIcon.class.getResourceAsStream(path.get());
            if (stream != null) {
                return new ImageView(new Image(stream));
            } else {
                LOGGER.error(
                        "Config node '" + node.getClass().getSimpleName()
                        + "' specified a custom icon '" + path.get() + "' that could not be loaded."
                );
            }
        }

        final SpeedmentIcon icon = NODE_ICONS.get(
                Optional.of(node)
                    .filter(HasMainInterface.class::isInstance)
                    .map(HasMainInterface.class::cast)
                    .map(HasMainInterface::mainInterface)
                    .orElse(node.getClass())
        );

        if (icon != null) {
            return icon.view();
        } else {
            LOGGER.error("Found no predefined icon for node type '" + node.getClass().getSimpleName() + "'.");
            return SpeedmentIcon.HELP.view();
        }
    }

    private SpeedmentIcon(String filename) {
        this.filename = requireNonNull(filename);
    }

    private InputStream getFileInputStream() {
        return getFileInputStream(null);
    }

    private InputStream getFileInputStream(Document node) {
        final InputStream stream = getClass().getResourceAsStream(filename);

        if (stream == null) {
            throw new RuntimeException("Could not find icon: '" + filename + "'.");
        }

        return stream;
    }
}
