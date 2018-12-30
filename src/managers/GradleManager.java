/**
 *  Copyright (C) 2017 Necati Caner Gaygisiz
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package managers;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SelectFromListDialog;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.stream.Stream;

public class GradleManager {

    private Project project;

    private Document buildGradle;

    private String[] modules = new String[]{};

    private VirtualFile projectBaseDir;

    public GradleManager(Project project) {
        this.project = project;
    }

    public boolean initBuildGradle() throws FileNotFoundException {
        checkFilesExist();

        SelectFromListDialog.ToStringAspect toStringAspect = String::valueOf;
        VirtualFile gradleVirtualFile;
        if (modules.length > 1) {
            SelectFromListDialog dialog = new SelectFromListDialog(project, modules, toStringAspect, "Select Gradle Module", ListSelectionModel.SINGLE_SELECTION);
            boolean isOk = dialog.showAndGet();
            if (isOk) {
                gradleVirtualFile = projectBaseDir
                        .findChild(String.valueOf(dialog.getSelection()[0]))
                        .findChild("build.gradle");
            } else {
                return false;
            }
        } else if (modules.length == 1) {
            gradleVirtualFile = projectBaseDir
                    .findChild(modules[0])
                    .findChild("build.gradle");
        } else {
            gradleVirtualFile = projectBaseDir
                    .findChild("build.gradle");
        }

        if (gradleVirtualFile != null) {
            buildGradle = FileDocumentManager.getInstance().getDocument(gradleVirtualFile);
        }

        return true;
    }

    private void checkFilesExist() throws FileNotFoundException {

        final String basePath = project.getBasePath();
        if (StringUtils.isEmpty(basePath)) {
            throw new FileNotFoundException(Strings.ERROR_BASE_PATH_NOT_FOUND);
        }

        projectBaseDir = LocalFileSystem.getInstance().findFileByPath(basePath);
        if (projectBaseDir == null) {
            throw new FileNotFoundException(Strings.ERROR_PROJECT_BASE_DIR_NOT_FOUND);
        }

        final VirtualFile virtualSettingsGradle = projectBaseDir.findChild("settings.gradle");
        if (virtualSettingsGradle != null) {
            final Document settingsGradle = FileDocumentManager.getInstance().getDocument(virtualSettingsGradle);
            if (settingsGradle != null) {
                modules = readSettingsGradle(settingsGradle);
            }
        } else if (projectBaseDir.findChild("build.gradle") == null) {
            throw new FileNotFoundException(Strings.ERROR_BUILD_GRADLE_NOT_FOUND);
        }
    }

    public void addDependency(String repository, AnActionEvent actionEvent) {
        String[] documentText = buildGradle.getText().split("\n");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < documentText.length; i++) {
            String line = documentText[i];

            sb
                    .append(line)
                    .append("\n");

            if (line.contains("dependencies")) {
                if (line.contains("{")) {
                    sb
                            .append("\timplementation '")
                            .append(repository)
                            .append("'\n");
                }
            }
        }

        writeToGradle(sb, actionEvent);
    }

    private void writeToGradle(StringBuilder stringBuilder, AnActionEvent actionEvent) {
        final Application application = ApplicationManager.getApplication();
        application.invokeLater(() -> {
            application.runWriteAction(() -> buildGradle.setText(stringBuilder));
            syncProject(actionEvent);
        });
    }

    // TODO do not allow this method called without invokeLater()
    private void syncProject(AnActionEvent actionEvent) {
        AnAction androidSyncAction = getAction("Android.SyncProject");
        AnAction refreshAllProjectAction = getAction("ExternalSystem.RefreshAllProjects");

        if (androidSyncAction != null && !(androidSyncAction instanceof EmptyAction)) {
            androidSyncAction.actionPerformed(actionEvent);
        } else if (refreshAllProjectAction != null && !(refreshAllProjectAction instanceof EmptyAction)) {
            refreshAllProjectAction.actionPerformed(actionEvent);
        } else {
            SwingUtilities.invokeLater(() -> Messages.showInfoMessage(Strings.ERROR_SYNC_FAILED, Strings.ERROR_TITLE_SYNC_FAILED));
        }
    }

    private AnAction getAction(String actionId) {
        return ActionManager.getInstance().getAction(actionId);
    }

    private String[] readSettingsGradle(Document settingsGradle) {
        return Stream.of(settingsGradle.getText().split("'"))
                .filter(s -> s.startsWith(":"))
                .map(s -> s.replace(":", ""))
                .toArray(String[]::new);

    }
}
