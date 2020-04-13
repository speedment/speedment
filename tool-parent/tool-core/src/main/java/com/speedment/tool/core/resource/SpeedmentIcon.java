/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.resource;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.tool.config.trait.HasIconPath;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * An enumeration of all the icons available in the Speedment Icon Package.
 *
 * @author Emil Forslund
 * @since 2.2.0
 */
public enum SpeedmentIcon implements Icon {

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
    TABLE_LINK("/pics/vectors_rendered/tableLink.png"),
    PLUGIN_DATA("/pics/vectors_rendered/plugin.png"),
    // Menu icons
    ADD_DBMS_TRANS("/pics/dialog/add_dbms_trans.png"),
    OPEN_FILE("/pics/dialog/openFile.png"),
    //QUESTION("/pics/dialog/question.png"),   // Doesn't exist
    SPEEDMENT_LOGO("/pics/dialog/speedment_logo.png"),
    SPEEDMENT_LOGO_100("/pics/dialog/speedment_logo100.png"),
    WALKING_MAN("/pics/dialog/walking_man.gif"),
    WALKING_MAN_SMALL("/pics/dialog/walking_man_small.png"),
    // Logotype
    SPIRE("/images/logo.png"),
    //Components controller
    SITEMAP_COLOR("/pics/vectors_rendered/sitemapColor.png"),
    BOX("/pics/vectors_rendered/box.png"),
    BRICKS("/pics/vectors_rendered/bricks.png"),
    BOOK("/pics/vectors_rendered/book.png"),
    BOOK_OPEN("/pics/vectors_rendered/bookOpen.png"),
    BOOK_NEXT("/pics/vectors_rendered/bookNext.png"),
    DATABASE_CONNECT("/pics/vectors_rendered/databaseConnect.png"),
    DATABASE("/pics/vectors_rendered/database.png"),
    PAGE_WHITE_CUP("/pics/vectors_rendered/pageWhiteCup.png"),
    CUP("/pics/vectors_rendered/cup.png"),
    TEXT_SIGNATURE("/pics/vectors_rendered/textSignature.png"),
    BOOK_LINK("/pics/vectors_rendered/bookLink.png"),
    //MenuController
    DISK("/pics/vectors_rendered/disk.png"),
    DISK_MULTIPLE("/pics/vectors_rendered/diskMultiple.png"),
    DOOR_IN("/pics/vectors_rendered/doorIn.png"),
    APPLICATION_SIDE_TREE("/pics/vectors_rendered/applicationSideTree.png"),
    APPLICATION_FORM("/pics/vectors_rendered/applicationForm.png"),
    APPLICATION_XP_TERMINAL("/pics/vectors_rendered/applicationXpTerminal.png"),
    USER_COMMENT("/pics/vectors_rendered/userComment.png"),
    INFORMATION("/pics/vectors_rendered/info.png"),
    HELP("/pics/vectors_rendered/help.png"),
    SCRIPT_ADD("/pics/vectors_rendered/scriptAdd.png"),
    SCRIPT_DELETE("/pics/vectors_rendered/scriptDelete.png"),

    DATABASE_MONO("/pics/vectors_rendered/mono-database.png"),
    DISK_MONO("/pics/vectors_rendered/mono-disk2.png"),
    EXCLAMATION_MONO("/pics/vectors_rendered/mono-exclamation.png"),
    REFRESH_MONO("/pics/vectors_rendered/mono-fa-refresh.png"),
    FOLDER_OPEN_MONO("/pics/vectors_rendered/mono-folder-open.png"),
    LOCK_MONO("/pics/vectors_rendered/mono-lock.png"),
    PLAY_CIRCLE_MONO("/pics/vectors_rendered/mono-play-circle.png"),
    SIGN_IN_MONO("/pics/vectors_rendered/mono-sign-in.png"),
    SPINNER_MONO("/pics/vectors_rendered/mono-spinner.png"),
    TIMES_MONO("/pics/vectors_rendered/mono-times.png");

    private final String filename;

    private static final Logger LOGGER = LoggerManager.getLogger(SpeedmentIcon.class);
    private static final Map<Class<?>, SpeedmentIcon> NODE_ICONS;

    static {
        final Map<Class<?>, SpeedmentIcon> map = new HashMap<>();
        map.put(Dbms.class, DBMS);
        map.put(Schema.class, SCHEMA);
        map.put(Table.class, TABLE);
        map.put(Column.class, COLUMN);
        map.put(Index.class, INDEX);
        map.put(IndexColumn.class, INDEX_COLUMN);
        map.put(ForeignKey.class, FOREIGN_KEY);
        map.put(ForeignKeyColumn.class, FOREIGN_KEY_COLUMN);
        map.put(PrimaryKeyColumn.class, PRIMARY_KEY_COLUMN);
        map.put(Project.class, PROJECT);
        NODE_ICONS = Collections.unmodifiableMap(map);
    }

    public Image load() {
        return new Image(getFileInputStream());
    }

    @Override
    public ImageView view() {
        return new ImageView(load());
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

        final SpeedmentIcon icon;
        if (node instanceof Table) {
            icon = ((Table) node).isView()
                ? TABLE_LINK : TABLE;
        } else {
            icon = NODE_ICONS.get(
                Optional.of(node)
                    .filter(HasMainInterface.class::isInstance)
                    .map(HasMainInterface.class::cast)
                    .map(HasMainInterface::mainInterface)
                    .orElse(null)
            );
        }

        if (icon != null) {
            return icon.view();
        } else {
            LOGGER.error("Found no predefined icon for node type '" + node.getClass().getSimpleName() + "'.");
            return SpeedmentIcon.HELP.view();
        }
    }

    SpeedmentIcon(String filename) {
        this.filename = requireNonNull(filename);
    }

    private InputStream getFileInputStream() {
        final InputStream stream = getClass().getResourceAsStream(filename);
        if (stream == null) {
            throw new SpeedmentException("Could not find icon: '" + filename + "'.");
        }
        return stream;

    }

    public String getFilename() {
        return filename;
    }
}
