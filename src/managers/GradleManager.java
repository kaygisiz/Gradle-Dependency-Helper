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

    private Document settingsGradle;

    private String[] modules;

    public GradleManager(Project project) {
        this.project = project;
        settingsGradle = FileDocumentManager.getInstance().getDocument(project.getBaseDir().findChild("settings.gradle"));
        modules = readSettingsGradle();
        initBuildGradle();
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
                            .append("\tcompile '")
                            .append(repository)
                            .append("'\n");
                }
            }
        }

        writeToGradle(sb, actionEvent);
    }

    private void writeToGradle(StringBuilder stringBuilder, AnActionEvent actionEvent) {
        ApplicationManager.getApplication().runWriteAction(() -> buildGradle.setText(stringBuilder));
        syncProject(actionEvent);
    }

    private void syncProject(AnActionEvent actionEvent) {
        ActionManager.getInstance().getAction("Android.SyncProject").actionPerformed(actionEvent);
    }

    private String[] readSettingsGradle() {
        return Stream.of(settingsGradle.getText().split("'"))
                .filter(s -> s.startsWith(":"))
                .map(s -> s.replace(":", ""))
                .toArray(String[]::new);

    }

    private void initBuildGradle() {
        SelectFromListDialog.ToStringAspect toStringAspect = String::valueOf;
        VirtualFile gradleVirtualFile = null;
        if (modules.length > 1) {
            SelectFromListDialog dialog = new SelectFromListDialog(project, modules, toStringAspect, "Select Gradle Module", ListSelectionModel.SINGLE_SELECTION);
            boolean isOk = dialog.showAndGet();
            if (isOk) {
                gradleVirtualFile = project.getBaseDir()
                        .findChild(String.valueOf(dialog.getSelection()[0]))
                        .findChild("build.gradle");
            }
        } else {
            gradleVirtualFile = project.getBaseDir()
                    .findChild(modules[0])
                    .findChild("build.gradle");
        }

        if (gradleVirtualFile != null) {
            buildGradle = FileDocumentManager.getInstance().getDocument(gradleVirtualFile);
        }
    }
}
